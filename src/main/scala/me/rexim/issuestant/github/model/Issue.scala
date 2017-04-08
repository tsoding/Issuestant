package me.rexim.issuestant.github.model

case class Issue (
  number: Int,
  title: String,
  html_url: String,
  body: String
)
