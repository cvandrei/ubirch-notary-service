package com.ubirch.notary.core.config

/**
  * author: cvandrei
  * since: 2016-08-17
  */
object ConfigKeys {

  private val prefix = "notaryService"
  final val INTERFACE = s"$prefix.interface"
  final val PORT = s"$prefix.port"

  private val bitcoinPrefix = "bitcoin"
  final val BITCOIN_EXPLORER_LINK = s"$bitcoinPrefix.chainExplorerLink"
  final val BITCOIN_NETWORK = s"$bitcoinPrefix.network"
  final val BITCOIN_WALLET_DIRECTORY = s"$bitcoinPrefix.wallet.directory"
  final val BITCOIN_WALLET_PREFIX = s"$bitcoinPrefix.wallet.prefix"
  final val BITCOIN_FEE_PER_KB = s"$bitcoinPrefix.feePerKb"
  final val BITCOIN_TOR_ENABLED = s"$bitcoinPrefix.tor.enabled"

}
