package me.rexim.issuestant

import me.rexim.issuestant.github._

package object splittree {
  implicit class IssueWithParent(issue: Issue) {
    lazy val parentNumber: Option[Int] = {
      "(?i)split +(?:from|form) +#([0-9]+)".r
        .findFirstMatchIn(issue.body)
        .map(m => Integer.parseInt(m.group(1)))
    }
  }
}
