package de.htwg.se.stratego.model.fileIoComponent.fileIoJsonImpl

import com.google.inject.Guice
import de.htwg.se.stratego.model.fileIoComponent.FileIOInterface
import de.htwg.se.stratego.model.matchFieldComponent.{FieldInterface, MatchFieldInterface}
import de.htwg.se.stratego.model.matchFieldComponent.matchFieldBaseImpl.{Colour, Figure, GameCharacter, Matrix, Field, MatchField}
import de.htwg.se.stratego.model.playerComponent.Player
import play.api.libs.json.{JsNumber, JsValue, Json, Writes}
import scala.util.{Try, Success, Failure}

import scala.io.Source
import javax.swing.JOptionPane



class FileIO extends FileIOInterface:
  def load: String = 
    fileNotFound("matchField.json") match 
      case Success(v) => println("File Found")
      case Failure(v) => JOptionPane.showMessageDialog(null, "Es ist kein Spielstand vorhanden!")
    
    val file = Source.fromFile("matchField.json")
    try file.mkString finally file.close()
  

  def fileNotFound(filename: String): Try[String] = 
    Try(Source.fromFile(filename).getLines().mkString)
  

  def save(gamestate_json: String): Unit = 
    import java.io._
    val print_writer = new PrintWriter(new File("matchField.json"))
    print_writer.write(gamestate_json)
    print_writer.close()
  
  








