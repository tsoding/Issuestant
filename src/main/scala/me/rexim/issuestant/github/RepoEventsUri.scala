package me.rexim.issuestant.github

import scalaz._
import org.http4s._

class RepoEventsUri(owner: String, repo: String) {
  private lazy val \/-(githubApiUri) =
    Uri.fromString("https://api.github.com/").map { baseUri =>
      baseUri / "repos" / owner / repo / "events"
    }

  def asUri: Uri = githubApiUri
}
