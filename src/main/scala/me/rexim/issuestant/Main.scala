package me.rexim.issuestant

import me.rexim.issuestant.github.EventsSource

import scala.concurrent.duration.{FiniteDuration, SECONDS}

import scalaz.concurrent.Task

import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze._
import org.http4s.client.blaze._

object Main extends ServerApp {
  def pollingLoop(permalink: Permalink): Unit = {
    permalink.update.flatMap { nextPermalink =>
      Task.schedule(pollingLoop(nextPermalink), FiniteDuration(1, SECONDS))
    }.runAsync { _ => }
  }

  // TODO: Use the HTTP port passed by heroku
  override def server(args: List[String]): Task[Server] = {
    Task.schedule(pollingLoop(new Permalink(
      new EventsSource(
        client = PooledHttp1Client(),
        owner = "tsoding",
        repo = "issuestant-playground"
      )
    )), FiniteDuration(1, SECONDS)).runAsync { _ => }

    BlazeBuilder.bindHttp(8080, "localhost").mountService(HelloService.service, "/api").start
  }
}
