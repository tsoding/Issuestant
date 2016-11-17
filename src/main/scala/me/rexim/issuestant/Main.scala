package me.rexim.issuestant

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.io._

object Main {
  def buildSplitForest(issues: List[Issue]): List[SplitIssueTree] = ???

  def main(args: Array[String]): Unit = {
    val issues =
      decode[List[Issue]](Source.fromFile("issues.json").mkString)

    println(issues.map(issues => buildSplitForest(issues).foreach(_.print())))

  }
}
