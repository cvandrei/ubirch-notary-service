package com.ubirch.notary

import java.io.File
import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.util.Timeout
import com.google.common.util.concurrent.Service
import com.typesafe.scalalogging.LazyLogging
import com.ubirch.notary.config.{AppConfig, AppConst}
import com.ubirch.notary.service.NotaryServiceActor
import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.kits.WalletAppKit
import org.bitcoinj.wallet.Wallet
import spray.can.Http

/**
  * author: cvandrei
  * since: 2016-06-08
  */
object Boot extends App with LazyLogging {

  logger.info("notaryService started")
  val (system, bitcoinWalletAppKit, bitcoinService) = start()

  Runtime.getRuntime.addShutdownHook(new Thread() {
    override def run() = {
      Boot.stop(bitcoinService)
    }
  })

  def start(): (ActorSystem, WalletAppKit, Service) = {

    val config = AppConfig.config

    val (kit, bitcoinService) = startBitcoin

    implicit val system = ActorSystem("on-spray-can")
    val service = system.actorOf(Props[NotaryServiceActor], "ubirch-notary")

    val interface = config.getString(AppConst.INTERFACE)
    val port = config.getInt(AppConst.PORT)
    implicit val timeout = Timeout(5, TimeUnit.SECONDS)
    IO(Http) ! Http.Bind(service, interface = interface, port = port)

    (system, kit, bitcoinService)

  }

  def stop(bitcoinService: Service) = {

    logger.info("=== stopping bitcoin connection ===")
    logger.info(s"bitcoin connection running? ...${bitcoinService.isRunning} (${bitcoinService.state})")
    bitcoinService.stopAsync
      .awaitTerminated()

  }

  def wallet: Wallet = bitcoinWalletAppKit.wallet

  private def startBitcoin: (WalletAppKit, Service) = {

    val config = AppConfig.config

    val network = NetworkParameters.fromID(config.getString(AppConst.BITCOIN_NETWORK))
    val walletDir = new File(config.getString(AppConst.BITCOIN_WALLET_DIRECTORY))
    val walletPrefix = config.getString(AppConst.BITCOIN_WALLET_PREFIX)
    logger.info(
      s"""=== starting bitcoin connection ===
          |  network: ${network.getId}
          |  wallet.directory: $walletDir
          |  wallet.fileName: $walletPrefix
       """.stripMargin)

    val kit = new WalletAppKit(network, walletDir, walletPrefix)
    val service = kit.startAsync
    kit.awaitRunning()
    logger.info("=== finished starting bitcoin connection ===")

    (kit, service)

  }

}
