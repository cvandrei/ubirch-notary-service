import sbt.Resolver

packagedArtifacts in file(".") := Map.empty // disable publishing of root project

lazy val testConfiguration = "-Dconfig.resource=" + Option(System.getProperty("test.config")).getOrElse("application.dev.conf")

lazy val commonSettings = Seq(

  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-feature"),

  version := "0.3.0-SNAPSHOT",

  organization := "com.ubirch.notary",
  homepage := Some(url("http://ubirch.com")),
  scmInfo := Some(ScmInfo(
    url("https://github.com/ubirch/ubirch-notary-service"),
    "https://github.com/ubirch/ubirch-notary-service"
  )),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("snapshots"),
    Resolver.bintrayRepo("rick-beton", "maven"),
    Resolver.bintrayRepo("hseeberger", "maven")
  ),

  javaOptions in Test += testConfiguration

)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .aggregate(server, model, core, client)

lazy val server = project
  .settings(commonSettings: _*)
  .dependsOn(model, core)
  .settings(
    mainClass in assembly := Some("com.ubirch.notary.Boot"),
    libraryDependencies ++= depServer
  )

lazy val model = project
  .settings(commonSettings: _*)

lazy val core = project
  .settings(commonSettings: _*)
  .dependsOn(model)
  .settings(
    libraryDependencies ++= depCore
  )

lazy val client = project
  .settings(commonSettings: _*)
  .dependsOn(model)
  .settings(
    libraryDependencies ++= depClientRest
  )

val akkaV = "2.3.9"
val sprayV = "1.3.3"
val json4sV = "3.4.0"
val scalaTestV = "3.0.0"

lazy val depServer = Seq(

  bitcoinj,

  // Spray
  "io.spray" %% "spray-can" % sprayV,
  "io.spray" %% "spray-routing" % sprayV,
  "io.spray" %% "spray-client" % sprayV,
  "org.json4s" %% "json4s-jackson" % "3.2.10",

  // logging and config
  "com.typesafe.akka" %% "akka-actor" % akkaV,
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "ch.qos.logback" % "logback-core" % "1.1.7",
  "net.logstash.logback" % "logstash-logback-encoder" % "4.3",
  "org.slf4j" % "slf4j-api" % "1.7.12",
  typesafeLogging,

  // misc
  "joda-time" % "joda-time" % "2.9.3"

)

lazy val depCore = Seq(
  bitcoinj,
  ubirchUtilCrypto,
  ubirchUtilConfig,
  typesafeLogging
)

lazy val depClientRest = {
  Seq(
    "uk.co.bigbeeconsultants" %% "bee-client" % "0.29.1",
    ubirchUtilConfig,
    typesafeLogging,
    json4sNative,
    json4sExt,
    json4sJackson,
    ubirchUtilCrypto % "test",
    scalaTest
  )
}

lazy val bitcoinj = "org.bitcoinj" % "bitcoinj-core" % "0.14.2" % "compile"

lazy val typesafeLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0"

lazy val json4sNative = "org.json4s" %% "json4s-native" % json4sV

lazy val json4sExt = "org.json4s" %% "json4s-ext" % json4sV

lazy val json4sJackson = "org.json4s" %% "json4s-jackson" % json4sV

lazy val ubirchUtilConfig = "com.ubirch.util" %% "config" % "0.1-SNAPSHOT"
lazy val ubirchUtilCrypto = "com.ubirch.util" %% "crypto" % "0.2-SNAPSHOT"

lazy val scalaTest = "org.scalatest" %% "scalatest" % scalaTestV % "test"