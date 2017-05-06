package me.rexim.issuestant.github

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scalaz.stream._
import scalaz.concurrent._
import me.rexim.issuestant.github.model.Event

import org.http4s._
import org.http4s.client._
import org.http4s.Http4s._
import org.http4s.Status._
import org.http4s.Method._
import org.http4s.EntityDecoder

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
  // TODO(4369af78-08f6-45d7-8399-c5ef1f97808a): Get rid of wart suppress
  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  def events: Process[Task, Event] =
    Process
      .iterateEval(Response())(pollingIteration)
      .flatMap((response) => extractEvents(response))

  // TODO(e4581c14-8db7-4e1b-90f0-0b8faec52a33): Implement EventsSource.pollingIteration
  //
  // Extract ETag from the previousResponse and use it for polling the next events
  private def pollingIteration(previousResponse: Response): Task[Response] = ???

  // TODO(6900c3e4-7c15-42cc-9389-de3947625ce9): Implement EventsSource.extractEvents
  private def extractEvents(response: Response): Process[Task, Event] = ???
}
// $COVERAGE-ON$
