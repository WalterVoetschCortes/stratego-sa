package de.htwg.se.stratego.controller.controllerComponent
import de.htwg.se.stratego.model.matchFieldComponent.matchFieldBaseImpl.{Field, Matrix}
import de.htwg.se.stratego.model.playerComponent.Player

object GameStatus extends Enumeration :
  type GameStatus = Value
  val IDLE, UNDO, REDO, NEW, INIT, ATTACK, SAVED, COULD_NOT_SAVE, LOADED, COULD_NOT_LOAD = Value

  val map = Map[GameStatus, String](
    IDLE -> "",
    UNDO -> "Undo last step",
    REDO -> "Redo last step",
    NEW -> "Created new Game",
    INIT -> "matchfield initialized\nMove Figures with (m direction[u,d,r,l] row col) or attack with (a row col row col)\n",
    ATTACK -> "enemy attacked",
    SAVED -> "The Game was saved",
    COULD_NOT_SAVE -> "The Game was could not be saved",
    LOADED -> "The Game was loaded",
    COULD_NOT_LOAD -> "The Game was could not be loaded",
  )

  def getMessage(actualStatus: GameStatus) =
    map(actualStatus)

