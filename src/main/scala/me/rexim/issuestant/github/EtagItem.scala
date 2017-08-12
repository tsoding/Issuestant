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

object EtagItem {

  def fromResponse[E](response: Response)(implicit decoder: Decoder[E]): Task[EtagItem[E]] = {
    implicit val http4sDecoder = jsonOf[E]

    if (response.status == Status.Ok) {
      response.as[E].map { e =>
        new EtagItem(EtagItem.getEtag(response), Some(e))
      }
    } else {
      Task(new EtagItem(EtagItem.getEtag(response), None))
    }
  }

  private def getEtag(response: Response): Option[String] =
    response.headers.get(CaseInsensitiveString("ETag")).map(_.value)
}

class EtagItem[E](etag: Option[String] = None, entity: Option[E] = None) {
  def nextRequest(pollingUri: Uri): Request =
    Request (
      uri = pollingUri,
      headers = etag
        .map(e => Headers(Header("If-None-Match", e)))
        .getOrElse(Headers.empty)
    )

  def asEntity: Option[E] = entity
}
