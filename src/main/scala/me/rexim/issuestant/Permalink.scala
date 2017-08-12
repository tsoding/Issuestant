package me.rexim.issuestant

import io.circe._

import me.rexim.issuestant.github.ActivityEventsSource
import me.rexim.issuestant.github.model._

import scalaz.concurrent.Task

import org.http4s._

// TODO(#37): Implement Permalink
//
// Introduce dependencies that enable Permalink with modifying
// comments, modifying issue descriptions and fileing issues.
//
// The Permalink service should be essentially an FSM. The full set of
// the possible states of such FSM should be represented by ADT. For
// example
// ```scala
//   sealed trait PermalinkProtocol {
//     def handleEvent(event: Event): PermalinkProtocol
//   }
//   case class PermalinkServingState(..) extends PermalinkProtocol
//   case class PernalinkAskPermissionState(..) extends PermalinkProtocol
// ```

/** Issuestant service for Permalink protocol (see doc/Permalink.md)
  *
  * The class is stateless. The next state is returned by update()
  * method. The state should be managed by the invoker.
  *
  * @param eventSource the source of GitHub events
  */
// $COVERAGE-OFF$
class Permalink[E](eventSource: ActivityEventsSource) {
  def asTask(implicit decoder: Decoder[E]): Task[Unit] = Task {
    val _ = eventSource.events.runLog.run
  }
}
// $COVERAGE-ON$
