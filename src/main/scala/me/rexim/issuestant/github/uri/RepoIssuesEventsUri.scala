package me.rexim.issuestant.github.uri

import scalaz._
import org.http4s._

class RepoIssuesEventsUri(owner: String, repo: String) {
  private lazy val \/-(githubApiUri) =
    Uri.fromString("https://api.github.com/").map { baseUri =>
      baseUri / "repos" / owner / repo / "issues"/ "events"
    }

  def asUri: Uri = githubApiUri
}
