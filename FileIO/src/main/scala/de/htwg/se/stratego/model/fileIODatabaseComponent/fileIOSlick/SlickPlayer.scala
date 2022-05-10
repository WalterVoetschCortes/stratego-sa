package de.htwg.se.stratego.model.fileIODatabaseComponent.fileIOSlick

import slick.lifted.ProvenShape
import slick.jdbc.PostgresProfile.api.*

class SlickPlayer(tag: Tag) extends Table[(Int, Int, String, Int)](tag, "player") :

  def id: Rep[Int] = column[Int]("ID")
  def currentPlayerIndex: Rep[Int] = column[Int]("CPI")
  def players: Rep[String] = column[String]("players")
  def sizeOfMatchfield: Rep[Int] = column[Int]("sizeOfMatchfield")

  override def * : ProvenShape[(Int, Int, String, Int)] = (id, currentPlayerIndex, players, sizeOfMatchfield)
