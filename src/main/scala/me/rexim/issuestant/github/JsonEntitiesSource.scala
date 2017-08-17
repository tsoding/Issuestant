package me.rexim.issuestant.github

import io.circe._

import scalaz.stream._
import scalaz.concurrent._

trait JsonEntitiesSource[E] {
  // TODO: Make JsonEntitiesSource accept http4s decoder iso the circe one
  //
  // Using the circe decoder effectively ties this interface to json
  // only. Switching to http4s decoder makes it much more flexible
  def entities(implicit decoder: Decoder[E]): Process[Task, E]
}
