package com.ubirch.notary.util

import com.ubirch.notary.core.config.AppConfig

/**
  * author: cvandrei
  * since: 2017-02-28
  */
object ChainExplorerUtil {

  private val txHashPlaceholder = "[TX_HASH]"

  def link(txHash: String): String = AppConfig.bitcoinChainExplorerLink.replace(txHashPlaceholder, txHash)

}
