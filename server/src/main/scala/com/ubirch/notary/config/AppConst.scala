package com.ubirch.notary.config

/**
  * author: cvandrei
  * since: 2016-06-08
  */
object AppConst {

  final val INTERFACE = "notaryService.interface"
  final val PORT = "notaryService.port"

  final val BITCOIN_NETWORK = "bitcoin.network"
  final val BITCOIN_WALLET_DIRECTORY = "bitcoin.wallet.directory"
  final val BITCOIN_WALLET_PREFIX = "bitcoin.wallet.prefix"

  final val pathVersion = "v1"
  final val urlVersion = s"/$pathVersion"

  final val pathNotary = "notaryService"
  final val urlNotary = s"$urlVersion/$pathNotary"

  final val pathNotarize = "notarize"
  final val urlNotarize = s"$urlNotary/$pathNotarize" // /v1/notary/notarize

  final val pathTransactions = "transactions"
  final val urlTransactions = s"$urlNotary/$pathTransactions" // /v1/notary/transactions

  final val pathUnspent = "unspent"
  final val urlTransactionsUnspent = s"$urlTransactions/$pathUnspent" // /v1/notary/transactions/unspent

  final val pathPending = "pending"
  final val urlTransactionsPending = s"$urlTransactions/$pathPending" // /v1/notary/transactions/pending

  final val pathWalletInfo = "wallet-info"
  final val urlWalletInfo = s"$urlNotary/$pathWalletInfo" // /v1/notary/wallet-info

}
