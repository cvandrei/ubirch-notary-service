package com.ubirch.notary.client.config

import com.typesafe.config.ConfigFactory

/**
  * author: cvandrei
  * since: 2016-08-15
  */
object Config {

  /**
    * @return url to send notarize notifications to
    */
  def anchorUrl: String = ConfigFactory.load.getString(AppConst.ANCHOR_URL)

}
