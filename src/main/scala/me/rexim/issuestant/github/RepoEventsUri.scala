package me.rexim.issuestant.github

import scalaz._
import org.http4s._

class RepoEventsUri(owner: String, repo: String) {
  private lazy val \/-(githubApiUri) =
    Uri.fromString(s"https://api.github.com/repos/$owner/$repo/issues/events")

  def asUri: Uri = githubApiUri
}
