package com.ubirch.notary.json

/**
  * author: cvandrei
  * since: 2016-08-12
  */
case class Notarize(data: String,
                    signature: Option[String] = None,
                    publicKey: Option[String] = None
                   )

case class NotarizeResponse(hash: String)
