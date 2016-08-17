package com.ubirch.notary.routes

import com.ubirch.notary.core.util.WelcomeUtil
import spray.routing.{Directives, Route}

/**
  * author: cvandrei
  * since: 2016-08-17
  */
class WelcomeRoute extends Directives {

  import com.ubirch.notary.json.Json4sSupport._

  val routes: Route = {

    get {
      complete {
        WelcomeUtil.welcome
      }
    }

  }

}
