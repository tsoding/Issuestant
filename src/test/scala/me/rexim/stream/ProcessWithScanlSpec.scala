package me.rexim.issuestant.stream

import org.scalatest._

import scalaz._
import scalaz.stream._
import scalaz.concurrent._

class ProcessWithScanlSpec extends FlatSpec with Matchers {
  behavior of "Process with scanl"

  it should "perform scanl operation similarly to the Haskell's one" in {
    val xs: Process[Task, Int] = Process.emitAll(1 to 5)

    xs.scanl(0)(_ + _).runLog.run.toList should
      be (List(0, 1, 3, 6, 10, 15))
  }
}
