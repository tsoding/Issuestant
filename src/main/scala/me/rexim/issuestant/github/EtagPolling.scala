package me.rexim.issuestant.github

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import org.http4s._
import org.http4s.client._
import org.http4s.circe._
import org.http4s.util._

import scalaz._
import scalaz.stream._
import scalaz.concurrent._

class EtagPolling[E](client: Client, pollingUri: Uri) {
  // TODO: make EtagPolling[E].Item a class, not a tuple
  type Item = (Option[String], Option[E])

  def responses(implicit decoder: Decoder[E]): Process[Task, E] =
    Process
      .iterateEval((None, None): Item)(pollingIteration(_)(decoder))
      .collect { case (_, Some(e)) => e }

  private def getETag(response: Response): Option[String] =
    response.headers.get(CaseInsensitiveString("ETag")).map(_.value)

  private def nextRequest(previousItem: Item): Request =
    Request (
      uri = pollingUri,
      headers =
        previousItem._1
          .map(e => Headers(Header("If-None-Match", e)))
          .getOrElse(Headers.empty)
    )

  private def pollingIteration(previousItem: Item)(implicit decoder: Decoder[E]): Task[Item] =
    client.fetch[Item](nextRequest(previousItem)) { (response) =>
      implicit val http4sDecoder = jsonOf[E]

      if (response.status == Status.Ok) {
        response.as[E].map { e =>
          (getETag(response), Some(e))
        }
      } else {
        Task((getETag(response), None))
      }
    }
}
