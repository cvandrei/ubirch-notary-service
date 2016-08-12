package com.ubirch.notary.routes

import com.typesafe.config.ConfigException.Missing
import com.typesafe.scalalogging.LazyLogging
import com.ubirch.notary.Boot
import com.ubirch.notary.config.{AppConfig, AppConst}
import com.ubirch.notary.directives.UriPathDirective
import com.ubirch.notary.json.{NotarizeResponse, Notarize}
import com.ubirch.util.crypto.hash.HashUtil
import org.bitcoinj.core.{Address, Coin, InsufficientMoneyException, NetworkParameters, Transaction}
import org.bitcoinj.script.ScriptBuilder
import org.bitcoinj.wallet.SendRequest
import spray.http.StatusCodes._
import spray.http.{HttpEntity, HttpResponse}
import spray.routing.{Directives, Route}

/**
  * author: cvandrei
  * since: 2016-06-08
  */
class NotarizeRoute extends Directives with UriPathDirective with LazyLogging {

  import com.ubirch.notary.json.Json4sSupport._

  val routes: Route = {

    (path(AppConst.pathNotarize) & entity(as[Notarize]) & post & uriPath & hostName) { (notarizeReq, path, host) =>

      logger.info(s"$path POST from $host")
      complete {

        verifySignatureAndKey(notarizeReq) match {

          case true =>

            sendOpReturn(notarizeReq) match {
              case Some(txHash) => NotarizeResponse(txHash)
              case None => HttpResponse(PreconditionFailed, HttpEntity("Unable to notarize right now. Either we ran out of bitcoins or you tried to send too much data (<= 40 bytes)."))
            }

          case false => Unauthorized

        }

      }

    }

  }

  private def verifySignatureAndKey(req: Notarize): Boolean = {

    // TODO verify public key is trusted
    // TODO verify signature

    true

  }

  /**
    * Trigger sending an OP_RETURN (uses only the "data" and "dataIsHash" fields from the incoming [NotarizeData]).
    *
    * @param notarize json from the request
    * @return None if there was an error
    */
  private def sendOpReturn(notarize: Notarize): Option[String] = {

    val wallet = Boot.wallet

    val network = wallet.getNetworkParameters
    val changeAddress = wallet.currentChangeAddress()

    try {

      opReturnRequest(network, changeAddress, notarize.data, notarize.dataIsHash) match {

        case None => None

        case Some(request) =>

          val tx = wallet.sendCoins(request).tx
          val txHash = tx.getHash
          logger.info(s"send OP_RETURN: txHash=$txHash, feePerKb=${request.feePerKb.getValue}, fee=${tx.getFee.getValue}, tx=$tx")

          Some(txHash.toString)

      }

    } catch {
      case e: InsufficientMoneyException => None
    }


  }

  /**
    * @param network    to what network we send the tx to
    * @param recipient  recipient of the tx
    * @param dataString data portion of OP_RETURN (currently <= 40 bytes)
    * @return None if data is too long (currently 40 bytes); Some otherwise
    */
  private def opReturnRequest(network: NetworkParameters,
                              recipient: Address,
                              dataString: String,
                              dataIsHash: Boolean
                             ): Option[SendRequest] = {

    val data: Array[Byte] = dataIsHash match {
      case true => HashUtil.hashAsBytes(dataString)
      case false => dataString.toCharArray.map(_.toByte)
    }

    try {

      val tx = new Transaction(network)
      tx.addOutput(Coin.ZERO, ScriptBuilder.createOpReturnScript(data))
      tx.addOutput(Coin.valueOf(100000), recipient)

      Some(requestWithFeePerKb(tx))

    } catch {
      case e: IllegalArgumentException =>
        logger.error(s"data is too long for an OP_RETURN: data=$data")
        None
    }

  }

  private def requestWithFeePerKb(tx: Transaction) = {

    val req = SendRequest.forTx(tx)

    try {
      val txFee = AppConfig.config.getLong("bitcoin.feePerKb")
      req.feePerKb = Coin.valueOf(txFee)
    } catch {
      case e: Missing => // don't set custom feePerKb
    }

    req

  }

}
