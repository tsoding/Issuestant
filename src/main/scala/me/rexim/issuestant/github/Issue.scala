package me.rexim.issuestant.github

case class Issue (
  number: Int,
  title: String,
  html_url: String,
  body: String
)
