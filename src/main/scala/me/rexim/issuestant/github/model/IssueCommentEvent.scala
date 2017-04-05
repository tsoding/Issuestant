package me.rexim.issuestant.github.model

case class IssueCommentEvent (
  action: String,
  comment: Comment
) extends EventPayload
