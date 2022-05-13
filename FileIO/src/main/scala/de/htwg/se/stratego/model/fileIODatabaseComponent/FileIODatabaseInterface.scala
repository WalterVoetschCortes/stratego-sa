package de.htwg.se.stratego.model.fileIODatabaseComponent

import de.htwg.se.stratego.model.fileIODatabaseComponent.fileIOSlick.Matchfield

import scala.collection.mutable.ListBuffer

trait FileIODatabaseInterface {
  
  def update(id: Int, game: String) : Unit

  def delete() : Unit

  def read(id: Int): String

}