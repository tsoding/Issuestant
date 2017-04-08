package me.rexim.issuestant.splittree

import me.rexim.issuestant.github.model._

object SplitIssueTree {
  def fromIssue(issue: Issue) = SplitIssueTree(issue.number, issue.title, issue.html_url, Nil)
}

case class SplitIssueTree(number: Int, title: String, htmlUrl: String, children: List[SplitIssueTree]) {
  private def toStringLevel(level: Int): List[String] = {
    s"${" " * level}${number}: ${title}" :: children.flatMap(_.toStringLevel(level + 1))
  }

  override def toString(): String = toStringLevel(0).mkString("\n")
}
