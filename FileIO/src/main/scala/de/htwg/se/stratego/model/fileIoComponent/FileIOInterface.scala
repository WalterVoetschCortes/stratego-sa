package de.htwg.se.stratego.model.fileIoComponent

import scala.util.Try

trait FileIOInterface:
  def load: String
  def save(gamestate_json: String): Unit
