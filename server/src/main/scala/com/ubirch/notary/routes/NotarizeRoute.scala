package com.ubirch.notary.routes

import com.typesafe.scalalogging.slf4j.StrictLogging
import com.ubirch.notary.Boot
import com.ubirch.notary.util.RouteConstants
import com.ubirch.notary.core.routes.NotarizeRouteUtil
import com.ubirch.notary.directives.UriPathDirective
import com.ubirch.notary.json.{Notarize, NotarizeResponse}
import spray.http.StatusCodes._
import spray.http.{HttpEntity, HttpResponse}
import spray.routing.{Directives, Route}

/**
  * author: cvandrei
  * since: 2016-06-08
  */
class NotarizeRoute extends Directives with UriPathDirective with StrictLogging {

  import com.ubirch.notary.json.Json4sSupport._

  private val notarizeRouteUtil = new NotarizeRouteUtil

  val routes: Route = {

    (path(RouteConstants.pathNotarize) & entity(as[Notarize]) & post & uriPath & hostName) { (notarizeReq, path, host) =>

      logger.info(s"$path POST from $host")
      complete {

        notarizeRouteUtil.verifySignatureAndKey(notarizeReq) match {

          case true =>

            notarizeRouteUtil.sendOpReturn(notarizeReq, Boot.wallet) match {
              case Some(txHash) => NotarizeResponse(txHash)
              case None => HttpResponse(PreconditionFailed, HttpEntity("Unable to notarize right now. Either we ran out of bitcoins or you tried to send too much data (<= 40 bytes)."))
            }

          case false => Unauthorized

        }

      }

    }

  }

}
