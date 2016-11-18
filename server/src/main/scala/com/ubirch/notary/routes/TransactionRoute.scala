package com.ubirch.notary.routes

import com.typesafe.scalalogging.slf4j.StrictLogging
import com.ubirch.notary.Boot
import com.ubirch.notary.util.RouteConstants
import com.ubirch.notary.core.bitcoin.BitcoinWalletUtil
import com.ubirch.notary.directives.UriPathDirective
import spray.routing.{Directives, Route}

/**
  * author: cvandrei
  * since: 2016-06-09
  */
class TransactionRoute extends Directives with UriPathDirective with StrictLogging {

  import com.ubirch.notary.json.Json4sSupport._

  val routes: Route = {

    get {

      (path(RouteConstants.pathTransactions / RouteConstants.pathUnspent) & uriPath & hostName) { (path, host) =>

        logger.info(s"$path GET from $host")
        complete {
          BitcoinWalletUtil.unspentTransactions(Boot.wallet)
        }

      } ~ (path(RouteConstants.pathTransactions / RouteConstants.pathPending) & uriPath & hostName) { (path, host) =>

        logger.info(s"$path GET from $host")
        complete {
          BitcoinWalletUtil.pendingTransactions(Boot.wallet)
        }

      } ~ (path(RouteConstants.pathTransactions) & uriPath & hostName) { (path, host) =>

        logger.info(s"$path GET from $host")
        complete {
          BitcoinWalletUtil.allTransactions(Boot.wallet)
        }

      }

    }

  }

}
