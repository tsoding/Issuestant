package me.rexim.issuestant

import me.rexim.issuestant.github.EventsSource

import scala.concurrent.duration.{FiniteDuration, SECONDS}

import scalaz.concurrent.Task

import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze._
import org.http4s.client.blaze._

object Main extends ServerApp {
  // TODO(19f86d8b-7c7f-4acc-af5b-a3a4c29cec5c): Use the HTTP port passed by heroku
  override def server(args: List[String]): Task[Server] = {
    new Permalink(new EventsSource (
      client = PooledHttp1Client(),
      owner = "tsoding",
      repo = "issuestant-playground"
    )).asTask.runAsync { _ => }

    BlazeBuilder.bindHttp(8080, "localhost").mountService(HelloService.service, "/api").start
  }
}
