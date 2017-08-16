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
import me.rexim.issuestant.stream._

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
class ActivityEventsSource(etagPolling: EtagPolling[List[ActivityEvent]]) extends Logging {
  // TODO: Implement timestamp based event filtering from #72
  def events: Process[Task, ActivityEvent] =
    etagPolling
      .responses
      // TODO: Extract filtering pair into a separate entity
      .scanl((Set[String](), List[ActivityEvent]())) {
        case ((seen, _), events) => (seen ++ events.map(_.id), events.filterNot(e => seen.contains(e.id)))
      }
      .map(_._2)
      .flatMap(Process.emitAll)
      .map((e) => { info(s"New GitHub event: ${e}"); e })
}
// $COVERAGE-ON$
