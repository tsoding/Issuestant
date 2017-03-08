package me.rexim.issuestant

import io.circe._
import io.circe.generic.semiauto._
import io.circe.parser._
import io.circe.syntax._

import scala.io._

import scalaz.concurrent.Task

import org.http4s._
import org.http4s.dsl._
import org.http4s.circe._
import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze._

object Main extends ServerApp {
  case class Message(message: String)

  // Circe macros expansions use asInstanceOf, which makes
  // Wartremover freak out a little bit. To avoid that we just
  // ignore this particular Wart here.
  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
  implicit val encodeMessageCirce = deriveEncoder[Message]
  implicit val encodeMessageHttp4s = jsonEncoderOf[Message]

  private val services = HttpService {
    case GET -> Root / "hello" =>
      Ok(Message("hello"))
  }

  // TODO: Use the HTTP port passed by heroku
  override def server(args: List[String]): Task[Server] = {
    BlazeBuilder
      .bindHttp(8080, "localhost")
      .mountService(services, "/api")
      .start
  }
}
