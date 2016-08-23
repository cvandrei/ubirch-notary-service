package com.ubirch.notary.client.config

import com.ubirch.util.config.ConfigBase

/**
  * author: cvandrei
  * since: 2016-08-15
  */
object ClientConfig extends ConfigBase {

  /**
    * @return url to send notarize notifications to
    */
  def anchorUrl: String = config.getString(AppConst.ANCHOR_URL)

}
