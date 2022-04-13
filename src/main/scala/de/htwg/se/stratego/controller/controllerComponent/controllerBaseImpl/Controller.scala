package de.htwg.se.stratego.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject}
import de.htwg.se.stratego.StrategoModule
import de.htwg.se.stratego.controller.controllerComponent.{ControllerInterface, FieldChanged, GameFinished, GameStatus, MachtfieldInitialized, NewGame, PlayerChanged, PlayerSwitch}
import de.htwg.se.stratego.controller.controllerComponent.GameStatus._
import de.htwg.se.stratego.model.fileIoComponent.FileIOInterface
import de.htwg.se.stratego.model.matchFieldComponent.MatchFieldInterface
import de.htwg.se.stratego.model.matchFieldComponent.matchFieldBaseImpl.{CharacterList, Field, Game, MatchField, Matrix}
import de.htwg.se.stratego.model.playerComponent.Player
import de.htwg.se.stratego.util.UndoManager
import de.htwg.se.stratego.controller.controllerComponent.RootService.system

import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal

import scala.swing.Publisher

import scala.util.{Success, Failure}
import com.typesafe.scalalogging.{LazyLogging, Logger}
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import de.htwg.se.stratego.model.matchFieldComponent.matchFieldBaseImpl.GameCharacter
import de.htwg.se.stratego.model.matchFieldComponent.matchFieldBaseImpl.Figure
import de.htwg.se.stratego.model.matchFieldComponent.matchFieldBaseImpl.Colour
import play.api.libs.json.JsNumber
import play.api.libs.json.JsObject
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContextExecutor

