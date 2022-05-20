package de.htwg.se.stratego.model.fileIODatabaseComponent

import com.google.inject.AbstractModule
import de.htwg.se.stratego.model.fileIODatabaseComponent.FileIODatabaseInterface
import de.htwg.se.stratego.model.fileIODatabaseComponent.fileIOSlick.FileIOSlick
import de.htwg.se.stratego.model.fileIODatabaseComponent.fileIOMongo.FileIOMongo

class FileIOModule extends AbstractModule:

  override def configure(): Unit =
    bind(classOf[FileIODatabaseInterface]).to(classOf[FileIOSlick])

