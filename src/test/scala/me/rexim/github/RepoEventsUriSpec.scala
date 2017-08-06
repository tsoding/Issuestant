package me.rexim.issuestant.github

import me.rexim.issuestant.github.uri._

import scalaz._
import org.scalatest._
import org.http4s._

class RepoEventsUriSpec extends FlatSpec with Matchers {
  behavior of "RepoEventsUri"

  it should "construct a proper GitHub API v3 URI" in {
    \/-(new RepoEventsUri("tsoding", "issuestant").asUri) should
      be (Uri.fromString("https://api.github.com/repos/tsoding/issuestant/events"))
  }

  it should "properly encode the arguments injected into the URI" in {
    \/-(new RepoEventsUri("&#*$#", "///348\\\\").asUri) should
      be (Uri.fromString("https://api.github.com/repos/&%23*$%23/%2F%2F%2F348%5C%5C/events"))
  }
}
