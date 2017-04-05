package me.rexim.issuestant.splittree

import me.rexim.issuestant.github.model._

class SplitIssueForest(issues: List[Issue]) {
  private def sortForest(forest: List[SplitIssueTree]): List[SplitIssueTree] =
    forest.sortBy(_.number).map { tree =>
      tree.copy(children = sortForest(tree.children))
    }

  private lazy val forest: List[SplitIssueTree] = sortForest {
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

  override def toString: String = forest.map(_.toString).mkString("\n")
}
