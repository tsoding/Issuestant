package me.rexim.issuestant

import me.rexim.issuestant.github._
import me.rexim.issuestant.github.model._
import me.rexim.issuestant.github.uri._

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.concurrent.duration.{FiniteDuration, SECONDS}

import scalaz.concurrent.Task

import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze._
import org.http4s.client.blaze._
import org.http4s.circe._
import org.http4s._

object Main extends ServerApp {
  // TODO(#33): Use the HTTP port passed by heroku
  override def server(args: List[String]): Task[Server] = {
    new Permalink(new EventsSource[IssueEvent](new EtagPolling(
      client = PooledHttp1Client(),
      pollingUri = new RepoIssuesEventsUri(
        owner = "tsoding",
        repo = "issuestant-playground"
      ).asUri
    ))).asTask.runAsync { _ => }

    BlazeBuilder.bindHttp(8080, "localhost").mountService(HelloService.service, "/api").start
  }
}
