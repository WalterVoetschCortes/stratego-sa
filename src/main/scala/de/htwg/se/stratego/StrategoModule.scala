package de.htwg.se.stratego

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.se.stratego.controller.controllerComponent.ControllerInterface
import de.htwg.se.stratego.model.fileIoComponent.FileIOInterface
import de.htwg.se.stratego.model.matchFieldComponent.MatchFieldInterface
import de.htwg.se.stratego.model.matchFieldComponent.matchFieldAvancedImpl.MatchField
import de.htwg.se.stratego.model.fileIoComponent._

class StrategoModule extends AbstractModule:

  val defaultSize:Int = 4
  val defaultSet:Boolean = false

  override def configure():Unit = 

    bindConstant().annotatedWith(Names.named("DefaultSize")).to(defaultSize)
    bindConstant().annotatedWith(Names.named("DefaultSet")).to(defaultSet)
    bind(classOf[MatchFieldInterface]).to(classOf[MatchField])
    bind(classOf[ControllerInterface]).to(classOf[controller.controllerComponent.controllerBaseImpl.Controller])

    bind(classOf[MatchFieldInterface]).annotatedWith(Names.named("tiny")).toInstance(new MatchField(defaultSize, defaultSize, defaultSet))
    bind(classOf[MatchFieldInterface]).annotatedWith(Names.named("small")).toInstance(new MatchField(6, 6, defaultSet))
    bind(classOf[MatchFieldInterface]).annotatedWith(Names.named("normal")).toInstance(new MatchField(10, 10, defaultSet))

    bind(classOf[FileIOInterface]).to(classOf[fileIoJsonImpl.FileIO])



