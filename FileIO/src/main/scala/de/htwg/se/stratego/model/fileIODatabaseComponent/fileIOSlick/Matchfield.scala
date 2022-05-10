package de.htwg.se.stratego.model.fileIODatabaseComponent.fileIOSlick

case class Matchfield(id: Int, row: Int, col: Int, figName: Option[String] = None, figValue: Option[Int] = None, colour: Option[Int] = None)

/*
object Matchfield {

  def apply(id: Int, row: Int, col: Int, figName: Option[String], figValue: Option[Int], colour: Option[Int]): Matchfield = {
    Matchfield(id, row, col, figName, figValue, colour)
  }

  def mapperTo(id: Int, row: Int, col: Int, figName: Option[String], figValue: Option[Int], colour: Option[Int]) = apply(id, row, col, figName, figValue, colour)
}
*/

