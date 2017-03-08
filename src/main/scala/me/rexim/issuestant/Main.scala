package me.rexim.issuestant

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.io._

import me.rexim.issuestant.github._
import me.rexim.issuestant.splittree._

object Main {
  def main(args: Array[String]): Unit = {
    // Circe macros expansions use asInstanceOf, which makes
    // Wartremover freak out a little bit. To avoid that we just
    // ignore this particular Wart here.
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    val issues =
      decode[List[Issue]](Source.fromFile("issues.json").mkString)

    issues.foreach(issues => println(new SplitIssueForest(issues)))
  }
}
