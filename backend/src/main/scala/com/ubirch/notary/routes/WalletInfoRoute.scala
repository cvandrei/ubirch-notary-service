package com.ubirch.notary.routes

import com.typesafe.scalalogging.LazyLogging
import com.ubirch.notary.Boot
import com.ubirch.notary.config.AppConst
import com.ubirch.notary.directives.UriPathDirective
import com.ubirch.notary.json.{WalletInfo, BitcoinWallet, BitcoinBalance, BitcoinAddress}
import org.bitcoinj.core.Coin
import org.bitcoinj.wallet.KeyChain.KeyPurpose
import org.bitcoinj.wallet.Wallet
import spray.routing.{Directives, Route}

/**
  * author: cvandrei
  * since: 2016-06-09
  */
class WalletInfoRoute extends Directives with UriPathDirective with LazyLogging {

  import com.ubirch.notary.json.Json4sSupport._

  val routes: Route = {

    (path(AppConst.pathWalletInfo) & get & uriPath & hostName) { (path, host) =>

      logger.info(s"$path GET from $host")
      complete {
        walletInfoObject(Boot.wallet)
      }

    }

  }

  private def walletInfoObject(wallet: Wallet): WalletInfo = {

    val bitcoinWallet = bitcoinWalletObject(wallet)

    WalletInfo(bitcoinWallet)

  }

  private def bitcoinWalletObject(wallet: Wallet): BitcoinWallet = {

    val balance = bitcoinBalance(wallet.getBalance)
    val address = bitcoinAddress(wallet)
    val network = Boot.wallet.getNetworkParameters.getId match {
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

}
