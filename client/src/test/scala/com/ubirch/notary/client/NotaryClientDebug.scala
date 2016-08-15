package com.ubirch.notary.client

import java.net.URL

import com.ubirch.notary.client.json.MyJsonProtocol
import com.ubirch.notary.json.NotarizeResponse
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

    ignore("for manual debug") {

      val event = "ubirch-chain-test-2342"
      val url = new URL("http://ubirchnotaryservice-env.us-east-1.elasticbeanstalk.com:8080/v1/notary/notarize")
      val json = s"""{"data": "$event"}"""
      val body = RequestBody(json, APPLICATION_JSON)

      val httpClient = new HttpClient
      val res = httpClient.post(url, Some(body))

      println("=== RESPONSE ===")
      println(s"status=${res.status}, body=${res.body.asString}")
      val hash = parse(res.body.asString).extract[NotarizeResponse]

      val expected = HashUtil.sha256HexString(event)
      hash.hash should be(expected)

      println(s"hash=${hash.hash}")

    }

  }

}
