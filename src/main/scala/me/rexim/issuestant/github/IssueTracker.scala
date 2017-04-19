package me.rexim.issuestant.github

import me.rexim.issuestant.github.model._

import scalaz.concurrent.Task

import org.http4s._
import org.http4s.client._

// TODO(aa92db76-a13d-46fd-8f34-48168c4207b9): Implement IssueTracker
class IssueTracker (client: Client, owner: String, repo: String) {
  def fileIssue(summary: String, description: String): Task[Issue] = ???
  def editIssueDescription(id: Int, newDescription: String): Task[Issue] = ???
  def editCommentBody(id: Int, newCommentBody: String): Task[Comment] = ???
}
