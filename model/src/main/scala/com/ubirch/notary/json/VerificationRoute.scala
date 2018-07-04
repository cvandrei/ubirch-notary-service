package com.ubirch.notary.json

/**
  * author: cvandrei
  * since: 2016-08-12
  */
case class Verification(hash: String,
                        data: String,
                        dataIsHash: Boolean = false,
                        signature: Option[String] = None,
                        publicKey: Option[String] = None
                       )