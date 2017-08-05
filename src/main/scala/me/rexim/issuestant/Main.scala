package me.rexim.issuestant

import me.rexim.issuestant.github._
import me.rexim.issuestant.github.model._

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
  private implicit val issueEventCirce: Decoder[IssueEvent] = Decoder.forProduct1("event")(IssueEvent)
  private implicit val issueEventHttp4s = jsonOf[IssueEvent]
  private implicit val listIssueEventHttp4s: EntityDecoder[List[IssueEvent]] = jsonOf[List[IssueEvent]]

  // TODO(#33): Use the HTTP port passed by heroku
  override def server(args: List[String]): Task[Server] = {
    new Permalink(new EventsSource[IssueEvent](new EtagPolling(
      client = PooledHttp1Client(),
      pollingUri = new RepoEventsUri(
        owner = "tsoding",
        repo = "issuestant-playground"
      ).asUri
    ))).asTask.runAsync { _ => }

    // new EventsSource[IssueEvent](new EtagPolling(
    //   client = PooledHttp1Client(),
    //   pollingUri = new RepoEventsUri(
    //     owner = "tsoding",
    //     repo = "issuestant-playground"
    //   ).asUri
    // )).events.take(1).runLog.run

    BlazeBuilder.bindHttp(8080, "localhost").mountService(HelloService.service, "/api").start
  }
}
