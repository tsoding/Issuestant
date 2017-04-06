package me.rexim.issuestant

import scalaz.concurrent.Task
import me.rexim.issuestant.github.EventsSource
import me.rexim.issuestant.github.model._

// TODO(1cdf1e3c-d19b-493d-9d33-f88c92ee24e3): Implement Permalink

/** Issuestant service for Permalink protocol (see doc/Permalink.md)
  *
  * The class is stateless. The next state is returned by update()
  * method. The state should be managed by the invoker.
  *
  * @param eventSource the source of GitHub events
  */
// $COVERAGE-OFF$
class Permalink(eventSource: EventsSource) {
  /** An update iteration of Permalink service
    *
    * Polls the recent events from the event source, performs the
    * permalinking actions and returns Permalink object with the next
    * state.
    *
    * @return next state of the Permalink service
    */
  def update: Task[Permalink] = ???
}
// $COVERAGE-ON$
