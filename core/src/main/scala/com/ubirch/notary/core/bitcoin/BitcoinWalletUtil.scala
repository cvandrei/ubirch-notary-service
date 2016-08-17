package com.ubirch.notary.core.bitcoin

import com.ubirch.notary.json.{BitcoinAddress, BitcoinBalance, BitcoinTransactions, BitcoinTx, BitcoinWallet, WalletInfo}
import org.bitcoinj.core.Coin
import org.bitcoinj.wallet.KeyChain.KeyPurpose
import org.bitcoinj.wallet.Wallet

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

/**
  * author: cvandrei
  * since: 2016-08-17
  */
object BitcoinWalletUtil {

  def unspentTransactions(wallet: Wallet): BitcoinTransactions = bitcoinTransactionsToResponse(wallet.getUnspents)

  def pendingTransactions(wallet: Wallet): BitcoinTransactions = bitcoinTransactionsToResponse(wallet.getPendingTransactions)

  def allTransactions(wallet: Wallet): BitcoinTransactions = bitcoinTransactionsToResponse(wallet.getTransactionsByTime)

  def walletInfoObject(wallet: Wallet): WalletInfo = {

    val bitcoinWallet = bitcoinWalletObject(wallet)

    WalletInfo(bitcoinWallet)

  }

  private def bitcoinWalletObject(wallet: Wallet): BitcoinWallet = {

    val balance = bitcoinBalance(wallet.getBalance)
    val address = bitcoinAddress(wallet)
    val network = wallet.getNetworkParameters.getId match {
      case "org.bitcoin.regtest" => "regtest"
      case "org.bitcoin.test" => "testnet"
      case "org.bitcoin.production" => "mainnet"
    }

    BitcoinWallet(network, balance, address)

  }

  private def bitcoinBalance(balance: Coin): BitcoinBalance = {

    val btc = balance.getValue / Coin.COIN.getValue
    val mBtc = balance.getValue / Coin.MILLICOIN.getValue
    val µBtc = balance.getValue / Coin.MICROCOIN.getValue

    BitcoinBalance(btc, mBtc, µBtc)

  }

  private def bitcoinAddress(wallet: Wallet): BitcoinAddress = {

    val receive = wallet.currentReceiveAddress.toBase58
    val change = wallet.currentChangeAddress.toBase58
    val authentication = wallet.currentAddress(KeyPurpose.AUTHENTICATION).toBase58
    val refund = wallet.currentAddress(KeyPurpose.REFUND).toBase58

    BitcoinAddress(receive, change, authentication, refund)

  }

  private def bitcoinTransactionsToResponse[B](txJavaList: java.util.Collection[B]): BitcoinTransactions = {

    val txListBuffer = new ListBuffer[BitcoinTx]
    for (tx <- txJavaList.asScala) {
      txListBuffer += BitcoinTx(tx.toString)
    }

    BitcoinTransactions(txListBuffer.toSeq)

  }

}
