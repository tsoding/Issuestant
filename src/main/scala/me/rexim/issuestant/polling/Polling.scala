package me.rexim.issuestant.polling

import grizzled.slf4j.Logging
import scala.concurrent.duration.Duration
import scalaz.concurrent.Task
import scalaz.stream.{Process, Cause}

// TODO(19e120b5-c7a8-426e-b54c-170cf1d7d3da): Cover Polling with Unit Tests
//
// It's actually not a trivial task, probably requires some
// decomposition. That's why it's a separate TODO.
class Polling(service: Pollable, interval: Int) extends Logging {
  // TODO(e2419ff0-47b9-4a54-91db-979b84de030e): get rid of wart suppressing
  //
  // Could not figure out why it complains
  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  def startAsync: Unit =
    Process
      .iterateEval(service)(_.update)
      .map { s =>
        Thread.sleep(interval)
        s
      }
      .onFailure{ e =>
        error(e)
        Process.Halt(Cause.Error(e))
      }
      .run.runAsync { _ => }
}
