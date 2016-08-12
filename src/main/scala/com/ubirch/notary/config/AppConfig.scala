package com.ubirch.notary.config

import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters._

/**
  * author: cvandrei
  * since: 2016-06-13
  */
object AppConfig {

  private val envKey = "ubirch.env"

  val config: Config = {

    getEnvKey match {

      case Some(confFile) => ConfigFactory.load(confFile)

      case None =>
//        dynamicConf // TODO activate once the method has been fully implemented
        ConfigFactory.load()

    }

  }

  private def getEnvKey: Option[String] = {

    System.getProperties.asScala.get(envKey) match {

      case Some(value) => Some(value)

      case None =>

        System.getenv.asScala.get(envKey) match {

          case Some(value) => Some(value)
          case None => None

        }

    }

  }

  private def dynamicConf: Config = {

    // TODO load config as string from some db maybe or through ConfigFactory.parseFile() from a File
    val applicationConf = ConfigFactory.load()
    ConfigFactory.parseString(
      """{
        |  bitcoin {
        |    wallet {
        |      directory = "/Users/cvandrei/"
        |    }
        |  }
        |}
      """.stripMargin
    ).withFallback(applicationConf)

  }

}
