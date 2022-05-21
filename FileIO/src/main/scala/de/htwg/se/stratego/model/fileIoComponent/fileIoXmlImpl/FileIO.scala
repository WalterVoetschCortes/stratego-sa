package de.htwg.se.stratego.model.fileIoComponent.fileIoXmlImpl

// import com.google.inject.Guice
// import de.htwg.se.stratego.model.fileIoComponent.FileIOInterface
// import de.htwg.se.stratego.model.matchFieldComponent.MatchFieldInterface
// import de.htwg.se.stratego.model.matchFieldComponent.matchFieldBaseImpl.{Colour, Figure, GameCharacter, MatchField}
// import de.htwg.se.stratego.model.playerComponent.Player
// import scala.util.{Try, Success, Failure}

// import scala.xml.PrettyPrinter

// class FileIO extends FileIOInterface:
//   override def load: Try[Option[(MatchFieldInterface, Int, String)]] = 
//     val emptyMatchfield = new MatchField(4, 4, false)
//     var matchFieldOption: Option[(MatchFieldInterface, Int, String)] = None
//     Try{
//       val file = scala.xml.XML.loadFile("matchField.xml")
//       val currentPlayerIndex = (file \\ "matchField" \ "@currentPlayerIndex").text.toInt
//       val playerS = (file \\ "matchField" \ "@players").text
//       matchFieldOption = Some(((emptyMatchfield),  0 , "a b"))
//       val fieldNodes = (file \\ "field")
//       matchFieldOption match
//         case Some((newmatchField, newPlayerIndex, newPlayers)) => 
//           var _newmatchField = newmatchField
//           for field <- fieldNodes do
//             val row: Int = (field \ "@row").text.toInt
//             val col: Int = (field\ "@col").text.toInt
//             val figName: String = (field\ "@figName").text
//             val figValue: Int = (field\ "@figValue").text.toInt
//             val colour:Int = (field\ "@colour").text.toInt
//             _newmatchField = _newmatchField.addChar(row, col, new GameCharacter(Figure.FigureVal(figName,figValue)),
//               Colour.FigureCol(colour))
//           matchFieldOption = Some((_newmatchField, currentPlayerIndex, playerS))
//         case None =>
//       matchFieldOption
//     }



//   def cellToXml(matchField: MatchFieldInterface, row: Int, col: Int) = 
//     if(matchField.fields.field(row,col).isSet) then
//       <field row={row.toString} col={col.toString} figName={matchField.fields.field(row,col).character.get.figure.name} figValue={matchField.fields.field(row,col).character.get.figure.value.toString} colour={matchField.fields.field(row,col).colour.get.value.toString}>
//       </field>

//   def matchFieldToXml(matchField: MatchFieldInterface, currentPlayerIndex: Int, playerS: String) =
//     <matchField  currentPlayerIndex={ currentPlayerIndex.toString} players={playerS}>
//       {
//       for
//         row <- 0 until matchField.fields.matrixSize
//         col <- 0 until matchField.fields.matrixSize
//       yield cellToXml(matchField, row, col)
//       }
//     </matchField>


//   def saveString(matchField: MatchFieldInterface, currentPlayerIndex:Int, players: List[Player]): Try[Unit] = 
//     import java.io._
//     Try{
//       val pw = new PrintWriter(new File("matchField.xml"))
//       val prettyPrinter = new PrettyPrinter(120,4)
//       val playerS = "" + players(0) + " " + players(1)
//       val xml = prettyPrinter.format(matchFieldToXml(matchField, currentPlayerIndex, playerS))
//       pw.write(xml)
//       pw.close      
//     }
    
//   override def save(matchField: MatchFieldInterface, currentPlayerIndex: Int, players: List[Player]): Try[Unit] = saveString(matchField,currentPlayerIndex,players)

