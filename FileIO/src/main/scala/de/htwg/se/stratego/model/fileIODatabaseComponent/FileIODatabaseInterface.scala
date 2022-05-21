package de.htwg.se.stratego.model.fileIODatabaseComponent

import de.htwg.se.stratego.model.fileIODatabaseComponent.fileIOSlick.Matchfield

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

trait FileIODatabaseInterface {
  
  def update(id: Int, game: String) : Unit

  def delete() : Future[Any]

  def read(id: Int): Future[String]

}