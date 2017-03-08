package me.rexim.issuestant

import org.scalatest._
import me.rexim.issuestant.github._

class ExtractParentSpecs extends FlatSpec with Matchers {
  "extractParent operation" should "extract parent number from the issue's body if it's present" in {
    val issue = Issue(1, "Foo", "http://example.com", "split from #2\n\nFoo bar")
    Main.extractParent(issue).parentNumber should be (Some(2))
  }

  it should "be case-insensitive to the split cookie" in {
    val issue = Issue(1, "Foo", "http://example.com", "SpLit FROM #3\n\nFoo bar")
    Main.extractParent(issue).parentNumber should be (Some(3))
  }

  it should "indicate that the issue doesn't have a parent if the body doesn't contain split cookie" in {
    val issue = Issue(1, "Foo", "http://example.com", "Split from 2\n\nFoo bar")
    Main.extractParent(issue).parentNumber should be (None)
  }

  it should "handle split cookie with arbitrary amount of spaces between words" in {
    val issue = Issue(1, "Foo", "http://example.com", "Split    from    #2\n\nFoo bar")
    Main.extractParent(issue).parentNumber should be (Some(2))
  }

  it should "not handle newlines" in {
    val issue = Issue(1, "Minoru", "https://blog.debiania.in.ua/",
      """|I think this can be solved with Tmux's split
         |
         |From #123 we already learned that […]""".stripMargin)
    Main.extractParent(issue).parentNumber should be (None)
  }

  it should "handle split cookies that have `from` misspelled as `form`" in {
    val issue = Issue(1, "Foo", "http://example.com", "split form #2\n\nFoo bar")
    Main.extractParent(issue).parentNumber should be (Some(2))
  }
}
