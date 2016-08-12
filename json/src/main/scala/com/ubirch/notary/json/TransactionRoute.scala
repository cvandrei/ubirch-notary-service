package com.ubirch.notary.json

/**
  * author: cvandrei
  * since: 2016-08-12
  */
case class BitcoinTransactions(tx: Seq[BitcoinTx])

case class BitcoinTx(tx: String)
