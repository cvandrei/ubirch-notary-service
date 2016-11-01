# ubirch Notary Service

## General Information

This REST service allows us to notarize data using the Bitcoin Blockchain. The REST API is documented here: 
[REST API with spray](./rest-spray.html).

## Scala Dependencies & Configs

### `model`

    resolvers ++= Seq(Resolver.sonatypeRepo("snapshots"))
    libraryDependencies ++= Seq(
      "com.ubirch.notary" %% "model" % "0.2.3"
    )

### `core`

    resolvers ++= Seq(
      Resolver.sonatypeRepo("snapshots"),
      "RoundEights" at "http://maven.spikemark.net/roundeights" // Hasher
    )
    libraryDependencies ++= Seq(
      "com.ubirch.notary" %% "core" % "0.2.3"
    )

### `server`

    resolvers ++= Seq(Resolver.sonatypeRepo("snapshots"))
    libraryDependencies ++= Seq(
      "com.ubirch.notary" %% "server" % "0.2.3"
    )

### `client`

    resolvers ++= Seq(
      Resolver.sonatypeRepo("snapshots"),
      Resolver.bintrayRepo("hseeberger", "maven"), // Seeberger Json
      Resolver.bintrayRepo("rick-beton", "maven"), // BeeClient
      "RoundEights" at "http://maven.spikemark.net/roundeights" // Hasher
    )
    libraryDependencies ++= Seq(
      "com.ubirch.notary" %% "client" % "0.2.3"
    )

You may configure which NotaryService the client calls by adding the test server for example to your config:

    notaryService {
      client {
        url = "http://ubirchnotaryservice-env.us-east-1.elasticbeanstalk.com/v1/notary/notarize"
      }
    }

## Release History

### 0.2.3 (2016-11-01)

  * add Dockerfile generation
  * update to sbt 0.13.12

### 0.2.2 (2016-11-01)

  * added Tor Support
  * refactored module structure to conform with our coding conventions

## Docker

This service can be run in a Docker container. Running the following shell script will generate a Dockerfile in the
project's root folder.

    ./generate-dockerfile.sh

## Links

A list of links that helped us with the OP_RETURN message:

* [OP_RETURN and the Future of Bitcoin](http://bitzuma.com/posts/op-return-and-the-future-of-bitcoin/)
* [Explanation of what an OP_RETURN transaction looks like](https://bitcoin.stackexchange.com/questions/29554/explanation-of-what-an-op-return-transaction-looks-like)
* [bitcoin.it Wiki: OP_RETURN](https://en.bitcoin.it/wiki/OP_RETURN)
* [How to put custom messages into Bitcoin blockchain – OP_RETURN](https://www.wlangiewicz.com/2014/10/24/how-to-put-custom-messages-into-bitcoin-blockchain-op_return/)
* [bitcoinj Mailinglist: is OP_RETURN available in bitcoinj?](https://groups.google.com/forum/?fromgroups#!topic/bitcoinj/766ZhvJjIqM)

Faucets:

* [Mainnet and Testnet Faucets](https://en.bitcoin.it/wiki/List_of_faucets)
