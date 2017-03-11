package me.rexim.issuestant

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import org.http4s._
import org.http4s.dsl._
import org.http4s.circe._

object HelloService {
  case class Message(message: String)

  implicit val encodeMessageCirce: Encoder[Message] =
    Encoder.forProduct1("message")(_.message)
  implicit val decodeMessageCirce: Decoder[Message] =
    Decoder.forProduct1("message")(Message)

  implicit val encodeMessageHttp4s = jsonEncoderOf[Message]
  implicit val decodeMessageHttp4s = jsonOf[Message]

  lazy val service = HttpService {
    case GET -> Root / "hello" =>
      Ok(Message("hello"))
  }
}
