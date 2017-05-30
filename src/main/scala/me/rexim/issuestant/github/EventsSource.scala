package me.rexim.issuestant.github

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scalaz.stream._
import scalaz.concurrent._
import me.rexim.issuestant.github.model.Event

import org.http4s._
import org.http4s.util._
import org.http4s.client._
import org.http4s.Http4s._
import org.http4s.Status._
import org.http4s.Method._
import org.http4s.EntityDecoder
import scalaz._

/** The source of GitHub events
  *
  * The class is stateless. The next state is returned by nextEvents()
  * method. The state should be managed by the invoker.
  *
  * @param client an http client for requesting GitHub API
  * @param owner the owner of the repo
  * @param repo the name of the repo
  */
// $COVERAGE-OFF$
class EventsSource (client: Client, owner: String, repo: String) {

  private lazy val \/-(githubApiUri) = Uri.fromString(s"https://api.github.com/repos/$owner/$repo/issues/events")

  // TODO(#46): Get rid of wart suppress
  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  def events: Process[Task, Event] = responses.flatMap(extractEvents)

  private def responses: Process[Task, Response] =
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

  def pollingIteration(previousResponse: Response): Task[Response] =
    client.fetch[Response](nextRequest(previousResponse))((x) => Task(x))

  // TODO(#48): Implement EventsSource.extractEvents
  private def extractEvents(response: Response): Process[Task, Event] = ???
}
// $COVERAGE-ON$