class Controller @Inject()(var matchField:MatchFieldInterface) extends ControllerInterface with Publisher with LazyLogging:


  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")  
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://localhost:8080/json"))

  val injector = Guice.createInjector(new StrategoModule)
  val fileIO = injector.getInstance(classOf[FileIOInterface])

  val list = CharacterList(matchField.fields.matrixSize)
  var playerBlue = Player("PlayerBlue", list.getCharacterList())
  var playerRed = Player("PlayerRed", list.getCharacterList())
  var game = Game(playerBlue, playerRed, matchField.fields.matrixSize, matchField)
  var playerList = List[Player](playerBlue,playerRed)

  var gameStatus: GameStatus = IDLE
  var currentPlayerIndex: Int = 0
  private val undoManager = new UndoManager
  var state: ControllerState = EnterPlayer(this)

  def handle(input: String):String =
    state.handle(input)


  def welcome:String =
    "Welcome to STRATEGO! " +
      "Please enter first name of Player1 and then of Player2 like (player1 player2)!"


  def setPlayers(input: String): String =
    playerList = game.setPlayers(input)
    nextState
    publish(new PlayerChanged)
    ""


  def createEmptyMatchfield(size:Int): String =
    matchField = new MatchField(size, size, false)
    game = game.copy(playerBlue,playerRed,size,matchField)
    gameStatus=NEW
    state = EnterPlayer(this)
    publish(new NewGame)
    currentPlayerIndex=0
    "created new matchfield\nPlease enter the names like (player1 player2)"


  def initMatchfield: String =
    var newMatchField = matchField
    newMatchField = game.init(0, 0, 0, 0, matchField)
    if matchField.equals(newMatchField) then
      ""
    else
      matchField = game.init(0, 0, 0, 0, matchField)
      gameStatus=INIT
      nextState
      publish(new MachtfieldInitialized)
      "" + playerList(currentPlayerIndex) + " it's your turn!"


  def attack(rowA: Int, colA: Int, rowD:Int, colD:Int): String =
    if game.onlyBombAndFlag(matchField,currentPlayerIndex) && matchField.fields.field(rowA,colA).isSet &&
      matchField.fields.field(rowA,colA).colour.get.value==currentPlayerIndex then
      currentPlayerIndex = nextPlayer
      publish(new GameFinished)
      currentPlayerIndex=1
      nextState
      createEmptyMatchfield(matchField.fields.matrixSize)
      return "Congratulations " + playerList(currentPlayerIndex) +"! You're the winner!\n" +
        "Game finished! Play new Game with (n)!"

    if rowD <= matchField.fields.matrixSize - 1 && rowD >= 0 && colD >= 0 && colD <= matchField.fields.matrixSize - 1 &&
      matchField.fields.field(rowA, colA).isSet.equals(true) && matchField.fields.field(rowD, colD).isSet.equals(true)
      && matchField.fields.field(rowD,colD).colour.get.value!= currentPlayerIndex &&
      matchField.fields.field(rowD,colD).character.get.figure.value==0 then
      publish(new GameFinished)
      currentPlayerIndex=1
      nextState
      createEmptyMatchfield(matchField.fields.matrixSize)
      return "Congratulations " + playerList(currentPlayerIndex) +"! You're the winner!\n" +
        "Game finished! Play new Game with (n)!"

    if rowD <= matchField.fields.matrixSize - 1 && rowD >= 0 && colD >= 0 && colD <= matchField.fields.matrixSize - 1 &&
      matchField.fields.field(rowA,colA).isSet && matchField.fields.field(rowA,colA).colour.get.value==currentPlayerIndex
      && matchField.fields.field(rowD,colD).isSet && matchField.fields.field(rowD,colD).colour.get.value!= currentPlayerIndex then
      matchField = game.Context.attack(matchField, rowA, colA, rowD, colD,currentPlayerIndex)
      gameStatus = ATTACK
      currentPlayerIndex= nextPlayer
      publish(new PlayerSwitch)
      publish(new FieldChanged)

    ""

  def set(row:Int, col:Int, charac:String): String =
    currentPlayerIndex match {
      case 0 =>
        undoManager.doStep(new SetCommand(currentPlayerIndex, row, col, charac, this))
        if game.bList.size == 0 then
          currentPlayerIndex=nextPlayer
          publish(new PlayerSwitch)

      case 1 =>
        undoManager.doStep(new SetCommand(currentPlayerIndex, row, col, charac, this))
        if game.rList.size == 0 then
          currentPlayerIndex=nextPlayer
          nextState
          publish(new MachtfieldInitialized)

    }
    publish(new FieldChanged)
    if game.rList.size == 0 then
        return "Move Figures with (m direction[u,d,r,l] row col) or attack with (a row col row col)\n" +
        playerList(currentPlayerIndex) + " it's your turn!"

    if game.bList.size == 0 then
      return ""

    ""

  def move(dir: Char, row:Int, col:Int): String =
    if matchField.fields.field(row,col).isSet && matchField.fields.field(row,col).colour.get.value==currentPlayerIndex then
      if game.onlyBombAndFlag(matchField,currentPlayerIndex) then
        currentPlayerIndex = nextPlayer
        publish(new GameFinished)
        currentPlayerIndex=1
        nextState
        createEmptyMatchfield(matchField.fields.matrixSize)
        return "Congratulations " + playerList(currentPlayerIndex) +"! You're the winner!\n" +
          "Game finished! Play new Game with (n)!"

      undoManager.doStep(new MoveCommand(dir, matchField, row, col, currentPlayerIndex, this))
      if !matchField.fields.field(row,col).isSet then
        currentPlayerIndex = nextPlayer
        publish(new FieldChanged)
        publish(new PlayerSwitch)

    ""


  def matchFieldToString: String = matchField.toString

  def undo: String =
    currentPlayerIndex= nextPlayer
    undoManager.undoStep
    gameStatus = UNDO
    publish(new FieldChanged)
    publish(new PlayerSwitch)
    "undo"


  def redo: String =
    currentPlayerIndex=nextPlayer
    undoManager.redoStep
    gameStatus = REDO
    publish(new FieldChanged)
    publish(new PlayerSwitch)
    "redo"


  def nextState: Unit =
    state = state.nextState()
    publish(new FieldChanged)


  def statusString:String = GameStatus.getMessage(gameStatus)

  def nextPlayer: Int = if currentPlayerIndex == 0 then 1 else 0

  override def getSize: Int = matchField.fields.matrixSize

  override def getField: Matrix[Field] = matchField.fields

  override def load: String = {
    responseFuture.onComplete {
      case Failure(_) => sys.error("HttpResponse failure")
      case Success(res) => {
        Unmarshal(res.entity).to[String].onComplete {
          case Failure(_) => sys.error("Marshal failure")
          case Success(result) => {
            unpackJson(result)
          }
        }
      }
    }
    "load"
  }

  def unpackJson(result: String): Unit = {
    val json: JsValue = Json.parse(result)
    val injector = Guice.createInjector(new StrategoModule)
    var newMatchField = injector.getInstance(classOf[MatchFieldInterface])
    val newPlayerIndex = (json \ "currentPlayerIndex").get.toString().toInt
    val playerS = (json \ "players").get.toString()
    for(index <- 0 until matchField.fields.matrixSize * matchField.fields.matrixSize){
      val row = (json \\ "row")(index).as[Int]
      val col = (json \\ "col")(index).as[Int]
      if(((json \ "matchField")(index) \\ "figName").nonEmpty) {
        val figName = ((json \ "matchField")(index) \ "figName").as[String]
        val figValue = ((json \ "matchField")(index) \ "figValue").as[Int]
        val colour = ((json \ "matchField")(index) \ "colour").as[Int]
        newMatchField = newMatchField.addChar(row, col, GameCharacter(Figure.FigureVal(figName, figValue)), Colour.FigureCol(colour))
      }
    }
    game = game.copy(matchField = newMatchField)
    currentPlayerIndex = newPlayerIndex

    state = GameState(this)
    gameStatus=LOADED
    publish(new FieldChanged)
  }

  def matchFieldToJson(matchField: MatchFieldInterface, currentPlayerIndex: Int, players: String): JsObject = {
    Json.obj(
      "currentPlayerIndex" -> JsNumber(currentPlayerIndex),
      "players" -> players,
      "matchField"-> Json.toJson(
        for{
          row <- 0 until matchField.fields.matrixSize
          col <- 0 until matchField.fields.matrixSize
        } yield {
          var obj = Json.obj(
            "row" -> row,
            "col" -> col
          )
          if(matchField.fields.field(row,col).isSet) {
            obj = obj.++(Json.obj(
              "figName" -> matchField.fields.field(row, col).character.get.figure.name,
              "figValue" -> matchField.fields.field(row, col).character.get.figure.value,
              "colour" -> matchField.fields.field(row, col).colour.get.value
            )
            )
          }
          obj
        }
      )
    )
  }

  override def save: String = {
    val players = playerList
    publish(new FieldChanged)
    gameStatus=SAVED
    val playerS = "" + players(0) + " " + players(1)
    val gamestate: String = Json.prettyPrint(matchFieldToJson(game.matchField, currentPlayerIndex, playerS))
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(method = HttpMethods.POST, uri = "http://localhost:8080/json", entity = gamestate))
    "save"
  }


