package com.ubirch.notary.routes

import com.typesafe.scalalogging.LazyLogging
import com.ubirch.notary.Boot
import com.ubirch.notary.config.AppConst
import com.ubirch.notary.directives.UriPathDirective
import spray.routing.{Directives, Route}

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

/**
  * author: cvandrei
  * since: 2016-06-09
  */
class TransactionRoute extends Directives with UriPathDirective with LazyLogging {

  import com.ubirch.notary.json.Json4sSupport._

  val routes: Route = {

    get {

      (path(AppConst.pathTransactions / AppConst.pathUnspent) & uriPath & hostName) { (path, host) =>

        logger.info(s"$path GET from $host")
        complete {
          unspentTransactions
        }

      } ~ (path(AppConst.pathTransactions / AppConst.pathPending) & uriPath & hostName) { (path, host) =>

        logger.info(s"$path GET from $host")
        complete {
          pendingTransactions
        }

      } ~ (path(AppConst.pathTransactions) & uriPath & hostName) { (path, host) =>

        logger.info(s"$path GET from $host")
        complete {
          allTransactions
        }

      }

    }

  }

  private def unspentTransactions: BitcoinTransactions = bitcoinTransactionsToResponse(Boot.wallet.getUnspents)

  private def pendingTransactions: BitcoinTransactions = bitcoinTransactionsToResponse(Boot.wallet.getPendingTransactions)

  private def allTransactions: BitcoinTransactions = bitcoinTransactionsToResponse(Boot.wallet.getTransactionsByTime)

  private def bitcoinTransactionsToResponse[B](txJavaList: java.util.Collection[B]): BitcoinTransactions = {

    val txListBuffer = new ListBuffer[BitcoinTx]
    for (tx <- txJavaList.asScala) {
      txListBuffer += BitcoinTx(tx.toString)
    }

    BitcoinTransactions(txListBuffer.toSeq)

  }

}

case class BitcoinTransactions(tx: Seq[BitcoinTx])

case class BitcoinTx(tx: String)
