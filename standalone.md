# Prototype Standalone

* [Getting Started](#started)
* [Query Further Information](#furtherinfo)
* [Switching to `testnet`](#testnet-switch)
* [Clean Up](#clean-up)
* [Additional Information](#additional)


## Getting Started {#started}

* install `bitcoin-cli` and `bitcoind`

Following the [Bitcoing Developer Examples: `regtest` mode](https://bitcoin.org/en/developer-examples#regtest-mode) we
run the following terminal commands.

Create a blockchain in mode `regtest` (local only) with 101 blocks and list the current balance.

    bitcoind -regtest -daemon
    bitcoin-cli -regtest generate 101
    bitcoin-cli -regtest getbalance

Running the `OpReturnSender` program for the first time it creates a wallet for us and prints out it's recipient address
to which we send 10 BTCs:

    bitcoin-cli -regtest sendtoaddress $RECEIVING_ADDRESS 10.00

This lists unspent outputs (UTXO) and the `0` includes unconfirmed ones:

    bitcoin-cli -regtest listunspent 0

The 10 BTC that we sent to the wallet from `OpReturnSender` are not listed since it's wallet is in another context but the
transaction still needs confirmation anyway. For this we have to generate another block:

    bitcoin-cli -regtest generate 1

Running `OpReturnSender` again we'll see now a balance greater than zero and sending as many `OP_RETURN` message as we
want works now, too.

To get them confirmed we have to generate another block:

    bitcoin-cli -regtest generate 1

## Query Further Information {#furtherinfo}

Currently there's two helper objects: `WalletInfo`, `BitcoinTransfer` and `TxExplorer`.

`WalletInfo` prints detailed information about our wallet.

`BitcoinTransfer` lets you transfer bitcoins to an address of your choice.

`TxExplorer` prints details about a given list of transaction hashes. These hashes are hardcoded in the object and the
output will look like this.

    ====== TX INFOS ======
    tx info:   a2665771719d8a5f9f30becd37dbb5717cad7597435fb97f01c63f9a44d29e08
      confidence: Seen by 1 peer. Appeared in best chain at height 869210, depth 3.
         in   PUSHDATA(71)[304402204b70989ef386119701d9ba3baffdb7237858a074eb1dafdf64250afc895b81050220229acb00e6878b2fd59960bf5a51bf1ca31350bacd2a7a1ad24823687616b8d901] PUSHDATA(33)[03d5a3397917306fcae6db84b9a7bdd37ab857f6c90d70674af007270c3ea78abd] 8.125 BTC
              outpoint:4384527712c6c71f1084ef04776d38968a32032a4db2dc9d22c13e64afe0a646:0 hash160:88dc81ff22d2fce348316a946e4dbd6aee4be473
         out  DUP HASH160 PUSHDATA(20)[0dcafce42e2e0645fde973509b8806257aa51e8d] EQUALVERIFY CHECKSIG 8.1247755 BTC Spent by 1f464950db252ce49401783d1d099b344a7884027022cc946fc212d3bfa3bf90
         out  DUP HASH160 PUSHDATA(20)[32e1e2ef0c5797293ff835eb8b2e47039ccf1299] EQUALVERIFY CHECKSIG 0.0001 BTC Spent by 4dd5b14c45e25c7c9e75ecc8d9714e8ee37b1f39c2cc46280cfa28bd544566d3
         out  RETURN PUSHDATA(11)[7562697263682d74657374] 0.00 BTC
         fee  0.00050404 BTC/kB, 0.0001245 BTC for 247 bytes
         prps USER_PAYMENT

    tx info:   1f464950db252ce49401783d1d099b344a7884027022cc946fc212d3bfa3bf90
      confidence: Seen by 5 peers. Appeared in best chain at height 869212, depth 1.
         in   PUSHDATA(72)[3045022100c79cb5e8dee4cf02d56d74bb84d7888931a9535570fe5c93120761368659c30802201f001d33aaf20c852cdebb85ed595948ab8e40b2a5dddb9ee5e6e31481763f0301] PUSHDATA(33)[0248984403d72a2ccb64aad5dcdab41845397440e254c56920d86cd62f3231e830] 8.1247755 BTC
              outpoint:a2665771719d8a5f9f30becd37dbb5717cad7597435fb97f01c63f9a44d29e08:0 hash160:0dcafce42e2e0645fde973509b8806257aa51e8d
         out  DUP HASH160 PUSHDATA(20)[67821b0acff0164f2bfbca11adbe65a3bae5b007] EQUALVERIFY CHECKSIG 0.0001 BTC Spent by 4dd5b14c45e25c7c9e75ecc8d9714e8ee37b1f39c2cc46280cfa28bd544566d3
         out  RETURN PUSHDATA(11)[7562697263682d74657374] 0.00 BTC
         out  DUP HASH160 PUSHDATA(20)[67821b0acff0164f2bfbca11adbe65a3bae5b007] EQUALVERIFY CHECKSIG 8.124551 BTC Spent by 4dd5b14c45e25c7c9e75ecc8d9714e8ee37b1f39c2cc46280cfa28bd544566d3
         fee  0.00050201 BTC/kB, 0.0001245 BTC for 248 bytes
         prps USER_PAYMENT

    tx info:   4dd5b14c45e25c7c9e75ecc8d9714e8ee37b1f39c2cc46280cfa28bd544566d3
      confidence: Pending/unconfirmed.
         in   PUSHDATA(71)[304402203a9fba12a5ebc583899bc12ae7b5e45f3493d9d6b8afdf3b993577c8c4e5899402207efc6db34d123b6a7a76642dd7fdf581bc6bc8ec9a118935bf4b4bc89d4dc41801] PUSHDATA(33)[0203c8af1fd621528780e3848938ac8a3eb841f0dde5f961871e61c08a72d4645f] 0.0001 BTC
              outpoint:a2665771719d8a5f9f30becd37dbb5717cad7597435fb97f01c63f9a44d29e08:1 hash160:32e1e2ef0c5797293ff835eb8b2e47039ccf1299
         in   PUSHDATA(71)[304402203b39461d019dba9406f48c8b1b8941d933a3a34686c6b7826ed44d43ee2bcb25022076bdffdf68de8a7a96f79167598dcf1790bb5bb865ed03ae6dc890ee0efc2a6a01] PUSHDATA(33)[0213b4609f90044d66151479374eb42405a660dfd0daa58e473996330b19ad31ee] 0.0001 BTC
              outpoint:1f464950db252ce49401783d1d099b344a7884027022cc946fc212d3bfa3bf90:0 hash160:67821b0acff0164f2bfbca11adbe65a3bae5b007
         in   PUSHDATA(72)[3045022100b97150ed4115e4d137a1b0a223adcd54d642f13c050a7771583261235724cd9b02201d5459413aacb58ada2541e051d2a7bbe2dec3109b4a765fdc8eff335f0fec2801] PUSHDATA(33)[0213b4609f90044d66151479374eb42405a660dfd0daa58e473996330b19ad31ee] 8.124551 BTC
              outpoint:1f464950db252ce49401783d1d099b344a7884027022cc946fc212d3bfa3bf90:2 hash160:67821b0acff0164f2bfbca11adbe65a3bae5b007
         out  RETURN PUSHDATA(11)[7562697263682d74657374] 0.00 BTC
         out  DUP HASH160 PUSHDATA(20)[339e77550ea46075d144218df8a90cbc71256554] EQUALVERIFY CHECKSIG 0.0001 BTC
         out  DUP HASH160 PUSHDATA(20)[339e77550ea46075d144218df8a90cbc71256554] EQUALVERIFY CHECKSIG 8.1243775 BTC
         fee  0.00050461 BTC/kB, 0.0002735 BTC for 542 bytes
         prps USER_PAYMENT

    ====== UTXO LIST ======
    TxOut of 0.0001 BTC to mkDtbFutXAddmKp5fEwwZnaybPa7Qg5agh script:DUP HASH160 PUSHDATA(20)[339e77550ea46075d144218df8a90cbc71256554] EQUALVERIFY CHECKSIG
    TxOut of 8.1243775 BTC to mkDtbFutXAddmKp5fEwwZnaybPa7Qg5agh script:DUP HASH160 PUSHDATA(20)[339e77550ea46075d144218df8a90cbc71256554] EQUALVERIFY CHECKSIG

    ====== TX BY TIME LIST ======
      4dd5b14c45e25c7c9e75ecc8d9714e8ee37b1f39c2cc46280cfa28bd544566d3
      confidence: Pending/unconfirmed.
         in   PUSHDATA(71)[304402203a9fba12a5ebc583899bc12ae7b5e45f3493d9d6b8afdf3b993577c8c4e5899402207efc6db34d123b6a7a76642dd7fdf581bc6bc8ec9a118935bf4b4bc89d4dc41801] PUSHDATA(33)[0203c8af1fd621528780e3848938ac8a3eb841f0dde5f961871e61c08a72d4645f] 0.0001 BTC
              outpoint:a2665771719d8a5f9f30becd37dbb5717cad7597435fb97f01c63f9a44d29e08:1 hash160:32e1e2ef0c5797293ff835eb8b2e47039ccf1299
         in   PUSHDATA(71)[304402203b39461d019dba9406f48c8b1b8941d933a3a34686c6b7826ed44d43ee2bcb25022076bdffdf68de8a7a96f79167598dcf1790bb5bb865ed03ae6dc890ee0efc2a6a01] PUSHDATA(33)[0213b4609f90044d66151479374eb42405a660dfd0daa58e473996330b19ad31ee] 0.0001 BTC
              outpoint:1f464950db252ce49401783d1d099b344a7884027022cc946fc212d3bfa3bf90:0 hash160:67821b0acff0164f2bfbca11adbe65a3bae5b007
         in   PUSHDATA(72)[3045022100b97150ed4115e4d137a1b0a223adcd54d642f13c050a7771583261235724cd9b02201d5459413aacb58ada2541e051d2a7bbe2dec3109b4a765fdc8eff335f0fec2801] PUSHDATA(33)[0213b4609f90044d66151479374eb42405a660dfd0daa58e473996330b19ad31ee] 8.124551 BTC
              outpoint:1f464950db252ce49401783d1d099b344a7884027022cc946fc212d3bfa3bf90:2 hash160:67821b0acff0164f2bfbca11adbe65a3bae5b007
         out  RETURN PUSHDATA(11)[7562697263682d74657374] 0.00 BTC
         out  DUP HASH160 PUSHDATA(20)[339e77550ea46075d144218df8a90cbc71256554] EQUALVERIFY CHECKSIG 0.0001 BTC
         out  DUP HASH160 PUSHDATA(20)[339e77550ea46075d144218df8a90cbc71256554] EQUALVERIFY CHECKSIG 8.1243775 BTC
         fee  0.00050461 BTC/kB, 0.0002735 BTC for 542 bytes
         prps USER_PAYMENT

      1f464950db252ce49401783d1d099b344a7884027022cc946fc212d3bfa3bf90
      confidence: Seen by 5 peers. Appeared in best chain at height 869212, depth 1.
         in   PUSHDATA(72)[3045022100c79cb5e8dee4cf02d56d74bb84d7888931a9535570fe5c93120761368659c30802201f001d33aaf20c852cdebb85ed595948ab8e40b2a5dddb9ee5e6e31481763f0301] PUSHDATA(33)[0248984403d72a2ccb64aad5dcdab41845397440e254c56920d86cd62f3231e830] 8.1247755 BTC
              outpoint:a2665771719d8a5f9f30becd37dbb5717cad7597435fb97f01c63f9a44d29e08:0 hash160:0dcafce42e2e0645fde973509b8806257aa51e8d
         out  DUP HASH160 PUSHDATA(20)[67821b0acff0164f2bfbca11adbe65a3bae5b007] EQUALVERIFY CHECKSIG 0.0001 BTC Spent by 4dd5b14c45e25c7c9e75ecc8d9714e8ee37b1f39c2cc46280cfa28bd544566d3
         out  RETURN PUSHDATA(11)[7562697263682d74657374] 0.00 BTC
         out  DUP HASH160 PUSHDATA(20)[67821b0acff0164f2bfbca11adbe65a3bae5b007] EQUALVERIFY CHECKSIG 8.124551 BTC Spent by 4dd5b14c45e25c7c9e75ecc8d9714e8ee37b1f39c2cc46280cfa28bd544566d3
         fee  0.00050201 BTC/kB, 0.0001245 BTC for 248 bytes
         prps USER_PAYMENT

      a2665771719d8a5f9f30becd37dbb5717cad7597435fb97f01c63f9a44d29e08
      confidence: Seen by 1 peer. Appeared in best chain at height 869210, depth 3.
         in   PUSHDATA(71)[304402204b70989ef386119701d9ba3baffdb7237858a074eb1dafdf64250afc895b81050220229acb00e6878b2fd59960bf5a51bf1ca31350bacd2a7a1ad24823687616b8d901] PUSHDATA(33)[03d5a3397917306fcae6db84b9a7bdd37ab857f6c90d70674af007270c3ea78abd] 8.125 BTC
              outpoint:4384527712c6c71f1084ef04776d38968a32032a4db2dc9d22c13e64afe0a646:0 hash160:88dc81ff22d2fce348316a946e4dbd6aee4be473
         out  DUP HASH160 PUSHDATA(20)[0dcafce42e2e0645fde973509b8806257aa51e8d] EQUALVERIFY CHECKSIG 8.1247755 BTC Spent by 1f464950db252ce49401783d1d099b344a7884027022cc946fc212d3bfa3bf90
         out  DUP HASH160 PUSHDATA(20)[32e1e2ef0c5797293ff835eb8b2e47039ccf1299] EQUALVERIFY CHECKSIG 0.0001 BTC Spent by 4dd5b14c45e25c7c9e75ecc8d9714e8ee37b1f39c2cc46280cfa28bd544566d3
         out  RETURN PUSHDATA(11)[7562697263682d74657374] 0.00 BTC
         fee  0.00050404 BTC/kB, 0.0001245 BTC for 247 bytes
         prps USER_PAYMENT

      4384527712c6c71f1084ef04776d38968a32032a4db2dc9d22c13e64afe0a646
      confidence: Appeared in best chain at height 868892, depth 321.
         in   PUSHDATA(71)[3044022000d6285f2a5dd1cc59b4f1b6e79745833d89e280d86b7e185bfebce4e1375fe302200590662283fab4d7c17ee989d1cfb5275c23f79a790d8be6991361d4a203680201] PUSHDATA(33)[0214eb700a27bd7a9215c035a9a53a27e078e21c20cd8c8d150d5aa58391bff82c]
              outpoint:e815856663d8c5f74135fc228ac5e9bfab0d0e31b5e28a9720017d60f63e7bd9:1
         out  DUP HASH160 PUSHDATA(20)[88dc81ff22d2fce348316a946e4dbd6aee4be473] EQUALVERIFY CHECKSIG 8.125 BTC Spent by a2665771719d8a5f9f30becd37dbb5717cad7597435fb97f01c63f9a44d29e08
         out  DUP HASH160 PUSHDATA(20)[5dd2982a85cdfa3e6b4fad31c0e6183b9dc91b4a] EQUALVERIFY CHECKSIG 976.50592187 BTC
         prps UNKNOWN


## Switching to `testnet` {#testnet-switch}

To use `testnet` change all calls to `walletAppKit()` to:

    walletAppKit(TEST_NET)

There's a patch file doing just this: `switch_to_testnet.patch`.

To get BTCs you can use [one of the faucets](https://en.bitcoin.it/wiki/Testnet#Faucets). And there's `testnet` [blockchain
explorers](https://en.bitcoin.it/wiki/Testnet#Block_explorers), too.


## Clean Up {#clean-up}

There's two components we can clean up the blockchain itself and the wallet used by our Scala objects.

To reset the blockchain:

    rm -rf ~/Library/Application Support/Bitcoin/regtest

To delete the wallet simply remove the `wallet-*.*` files in this project's folder. The wallet directory and file
prefixes are defined in `com.ubirch.playground.bitcoin.util.BitcoinUtil.walletAppKit`.


## Additional Information {#additional}

The [Bitcoin Developer Reference](https://bitcoin.org/en/developer-reference#rpcs) includes a list of all `bitcoin-cli`
commands available.

To list all transaction related to the miner run the following command (unfortunately we use a separate wallet so our
`OP_RETURN` transactions are not listed).

    bitcoin-cli -regtest listtransactions
