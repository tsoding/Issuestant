package me.rexim.issuestant

import scalaz._
import scalaz.stream._
import scalaz.concurrent._
import scala.language.higherKinds

package object stream {
  // TODO: make ProcessWithScanl even more generic and get rid of Task
  implicit class ProcessWithScanl[+O](process: Process[Task, O]) {
    def scanl[O2](f: (O2, O) => O2, x0: O2): Process[Task, O2] =
      Process.emit(x0) ++ Process.eval(process.uncons.attempt.map {
        case -\/(_) => Process.empty
        case \/-((x1, rest_of_the_shit)) =>
          rest_of_the_shit.scanl(f, f(x0, x1))
      }).flatMap(x => x)
  }
}
