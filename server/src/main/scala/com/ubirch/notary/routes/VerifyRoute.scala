
package com.ubirch.notary.routes

import akka.actor.ActorSystem
import com.typesafe.scalalogging.slf4j.StrictLogging
import com.ubirch.notary.directives.UriPathDirective
import com.ubirch.notary.json.{Json4sSupport, Verification}
import com.ubirch.notary.util.RouteConstants
import spray.http.{HttpRequest, HttpResponse}
import spray.routing.{Directives, Route}
import spray.client.pipelining._
import spray.http.StatusCodes._

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

/**
  * author: Matthias L. Jugel
  * since: 2018-07-04
  */
class VerifyRoute extends Directives with UriPathDirective with StrictLogging {
  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  import com.ubirch.notary.json.Json4sSupport._

  /*
  {
    "value": 0,
    "script": "6a40c9aa07eac2397fd05996bd14daa0722abfd4de79f8a4e14d01d2cf0087c14485b723356b25da3cc63c5f836269225e5253a59ddd4b4823c668413cf95dcb7c01",
    "addresses": null,
    "script_type": "null-data",
    "data_hex": "c9aa07eac2397fd05996bd14daa0722abfd4de79f8a4e14d01d2cf0087c14485b723356b25da3cc63c5f836269225e5253a59ddd4b4823c668413cf95dcb7c01"
  }
   */
  case class OutputsInfo(data_hex: Option[String])

  case class TransactionInfo(confirmed: String, confirmations: Int, outputs: Array[OutputsInfo])

  val routes: Route = {
    (path(RouteConstants.pathVerify) & entity(as[Verification]) & post & uriPath & hostName) { (verificationReq, path, host) =>
      logger.info(s"$path POST from $host")

      val pipeline: HttpRequest => Future[TransactionInfo] = sendReceive ~> unmarshal[TransactionInfo]
      val responseFuture: Future[TransactionInfo] =
        pipeline(Get(s"https://api.blockcypher.com/v1/btc/test3/txs/${verificationReq.hash}"))

      onComplete(responseFuture) {
        case Success(response) =>
          logger.debug(s"confirmed: ${response.confirmed} (${response.confirmations})")
          val output = response.outputs.find(o => o.data_hex.isDefined).get.data_hex.getOrElse("--UNDEFINED--")
          logger.debug(s"data     : $output")
          if(verificationReq.data == output) {
            complete(OK -> Map("confirmed" -> response.confirmed, "confirmations" -> response.confirmations))
          } else {
            complete(BadRequest -> Map("error" -> s"data mismatch: $output}"))
          }
        case Failure(error) =>
          complete(InternalServerError -> Map("error" -> s"blockchain access failed: ${error.getMessage}"))
      }
    }
  }
}
