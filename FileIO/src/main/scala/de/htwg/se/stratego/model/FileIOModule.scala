package de.htwg.se.stratego.model

import com.google.inject.AbstractModule
import de.htwg.se.stratego.model.fileIODatabaseComponent.FileIODatabaseInterface
import de.htwg.se.stratego.model.fileIODatabaseComponent.fileIOMongo.FileIOMongo
import de.htwg.se.stratego.model.fileIODatabaseComponent.fileIOSlick.FileIOSlick
import de.htwg.se.stratego.model.fileIoComponent.FileIOInterface
import de.htwg.se.stratego.model.fileIoComponent.fileIoJsonImpl.FileIO

class FileIOModule extends AbstractModule:

  override def configure(): Unit =
    bind(classOf[FileIODatabaseInterface]).to(classOf[FileIOSlick])
    bind(classOf[FileIOInterface]).to(classOf[FileIO])

