package com.ubirch.notary.client

import java.net.URL

import com.typesafe.scalalogging.LazyLogging
import com.ubirch.notary.client.config.Config
import com.ubirch.notary.client.json.MyJsonProtocol
import com.ubirch.notary.json.{Notarize, NotarizeResponse}
import org.json4s.native.JsonMethods._
import uk.co.bigbeeconsultants.http.HttpClient
import uk.co.bigbeeconsultants.http.header.MediaType._
import uk.co.bigbeeconsultants.http.request.RequestBody
import uk.co.bigbeeconsultants.http.response.Status._

/**
  * author: cvandrei
  * since: 2016-08-05
  */
object NotaryClient extends MyJsonProtocol
  with LazyLogging {

  def notarize(blockHash: String, dataIsHash: Boolean = false): Option[NotarizeResponse] = {

    val notarizeUrl = new URL(Config.anchorUrl)

    val notarizeObject = Notarize(blockHash, dataIsHash)
    val json = serialization.write(notarizeObject)
    logger.debug(s"json=$json")
    val body = RequestBody(json, APPLICATION_JSON)

    val httpClient = new HttpClient
    val res = httpClient.post(notarizeUrl, Some(body))

    res.status match {

      case S200_OK => Some(parse(res.body.asString).extract[NotarizeResponse])

      case _ =>
        logger.error(s"failed to notarize: blockHash=$blockHash, response=$res")
        None

    }


  }

}
