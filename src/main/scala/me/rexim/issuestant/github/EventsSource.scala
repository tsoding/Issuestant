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
class EventsSource (etagPolling: EtagPolling) {
  def events: Process[Task, Event] = etagPolling.responses.flatMap(extractEvents)

  // TODO(#48): Implement EventsSource.extractEvents
  private def extractEvents(response: Response): Process[Task, Event] = ???
}
// $COVERAGE-ON$
