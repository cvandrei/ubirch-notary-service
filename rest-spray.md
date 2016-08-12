# Notary Service REST API with Spray

This documentation attempts to list all relevant information for the __Notary Service__ REST API.

* [Configuration](#config)
* [Available Methods](#methods)
* [Curl Calls](#curl)

## Configuration {#config}

The configuration is done in the `application[.*].conf` files and always loaded through `com.ubirch.notary.config.AppConfig.config`.
By default `application.conf` is used when we want another one we can configure a system property (`-Dubirch.env=application.dev`)
or an environment variable (`export ubirch.env=application.dev`). If using neither system properties nor enviroment variables
is an option the deployed jar may contain an `application.conf` matching the environment.

In the future we might extend this mechanism to load the config from an external source (e.g. DynamoDB; see 
com.ubirch.notary.config.AppConfig.dynamicConf for the current test implementation).

The following configuration key are available.

### Network

    notaryService.interface
    notaryService.port

### Bitcoin

Bitcoin connects to a network (`org.bitcoin.regtest`, `org.bitcoin.test` or `org.bitcoin.production`).

    bitcoin.network

There's also a wallet. If none is found a new one is generated in the specified location.

    bitcoin.wallet.directory
    bitcoin.wallet.prefix

The amount sent along with the `OP_RETURN` has to cover the transaction fees and if wanted we can configure it, too. The
higher it is the sooner our transaction will be included in the next block but if it's too low it might never be
processed see [https://bitcoinj.github.io/working-with-the-wallet#using-fees](https://bitcoinj.github.io/working-with-the-wallet#using-fees) for details.

    bitcoin.feePerKb

### Spray

There should not be much of a need to change the spray configuration but if there is `application.base.conf` currently
contains it.


## Available Methods {#methods}

The service has three main routes.

### Wallet Information

    GET /v1/notary/wallet-info

### Transactions

    GET /v1/notary/transactions
    GET /v1/notary/transactions/unspent
    GET /v1/notary/transactions/pending

### Publish Data on Blockchain

    POST /v1/notary/notarize

An example for the JSON we may POST (with `Header: Content-Type: application/json`).

```{.json}
{
  "data": "ubirch-test", // BitcoinJ allows up to 40 bytes
  "signature": "$SIGNATURE", // (optional) signature of "data" (will become mandatory at some point)
  "publicKey": "$PUBLIC_KEY" // (optional) publicKey allowing us to verify the signature; we also have to trust it (will become mandatory at some point)
}
```

## `curl` Calls {#curl}

### Wallet Information

    curl -k "http://localhost:8080/v1/notary/wallet-info"

### Transactions

    curl -k "http://localhost:8080/v1/notary/transactions"
    curl -k "http://localhost:8080/v1/notary/transactions/unspent"
    curl -k "http://localhost:8080/v1/notary/transactions/pending"

### Publish Data on Blockchain

    curl -X POST -H "Content-Type: application/json" -k "http://localhost:8080/v1/notary/notarize" -d '{"Notarize": { "payload": "ubirch-test", "signature": "sig1", "publicKey": "pubKey"} }'
