package com.ubirch.notary.routes

import com.ubirch.notary.json.Welcome
import spray.routing.{Route, Directives}

/**
  * author: cvandrei
  * since: 2016-08-17
  */
class WelcomeRoute extends Directives {

  import com.ubirch.notary.json.Json4sSupport._

  val routes: Route = {

    get {
      complete {
        Welcome(message = "Welcome to the ubirch NotaryService")
      }
    }

  }

}
