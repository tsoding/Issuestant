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
  def responses(implicit decoder: Decoder[E]): Process[Task, E] =
    Process
      .iterateEval(new EtagItem[E]())(pollingIteration)
      .map(_.asEntity)
      .collect { case Some(e) => e }

  private def pollingIteration(prevItem: EtagItem[E])
                              (implicit decoder: Decoder[E]): Task[EtagItem[E]] =
    client.fetch[EtagItem[E]](prevItem.nextRequest(pollingUri))(EtagItem.fromResponse)
}
