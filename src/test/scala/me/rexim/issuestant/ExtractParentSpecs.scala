package me.rexim.issuestant

import org.scalatest._

class ExtractParentSpecs extends FlatSpec with Matchers {
  "extractParent operation" should "extract parent number from the issue's body if it's present" in {
    val issue = Issue(1, "Foo", "http://example.com", "Split from 2\n\nFoo bar")
    Main.extractParent(issue).parentNumber should be (Some(2))
  }

  it should "indicate that the issue doesn't have a parent if the body doesn't contain anything" in {
    val issue = Issue(1, "Foo", "http://example.com", "Split from 2\n\nFoo bar")
    Main.extractParent(issue).parentNumber should be (None)
  }
}
