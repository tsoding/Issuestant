package me.rexim.issuestant

import org.scalatest._
import me.rexim.issuestant.github._
import me.rexim.issuestant.splittree._

class BuildSplitForestSpec extends FlatSpec with Matchers {
  /**
    * foo
    *  |---subfoo1
    *  +---subfoo2
    *
    * bar
    *  +---subbar
    *
    * hello
    *
    * world
    */
  val inputIssues = List(
    Issue(1, "foo", "http://example.com", "Root foo issue"),
    Issue(2, "bar", "http://example.com", "Root bar issue"),
    Issue(3, "hello", "http://rexim.me", "Hello"),
    Issue(4, "world", "http://rexim.me", "World"),
    Issue(5, "subfoo1", "http://pornhub.com", "awawawaw\n\nsplit from #1"),
    Issue(6, "subbar", "https://google.com", "SPLIT FROM #2\n\nolyvova"),
    Issue(7, "subfoo2", "https://fornever.me", "\n\nSpLiT FrOm #1\n\n"))

  val expectedForest = List(
    SplitIssueTree(1, "foo", "http://example.com", List(
      SplitIssueTree(5, "subfoo1", "http://pornhub.com", Nil),
      SplitIssueTree(7, "subfoo2", "https://fornever.me", Nil)
    )),
    SplitIssueTree(2, "bar", "http://example.com", List(
      SplitIssueTree(6, "subbar", "https://google.com", Nil)
    )),
    SplitIssueTree(3, "hello", "http://rexim.me", Nil),
    SplitIssueTree(4, "world", "http://rexim.me", Nil))

  def sortForest(forest: List[SplitIssueTree]): List[SplitIssueTree] =
    forest.sortBy(_.number).map { tree =>
      tree.copy(children = sortForest(tree.children))
    }

  "Build split forest operation" should "build split forest from plain set of issues" in {
    sortForest(Main.buildSplitForest(inputIssues)) should be (sortForest(expectedForest))
  }
}
