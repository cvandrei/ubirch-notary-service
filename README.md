# ubirch Notary Service

## General Information

This REST service allows us to notarize data using the Bitcoin Blockchain. The REST API is documented here: 
[REST API with spray](./rest-spray.html).

## Scala Dependencies & Configs

### `model`

```scala
resolvers ++= Seq(Resolver.sonatypeRepo("snapshots"))
libraryDependencies ++= Seq(
  "com.ubirch.notary" %% "model" % "0.2.5"
)
```

### `core`

```scala
resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  "RoundEights" at "http://maven.spikemark.net/roundeights" // Hasher
)
libraryDependencies ++= Seq(
  "com.ubirch.notary" %% "core" % "0.2.5"
)
```

### `server`

```scala
resolvers ++= Seq(Resolver.sonatypeRepo("snapshots"))
libraryDependencies ++= Seq(
  "com.ubirch.notary" %% "server" % "0.2.5"
)
```

### `client`

```scala
resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.bintrayRepo("hseeberger", "maven"), // Seeberger Json
  Resolver.bintrayRepo("rick-beton", "maven"), // BeeClient
  "RoundEights" at "http://maven.spikemark.net/roundeights" // Hasher
)
libraryDependencies ++= Seq(
  "com.ubirch.notary" %% "client" % "0.2.5"
)
```

You may configure which NotaryService the client calls by adding the test server for example to your config:

    notaryService {
      client {
        url = "http://ubirchnotaryservice-env.us-east-1.elasticbeanstalk.com/v1/notary/notarize"
      }
    }

## Release History

### 0.2.5 (2016-11-18)

- changed log imports to: `com.typesafe.scalalogging.slf4j.StrictLogging`
- update logging dependencies to our current standard set:
  
```scala
lazy val scalaLogging = Seq(
  "org.slf4j" % "slf4j-api" % "1.7.21",
  "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2" exclude("org.slf4j", "slf4j-api"),
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0" exclude("org.slf4j", "slf4j-api"),
  "ch.qos.logback" % "logback-core" % "1.1.7",
  "ch.qos.logback" % "logback-classic" % "1.1.7"
)
```

### 0.2.4 (2016-11-14)

  * use StrictLogging instead of LazyLogging
  * update dependency com.typesafe.scala-logging:scala-logging: 3.4.0 -> 3.5.0

### 0.2.3 (2016-11-01)

  * add Dockerfile generation
  * update to sbt 0.13.12

### 0.2.2 (2016-11-01)

  * added Tor Support
  * refactored module structure to conform with our coding conventions

## Docker

This service can be run in a Docker container. Running the following shell script will generate a Dockerfile in the
project's root folder.

```
./generate-dockerfile.sh
```

## Links

A list of links that helped us with the OP_RETURN message:

* [OP_RETURN and the Future of Bitcoin](http://bitzuma.com/posts/op-return-and-the-future-of-bitcoin/)
* [Explanation of what an OP_RETURN transaction looks like](https://bitcoin.stackexchange.com/questions/29554/explanation-of-what-an-op-return-transaction-looks-like)
* [bitcoin.it Wiki: OP_RETURN](https://en.bitcoin.it/wiki/OP_RETURN)
* [How to put custom messages into Bitcoin blockchain – OP_RETURN](https://www.wlangiewicz.com/2014/10/24/how-to-put-custom-messages-into-bitcoin-blockchain-op_return/)
* [bitcoinj Mailinglist: is OP_RETURN available in bitcoinj?](https://groups.google.com/forum/?fromgroups#!topic/bitcoinj/766ZhvJjIqM)

Faucets:

* [Mainnet and Testnet Faucets](https://en.bitcoin.it/wiki/List_of_faucets)
