package me.rexim.issuestant

import org.scalatest._
import org.http4s._

import HelloService._

class HelloServiceSpec extends FlatSpec with Matchers {
  behavior of "HelloService"

  it should "return hello message on GET /hello" in {
    val message = service(new Request(uri = Uri(path = "/hello")))
      .flatMap(_.as[HelloService.Message])
      .run

    message should be (HelloService.Message("hello"))
  }
}
