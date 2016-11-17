package me.rexim.issuestant

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.io._

object SplitIssueTree {
  def fromIssue(issue: Issue) = SplitIssueTree(issue.number, issue.title, issue.html_url, List())
}

case class SplitIssueTree(number: Int, title: String, htmlUrl: String, children: List[SplitIssueTree]) {
  def print(level: Int = 0): Unit = {
    val padding = (1 to level).map(_ => ' ').mkString
    println(s"${padding}${number}: ${title}")
    children.foreach(_.print(level + 1))
  }
}

object Main {
  def buildSplitForest(issues: List[Issue]): List[SplitIssueTree] = ???

  def main(args: Array[String]): Unit = {
    val issues =
      decode[List[Issue]](Source.fromFile("issues.json").mkString)

    println(issues.map(issues => buildSplitForest(issues).foreach(_.print())))

  }
}
