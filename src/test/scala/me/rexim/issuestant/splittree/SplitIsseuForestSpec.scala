package me.rexim.issuestant.splittree

import org.scalatest._
import me.rexim.issuestant.github._
import me.rexim.issuestant.splittree._

class SplitIssueForestSpec extends FlatSpec with Matchers {
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

  val expectedString = """|1: foo
                          | 5: subfoo1
                          | 7: subfoo2
                          |2: bar
                          | 6: subbar
                          |3: hello
                          |4: world""".stripMargin


  "Build split forest operation" should "build split forest from plain set of issues" in {
    new SplitIssueForest(inputIssues).toString should be (expectedString)
  }
}
