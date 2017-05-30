package me.rexim.issuestant.github

import org.http4s._
import org.http4s.client._
import org.http4s.util._

import scalaz._
import scalaz.stream._
import scalaz.concurrent._

class EtagPolling(client: Client, owner: String, repo: String) {
  private lazy val \/-(githubApiUri) = Uri.fromString(s"https://api.github.com/repos/$owner/$repo/issues/events")

  def responses: Process[Task, Response] =
    Process.iterateEval(Response())(pollingIteration)

  private def getETag(response: Response): Option[String] =
    response.headers.get(CaseInsensitiveString("ETag")).map(_.value)

  private def nextRequest(previousResponse: Response): Request =
    Request (
      uri = githubApiUri,
      headers =
        getETag(previousResponse)
          .map(e => Headers(Header("If-None-Match", e)))
          .getOrElse(Headers.empty)
    )

  private def pollingIteration(previousResponse: Response): Task[Response] =
    client.fetch[Response](nextRequest(previousResponse))((x) => Task(x))
}
