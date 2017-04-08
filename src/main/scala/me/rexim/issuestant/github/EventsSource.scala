package me.rexim.issuestant.github

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scalaz.concurrent.Task
import me.rexim.issuestant.github.model.Event

import org.http4s._
import org.http4s.client._
import org.http4s.Http4s._
import org.http4s.Status._
import org.http4s.Method._
import org.http4s.EntityDecoder

// TODO(83b40d5c-6af7-4268-a2a2-960d051102ae): Implement EventsSource

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
  /** Recent events recieved by the previous invocation of nextEvents
    * method
    *
    * @return recent events
    */
  def events: List[Event] = ???

  /** Receive next events
    *
    * @return next state of the EventsSource object
    */
  def nextEvents(): Task[EventsSource] = ???
}
// $COVERAGE-ON$
