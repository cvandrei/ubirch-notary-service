package com.ubirch.notary.client

import java.net.URL

import com.ubirch.notary.client.json.MyJsonProtocol
import com.ubirch.notary.json.{Notarize, NotarizeResponse}
import com.ubirch.util.crypto.hash.HashUtil
import org.json4s.native.JsonMethods._
import org.scalatest.{FeatureSpec, Matchers}
import uk.co.bigbeeconsultants.http.HttpClient
import uk.co.bigbeeconsultants.http.header.MediaType._
import uk.co.bigbeeconsultants.http.request.RequestBody

/**
  * author: cvandrei
  * since: 2016-08-08
  */
class NotaryClientDebug extends FeatureSpec
  with Matchers
  with MyJsonProtocol {

  feature("NotaryClient.notarize") {

    scenario("for manual debug") {

      val event = "ubirch-chain-test-2342"
      val url = new URL("http://ubirchnotaryservice-env.us-east-1.elasticbeanstalk.com/v1/notary/notarize")

      val eventHash = HashUtil.sha256HexString(event)
      val notarizeObject = Notarize(eventHash, dataIsHash = true)
      val json = serialization.write(notarizeObject)

      val body = RequestBody(json, APPLICATION_JSON)
      val res = (new HttpClient).post(url, Some(body))

      println("=== RESPONSE ===")
      println(s"status=${res.status}, body=${res.body.asString}")
      val hash = parse(res.body.asString).extract[NotarizeResponse]

      hash.hash shouldNot be("")

      println(s"txHash=${hash.hash}")

    }

  }

}
