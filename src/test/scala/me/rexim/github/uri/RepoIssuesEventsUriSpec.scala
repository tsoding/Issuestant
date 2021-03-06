package me.rexim.issuestant.github.uri

import scalaz._
import org.scalatest._
import org.http4s._

class RepoIssuesEventsUriSpec extends FlatSpec with Matchers {
  behavior of "RepoIssuesEventsUri"

  it should "construct a proper GitHub API v3 URI" in {
    \/-(new RepoIssuesEventsUri("tsoding", "issuestant").asUri) should
      be (Uri.fromString("https://api.github.com/repos/tsoding/issuestant/issues/events"))
  }

  it should "properly encode the arguments injected into the URI" in {
    \/-(new RepoIssuesEventsUri("&#*$#", "///348\\\\").asUri) should
      be (Uri.fromString("https://api.github.com/repos/&%23*$%23/%2F%2F%2F348%5C%5C/issues/events"))
  }
}
