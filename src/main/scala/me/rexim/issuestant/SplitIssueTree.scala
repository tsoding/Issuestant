package me.rexim.issuestant

object SplitIssueTree {
  def fromIssue(issue: Issue) = SplitIssueTree(issue.number, issue.title, issue.html_url, Nil)
}

case class SplitIssueTree(number: Int, title: String, htmlUrl: String, children: List[SplitIssueTree]) {
  def print(level: Int = 0): Unit = {
    val padding = (1 to level).map(_ => ' ').mkString
    println(s"${padding}${number}: ${title}")
    children.foreach(_.print(level + 1))
  }
}
