package me.rexim.issuestant

import scalaz.concurrent.Task

import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze._

object Main extends ServerApp {
  // TODO: Use the HTTP port passed by heroku
  override def server(args: List[String]): Task[Server] = {
    BlazeBuilder.bindHttp(8080, "localhost").mountService(HelloService.service, "/api").start
  }
}
