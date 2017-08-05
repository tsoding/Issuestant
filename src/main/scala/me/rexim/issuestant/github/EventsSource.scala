package me.rexim.issuestant.github

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scalaz.stream._
import scalaz.concurrent._

import org.http4s._
import org.http4s.util._
import org.http4s.client._
import org.http4s.circe._
import org.http4s.Http4s._
import org.http4s.Status._
import org.http4s.Method._
import org.http4s.EntityDecoder
import scalaz._

import me.rexim.issuestant.github.model._

import grizzled.slf4j.Logging

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
class EventsSource[E] (etagPolling: EtagPolling) extends Logging {

  def events(implicit decoder: EntityDecoder[List[E]]): Process[Task, E] =
    etagPolling.responses
      .flatMap(extractEvents)
      .map((e) => { info(s"New GitHub event: ${e}"); e })

  private def extractEvents(response: Response)
    (implicit decoder: EntityDecoder[List[E]]): Process[Task, E] =
    Process.emitAll(response.as[List[E]].run)
}
// $COVERAGE-ON$
