package me.rexim.issuestant.polling

import scalaz.concurrent.Task

trait Pollable {
  def update: Task[Pollable]
}
