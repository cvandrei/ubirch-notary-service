package com.ubirch.notary.directives

import spray.routing.Directives

/**
  * author: cvandrei
  * since: 2016-05-20
  */
trait UriPathDirective extends Directives {

  def uriPath = extract(_.request.uri.path)

}
