package me.rexim.issuestant

import org.scalatest._
import me.rexim.issuestant.github._

class SplitIssueTreeSpec extends FlatSpec with Matchers {
  "SplitIssueTree" should "be constructable from a regular issue" in {
    val issue = Issue(1, "Foo", "http://example.com", "split from #2\n\nFoo bar")
    val splitIssueTree = SplitIssueTree.fromIssue(issue)

    splitIssueTree.number should be (issue.number)
    splitIssueTree.title should be (issue.title)
    splitIssueTree.htmlUrl should be (issue.html_url)
    splitIssueTree.children should be (Nil)
  }
}
