package me.rexim.issuestant.github.model

case class IssuesEvent (
  action: String,
  issue: Issue
) extends EventPayload
