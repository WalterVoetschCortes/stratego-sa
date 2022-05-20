package de.htwg.se.stratego.model.fileIODatabaseComponent

import com.google.inject.Guice
import de.htwg.se.stratego.model.FileIOModule

class FileIODatabaseProxy extends FileIODatabaseInterface {
  
  val injector = Guice.createInjector(new FileIOModule)
  val db = injector.getInstance(classOf[FileIODatabaseInterface])
  
  def update(id: Int, game: String): Unit = db.update(0, game)
  def delete(): Unit = db.delete()
  def read(id: Int): String = db.read(0)
}
