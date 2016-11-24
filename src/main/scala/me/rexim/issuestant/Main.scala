package me.rexim.issuestant

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.io._

case class IssueWithParent(parentNumber: Option[Int], issue: Issue);

object Main {
  def extractParent(issue: Issue): IssueWithParent = ???

  def buildSplitForest(issues: List[Issue]): List[SplitIssueTree] = ???

  def main(args: Array[String]): Unit = {
    val issues =
      decode[List[Issue]](Source.fromFile("issues.json").mkString)

    println(issues.map(issues => buildSplitForest(issues).foreach(_.print())))

  }
}
