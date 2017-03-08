package me.rexim.issuestant

import me.rexim.issuestant.github._

object SplitIssueTree {
  def fromIssue(issue: Issue) = SplitIssueTree(issue.number, issue.title, issue.html_url, Nil)
}

case class SplitIssueTree(number: Int, title: String, htmlUrl: String, children: List[SplitIssueTree]) {
  private def printLevel(level: Int): Unit = {
    val padding = (1 to level).map(_ => ' ').mkString
    println(s"${padding}${number}: ${title}")
    children.foreach(_.printLevel(level + 1))
  }

  def print(): Unit = {
    printLevel(0)
  }
}
