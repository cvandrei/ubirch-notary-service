package com.ubirch.notary.core.bitcoin

import java.io.File

import com.google.common.util.concurrent.Service
import com.typesafe.config.ConfigException.Missing
import com.typesafe.scalalogging.LazyLogging

import com.ubirch.notary.core.config.AppConfig

import org.bitcoinj.core.NetworkParameters
import org.bitcoinj.kits.WalletAppKit
import org.bitcoinj.wallet.Wallet

/**
  * author: cvandrei
  * since: 2016-08-17
  */
class BitcoinConnection extends LazyLogging {

  private var walletAppKit: Option[WalletAppKit] = None
  private var bitcoinService: Option[Service] = None

  def startBitcoin: (WalletAppKit, Service) = {

    val network = NetworkParameters.fromID(AppConfig.bitcoinNetwork)
    val walletDir = new File(AppConfig.bitcoinWalletDirectory)
    val walletPrefix = AppConfig.bitcoinWalletPrefix
    logger.info(
      s"""=== starting bitcoin connection ===
          |  network: ${network.getId}
          |  wallet.directory: $walletDir
          |  wallet.fileName: $walletPrefix
       """.stripMargin)

    val kit = new WalletAppKit(network, walletDir, walletPrefix)
    val service = kit.startAsync
    enableTorIfConfigured(kit)
    kit.awaitRunning()
    logger.info("=== finished starting bitcoin connection ===")

    walletAppKit = Some(kit)
    bitcoinService = Some(service)

    (kit, service)

  }

  def wallet: Wallet = {
    walletAppKit match {
      case None => throw new IllegalStateException("unable to get wallet: bitcoin connection has not been started or starting it failed")
      case Some(kit) => kit.wallet
    }
  }

  def stop(): Unit = {

    logger.info("=== stopping bitcoin connection ===")
    bitcoinService match {

      case None => logger.info("bitcoin connection was not open....nothing to stop")

      case Some(service) =>
        logger.info(s"bitcoin connection running? ...${service.isRunning} (${service.state})")
        service.stopAsync
          .awaitTerminated()

    }


  }

  private def enableTorIfConfigured(kit: WalletAppKit) = {

    try {

      if (AppConfig.bitcoinTorEnabled) {
        logger.info("Tor proxy enabled")
        kit.useTor()
      }

    } catch {
      case e: Missing => logger.info("Tor proxy is not enabled")
    }

  }

}
