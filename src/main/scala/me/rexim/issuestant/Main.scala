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
  def addChild(child: SplitIssueTree) =
    SplitIssueTree(number, title, htmlUrl, child :: children)

  def print(level: Int = 0): Unit = {
    val padding = (1 to level).map(_ => ' ').mkString
    println(s"${padding}${number}: ${title}")
    children.foreach(_.print(level + 1))
  }
}

object Main {

  def mapify(issues: List[Issue]): Map[Int, Issue] =
    issues.foldLeft(Map[Int, Issue]()) {
      (acc, issue) => acc + (issue.number -> issue)
    }


  def buildSplitForest(issues: List[Issue]): List[SplitIssueTree] = {
    case class State(
      roots: Set[Int] = Set(),
      splitIssueTreeMap: Map[Int, SplitIssueTree] = Map[Int, SplitIssueTree]()
    ) {
      def splittedFrom(parentIssueNumber: Int, child: SplitIssueTree): State = {
        val parent = splitIssueTreeMap.get(parentIssueNumber).getOrElse(splitIssueTreeMap.get(0).get);
        State(roots, splitIssueTreeMap.updated(parentIssueNumber, parent.addChild(child)))
      }

      def addNode(nodeId: Int, node: SplitIssueTree) =
        State(roots, splitIssueTreeMap.updated(nodeId, node))

      def visited(currentIssueNumber: Int): Boolean =
        splitIssueTreeMap.contains(currentIssueNumber)

      def addRoot(rootId: Int) =
        State(roots + rootId, splitIssueTreeMap)

      def toSplitIssueForest: List[SplitIssueTree] =
        splitIssueTreeMap
          .filterKeys(roots.contains(_))
          .values.toList
    }

    val issuesMap = mapify(issues) + (0 -> Issue(0, "PLACEHOLDER", "", ""))

    def collectIssues(currentIssueNumber: Int, state: State): State = {
      val splitCookie = "Split from #([0-9]+)".r
      val issue = issuesMap.get(currentIssueNumber).getOrElse(issuesMap.get(0).get)
      val issueSplitTree = SplitIssueTree.fromIssue(issue)

      splitCookie
        .findFirstMatchIn(issue.body)
        .map(_.group(1).toInt)
        .map { parentIssueNumber =>
          if (!state.visited(parentIssueNumber)) {
            collectIssues(parentIssueNumber, state)
              .splittedFrom(parentIssueNumber, issueSplitTree)
          } else {
            state
          }
        }
        .getOrElse(state.addRoot(issue.number))
        .addNode(issue.number, issueSplitTree)
    }

    issues.foldLeft(State()) {
      case (state, issue) => if (!state.visited(issue.number)) {
        collectIssues(issue.number, state)
      } else {
        state
      }
    }
    .toSplitIssueForest
  }

  def main(args: Array[String]): Unit = {
    val issues =
      decode[List[Issue]](Source.fromFile("issues.json").mkString)

    println(issues.map(issues => buildSplitForest(issues).foreach(_.print())))

  }
}
