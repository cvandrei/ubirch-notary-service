package com.ubirch.notary

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.util.Timeout
import com.typesafe.scalalogging.LazyLogging
import com.ubirch.notary.core.bitcoin.BitcoinConnection
import com.ubirch.notary.core.config.AppConfig
import com.ubirch.notary.service.NotaryServiceActor
import org.bitcoinj.wallet.Wallet
import spray.can.Http

/**
  * author: cvandrei
  * since: 2016-06-08
  */
object Boot extends App with LazyLogging {

  logger.info("notaryService is starting")
  val bitcoinConnection = new BitcoinConnection
  val system = start()
  logger.info("notaryService started")

  Runtime.getRuntime.addShutdownHook(new Thread() {
    override def run() = {
      bitcoinConnection.stop()
    }
  })

  def start(): ActorSystem = {

    bitcoinConnection.startBitcoin

    implicit val system = ActorSystem("on-spray-can")
    val service = system.actorOf(Props[NotaryServiceActor], "ubirch-notary")

    val interface = AppConfig.serverInterface
    val port = AppConfig.serverPort
    implicit val timeout = Timeout(5, TimeUnit.SECONDS)
    IO(Http) ! Http.Bind(service, interface = interface, port = port)

    system

  }

  def wallet: Wallet = bitcoinConnection.wallet

}
