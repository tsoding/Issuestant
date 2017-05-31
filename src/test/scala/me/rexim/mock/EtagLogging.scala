package me.rexim.issuestant.mock

import scala.collection.mutable.ListBuffer
import scala.util._

import org.http4s._
import org.http4s.client._

import scalaz.concurrent._

class EtagLogging {
  private val requestLog: ListBuffer[Request] = ListBuffer.empty

  private def randomEtag(): String =
    Random.nextString(10).toList.map(_.toInt.toHexString).mkString

  lazy val client: Client = Client(
    open = HttpService.lift { (request) =>
      requestLog += request
      Task {
        Response(
          status = Status.Ok,
          headers = Headers(
            Header("ETag", randomEtag())
          )
        )
      }
    }.map((r) => DisposableResponse(r, Task({}))),
    shutdown = Task({})
  )
  def log: List[Request] = requestLog.toList
}
