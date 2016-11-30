package me.rexim.issuestant

import org.scalatest._

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
}
