package com.ubirch.notary.routes

import com.typesafe.scalalogging.LazyLogging
import com.ubirch.notary.Boot
import com.ubirch.notary.util.RouteConstants
import com.ubirch.notary.core.bitcoin.BitcoinWalletUtil
import com.ubirch.notary.directives.UriPathDirective
import spray.routing.{Directives, Route}

/**
  * author: cvandrei
  * since: 2016-06-09
  */
class WalletInfoRoute extends Directives with UriPathDirective with LazyLogging {

  import com.ubirch.notary.json.Json4sSupport._

  val routes: Route = {

    (path(RouteConstants.pathWalletInfo) & get & uriPath & hostName) { (path, host) =>

      logger.info(s"$path GET from $host")
      complete {
        BitcoinWalletUtil.walletInfoObject(Boot.wallet)
      }

    }

  }

}
