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

class ActivityEventsSource(jsonEntitiesSource: JsonEntitiesSource[List[ActivityEvent]]) extends Logging {
  // TODO(#75): Implement timestamp based event filtering from #72
  def events: Process[Task, ActivityEvent] =
    jsonEntitiesSource
      .entities
      // TODO: Extract filtering pair into a separate entity
      .scanl((Set[String](), List[ActivityEvent]())) {
        case ((seen, _), events) => (seen ++ events.map(_.id), events.filterNot(e => seen.contains(e.id)))
      }
      .map(_._2)
      .flatMap(Process.emitAll)
      .map((e) => { info(s"New GitHub event: ${e}"); e })
}

