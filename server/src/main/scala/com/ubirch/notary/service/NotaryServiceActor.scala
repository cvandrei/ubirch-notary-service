package com.ubirch.notary.service

import akka.actor.Actor
import com.ubirch.notary.util.RouteConstants
import com.ubirch.notary.routes.{WelcomeRoute, TransactionRoute, NotarizeRoute, WalletInfoRoute}
import spray.routing._

/**
  * author: cvandrei
  * since: 2016-06-08
  */
class NotaryServiceActor extends Actor with NotaryService {

  def actorRefFactory = context

  def receive = runRoute(routes)
}

trait NotaryService extends HttpService {

  val notarize = new NotarizeRoute
  val walletInfo = new WalletInfoRoute
  val transaction = new TransactionRoute
  val welcome = new WelcomeRoute

  val routes: Route =

    pathPrefix(RouteConstants.v1) {
      pathPrefix(RouteConstants.notaryService) {
        notarize.routes ~ walletInfo.routes ~ transaction.routes
      }
    } ~ pathSingleSlash {
      welcome.routes
    }

}
