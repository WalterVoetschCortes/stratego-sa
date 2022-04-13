package de.htwg.se.stratego.model.fileIoComponent

import de.htwg.se.stratego.model.matchFieldComponent.MatchFieldInterface
import de.htwg.se.stratego.model.playerComponent.Player
import scala.util.Try

trait FileIOInterface:
  def load: String
  def save(gamestate_json: String): Unit
