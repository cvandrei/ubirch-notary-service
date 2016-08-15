packagedArtifacts in file(".") := Map.empty // disable publishing of root project

lazy val commonSettings = Seq(

  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-feature"),

  version := "0.3.0-SNAPSHOT",

  organization := "com.ubirch.notary",
  homepage := Some(url("https://github.com/ubirch/ubirch-notary-service")),
  scmInfo := Some(ScmInfo(
    url("https://github.com/ubirch/ubirch-notary-service"),
    "https://github.com/ubirch/ubirch-notary-service"
  )),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("snapshots")
  )

)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .aggregate(server, json)

lazy val server = project
  .settings(commonSettings: _*)
  .dependsOn(json)
  .settings(
    name := "backend",
    mainClass in assembly := Some("com.ubirch.notary.Boot"),
    libraryDependencies ++= depBackend
  )

lazy val json = project
  .settings(commonSettings: _*)
  .settings(
    name := "json"
  )

val akkaV = "2.3.9"
val sprayV = "1.3.3"

lazy val depBackend = Seq(

  "org.bitcoinj" % "bitcoinj-core" % "0.14.2" % "compile",

  // Spray
  "io.spray" %% "spray-can" % sprayV,
  "io.spray" %% "spray-routing" % sprayV,
  "io.spray" %% "spray-client" % sprayV,
  "org.json4s" %% "json4s-jackson" % "3.2.10",

  // logging and config
  "com.typesafe.akka" %% "akka-actor" % akkaV,
  "com.typesafe" % "config" % "1.3.0",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "ch.qos.logback" % "logback-core" % "1.1.7",
  "net.logstash.logback" % "logstash-logback-encoder" % "4.3",
  "org.slf4j" % "slf4j-api" % "1.7.12",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0",

  // misc
  "joda-time" % "joda-time" % "2.9.3",

  // ubirch
  "com.ubirch.util" %% "crypto" % "0.2-SNAPSHOT"

)
