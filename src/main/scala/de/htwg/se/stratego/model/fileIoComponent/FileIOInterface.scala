package de.htwg.se.stratego.model.fileIoComponent

import de.htwg.se.stratego.model.matchFieldComponent.MatchFieldInterface
import de.htwg.se.stratego.model.playerComponent.Player
import scala.util.Try

trait FileIOInterface:
  def load: Try[Option[(MatchFieldInterface, Int, String)]]
  def save(matchField: MatchFieldInterface, currentPlayerIndex: Int, players:List[Player]): Try[Unit]
