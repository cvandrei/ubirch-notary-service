# ubirch Notary Service

## General Information

This REST service allows us to notarize data using the Bitcoin Blockchain. The REST API is documented here: 
[REST API with spray](./rest-spray.html).

## Scala Dependencies & Configs

### `model`

    resolvers ++= Seq(Resolver.sonatypeRepo("snapshots"))
    libraryDependencies ++= Seq(
      "com.ubirch.notary" %% "model" % "0.3.0-SNAPSHOT"
    )

### `server`

    resolvers ++= Seq(Resolver.sonatypeRepo("snapshots"))
    libraryDependencies ++= Seq(
      "com.ubirch.notary" %% "server" % "0.3.0-SNAPSHOT"
    )

### `client`

    resolvers ++= Seq(Resolver.sonatypeRepo("snapshots") )
    libraryDependencies ++= Seq(
      "com.ubirch.notary" %% "client" % "0.3.0-SNAPSHOT"
    )

You may configure which NotaryService the client calls by adding the test server for example to your config:

    notary {
      client {
        serverUrl = "http://ubirchnotaryservice-env.us-east-1.elasticbeanstalk.com:8080/v1/notary/notarize"
      }
    }

## Links

A list of links that helped us with the OP_RETURN message:

* [OP_RETURN and the Future of Bitcoin](http://bitzuma.com/posts/op-return-and-the-future-of-bitcoin/)
* [Explanation of what an OP_RETURN transaction looks like](https://bitcoin.stackexchange.com/questions/29554/explanation-of-what-an-op-return-transaction-looks-like)
* [bitcoin.it Wiki: OP_RETURN](https://en.bitcoin.it/wiki/OP_RETURN)
* [How to put custom messages into Bitcoin blockchain – OP_RETURN](https://www.wlangiewicz.com/2014/10/24/how-to-put-custom-messages-into-bitcoin-blockchain-op_return/)
* [bitcoinj Mailinglist: is OP_RETURN available in bitcoinj?](https://groups.google.com/forum/?fromgroups#!topic/bitcoinj/766ZhvJjIqM)
