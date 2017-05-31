package me.rexim.issuestant.github

import org.scalatest._
import me.rexim.issuestant.mock._

import org.http4s._
import org.http4s.client._

class EtagPollingSpec extends FlatSpec with Matchers {
  behavior of "EtagPolling"

  it should "reuse etag header for the service response" in {
    val etagLogging = new EtagLogging
    val etagPolling = new EtagPolling(
      etagLogging.client,
      Uri(path = "/rexim")
    )

    val responses = etagPolling.responses.take(5).runLog.run.toList
    val requests = etagLogging.log

    (Nil :: responses.map(_.headers.map(_.value)).init) should be (requests.map(_.headers.map(_.value)))
  }
}
