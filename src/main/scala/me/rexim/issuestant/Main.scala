package me.rexim.issuestant

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

import scala.io._

import me.rexim.issuestant.github._
import me.rexim.issuestant.splittree._

object Main {
  def buildSplitForest(issues: List[Issue]): List[SplitIssueTree] = {
    val initialForestMap: Map[Int, SplitIssueTree] =
      issues.map { issue =>
        issue.number -> SplitIssueTree.fromIssue(issue)
      }.toMap

    val forestRootNumbers: Set[Int] =
      issues
        .filter(_.parentNumber.isEmpty)
        .map(_.number)
        .toSet

    issues.foldLeft(initialForestMap) {
      case (forestMap, issue) =>
        issue.parentNumber.map { parentNumber =>
          val Some(parent) = forestMap.get(parentNumber)
          val Some(child) = forestMap.get(issue.number)

          forestMap.updated(
            parentNumber,
            parent.copy(children = child :: parent.children))
        } getOrElse(forestMap)
    }.values.filter(tree => forestRootNumbers.contains(tree.number)).toList
  }

  def main(args: Array[String]): Unit = {
    // Circe macros expansions use asInstanceOf, which makes
    // Wartremover freak out a little bit. To avoid that we just
    // ignore this particular Wart here.
    @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
    val issues =
      decode[List[Issue]](Source.fromFile("issues.json").mkString)

    issues.foreach(issues => buildSplitForest(issues).foreach(_.print()))

  }
}
