package com.ubirch.notary.json

/**
  * author: cvandrei
  * since: 2016-08-12
  */
case class WalletInfo(bitcoinWallet: BitcoinWallet)

case class BitcoinWallet(network: String, balance: BitcoinBalance, address: BitcoinAddress)

case class BitcoinBalance(BTC: Long, mBTC: Long, µBTC: Long)

case class BitcoinAddress(receive: String, change: String, authenticate: String, refund: String)
