package com.ubirch.notary.core.config

import com.ubirch.util.config.ConfigBase

/**
  * author: cvandrei
  * since: 2016-06-13
  */
object AppConfig extends ConfigBase {

  /*
   * Server
   ****************************************************/

  def serverInterface: String = config.getString(ConfigKeys.INTERFACE)

  def serverPort: Int = config.getInt(ConfigKeys.PORT)

  /*
   * Bitcoin
   ****************************************************/

  def bitcoinChainExplorerLink: String = config.getString(ConfigKeys.BITCOIN_EXPLORER_LINK)

  def bitcoinNetwork: String = config.getString(ConfigKeys.BITCOIN_NETWORK)

  def bitcoinWalletDirectory: String = config.getString(ConfigKeys.BITCOIN_WALLET_DIRECTORY)

  def bitcoinWalletPrefix: String = config.getString(ConfigKeys.BITCOIN_WALLET_PREFIX)

  def bitcoinFeePerKb: Long = config.getLong(ConfigKeys.BITCOIN_FEE_PER_KB)

  def bitcoinTorEnabled: Boolean = config.getBoolean(ConfigKeys.BITCOIN_TOR_ENABLED)

}
