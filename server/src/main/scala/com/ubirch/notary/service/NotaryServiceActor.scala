package com.ubirch.notary.service

import akka.actor.Actor
import com.ubirch.notary.config.AppConst
import com.ubirch.notary.routes.{TransactionRoute, NotarizeRoute, WalletInfoRoute}
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

  val routes: Route =

    pathPrefix(AppConst.pathVersion / AppConst.pathNotary) {
      notarize.routes ~ walletInfo.routes ~ transaction.routes
    }
}
