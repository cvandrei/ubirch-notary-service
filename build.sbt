lazy val commonSettings = Seq(
  version := "0.2.0-SNAPSHOT",
  scalaVersion := "2.11.8",
  organization := "com.ubirch"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "bitcoinTxEval",
    mainClass in assembly := Some("com.ubirch.notary.Boot"),
    libraryDependencies ++= {
      val akkaV = "2.3.9"
      val sprayV = "1.3.3"
      Seq(

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
        "joda-time" % "joda-time" % "2.9.3"

      )
    }
  )