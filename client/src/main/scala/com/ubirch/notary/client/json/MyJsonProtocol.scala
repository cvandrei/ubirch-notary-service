package com.ubirch.notary.client.json

import org.json4s.ext.JavaTypesSerializers
import org.json4s.ext.JodaTimeSerializers
import org.json4s.{DefaultFormats, Formats, jackson}

/**
  * author: cvandrei
  * since: 2016-08-15
  */
trait MyJsonProtocol {

  implicit val serialization = jackson.Serialization // or native.Serialization

  implicit def json4sJacksonFormats: Formats = DefaultFormats ++ JavaTypesSerializers.all ++ JodaTimeSerializers.all

}
