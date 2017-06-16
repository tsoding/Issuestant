package me.rexim.issuestant.github

import org.scalatest._
import me.rexim.issuestant.mock._

import org.http4s._
import org.http4s.client._
import org.http4s.util._

class EtagPollingSpec extends FlatSpec with Matchers {
  behavior of "EtagPolling"

  it should "reuse etag header for the service response" in {
    // TODO(#54): Use org.http4s.headers._ everywhere instead of CaseInsensitiveString as header name
    val etagName = CaseInsensitiveString("Etag")
    val ifNoneMatchName = CaseInsensitiveString("If-None-Match")

    val requestCount = 5
    val requestUri = Uri(path = "/rexim")

    val etagLogging = new EtagLogging
    val etagPolling = new EtagPolling(
      etagLogging.client,
      requestUri
    )

    val responses = etagPolling.responses.take(requestCount).runLog.run.toList
    val requests = etagLogging.log

    requests.map(_.uri) should be ((1 to requestCount).map(_ => requestUri))

    (None :: responses.map(_.headers.get(etagName).map(_.value)).init) should
      be (requests.map(_.headers.get(ifNoneMatchName).map(_.value)))
  }
}
