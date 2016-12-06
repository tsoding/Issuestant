package me.rexim.issuestant

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.io._

case class IssueWithParent(parentNumber: Option[Int], issue: Issue);

object Main {
  def extractParent(issue: Issue): IssueWithParent = {
    val splitCookie = "(?i)split +(?:from|form) +#([0-9]+)".r
    IssueWithParent(
      splitCookie
        .findFirstMatchIn(issue.body)
        .map(m => Integer.parseInt(m.group(1))),
      issue)
  }

  def buildSplitForest(issues: List[Issue]): List[SplitIssueTree] = {
    val initialForestMap: Map[Int, SplitIssueTree] =
      issues.map { issue =>
        issue.number -> SplitIssueTree.fromIssue(issue)
      }.toMap

    val issuesWithParents: List[IssueWithParent] =
      issues.map(extractParent)

    val forestRootNumbers: Set[Int] =
      issuesWithParents
        .filter(_.parentNumber.isEmpty)
        .map(_.issue.number)
        .toSet

    issuesWithParents.foldLeft(initialForestMap) {
      case (forestMap, IssueWithParent(Some(parentNumber), issue)) =>
        val Some(parent) = forestMap.get(parentNumber)
        val Some(child) = forestMap.get(issue.number)

        forestMap.updated(
          parentNumber,
          parent.copy(children = child :: parent.children))

      case (forestMap, _) => forestMap
    }.values.filter(tree => forestRootNumbers.contains(tree.number)).toList
  }

  def main(args: Array[String]): Unit = {
    val issues =
      decode[List[Issue]](Source.fromFile("issues.json").mkString)

    issues.foreach(issues => buildSplitForest(issues).foreach(_.print()))

  }
}
