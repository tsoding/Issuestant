package me.rexim.issuestant.github

import me.rexim.issuestant.github.model._

import io.circe._

import scalaz.stream._
import scalaz.concurrent._

import org.scalatest._

class ActivityEventsSourceSpec extends FlatSpec with Matchers {
  behavior of "ActivityEventsSource"

  it should "filter out already happened events" in {
    val eventsSource = new ActivityEventsSource(
      new JsonEntitiesSource[List[ActivityEvent]] {
        override def entities(implicit decoder: Decoder[List[ActivityEvent]]): Process[Task, List[ActivityEvent]] =
          Process.emitAll(List(
            List(),
            List(ActivityEvent("1", "hello"), ActivityEvent("2", "hello"), ActivityEvent("3", "hello")),
            List(ActivityEvent("2", "hello"), ActivityEvent("3", "hello"), ActivityEvent("4", "hello")),
            List(ActivityEvent("3", "hello"), ActivityEvent("4", "hello"), ActivityEvent("5", "hello"))
          ))
      })

    eventsSource.events.runLog.run.toList should
      be (List(
        ActivityEvent("1", "hello"),
        ActivityEvent("2", "hello"),
        ActivityEvent("3", "hello"),
        ActivityEvent("4", "hello"),
        ActivityEvent("5", "hello")))
  }
}
