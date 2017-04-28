package me.rexim.issuestant

import scalaz.concurrent.Task
import me.rexim.issuestant.github.EventsSource
import me.rexim.issuestant.github.model._
import me.rexim.issuestant.polling._

// TODO(7ca7cee3-8fdc-4f9e-bd60-c4229b64f02b): Permalink Protocol FSM
//
// May supersede 1cdf1e3c-d19b-493d-9d33-f88c92ee24e3
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

// TODO(1cdf1e3c-d19b-493d-9d33-f88c92ee24e3): Implement Permalink
//
// Introduce dependencies that enable Permalink with modifying
// comments, modifying issue descriptions and fileing issues.

/** Issuestant service for Permalink protocol (see doc/Permalink.md)
  *
  * The class is stateless. The next state is returned by update()
  * method. The state should be managed by the invoker.
  *
  * @param eventSource the source of GitHub events
  */
// $COVERAGE-OFF$
class Permalink(eventSource: EventsSource) extends Pollable {
  /** An update iteration of Permalink service
    *
    * Polls the recent events from the event source, performs the
    * permalinking actions and returns Permalink object with the next
    * state.
    *
    * @return next state of the Permalink service
    */
  def update: Task[Pollable] = ???
}
// $COVERAGE-ON$
