package de.htwg.se.stratego.model.fileIODatabaseComponent.fileIOSlick

import de.htwg.se.stratego.model.fileIODatabaseComponent.FileIODatabaseInterface
import de.htwg.se.stratego.model.fileIODatabaseComponent.fileIOSlick.{SlickMatchfield, SlickPlayer}
import play.api.libs.json.{JsArray, JsNumber, JsString, JsValue, Json}
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery
import slick.jdbc.PostgresProfile.api.*

import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

case class FileIOSlick() extends FileIODatabaseInterface :
  val database = Database.forURL("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres", driver = "org.postgresql.Driver")

  val slickplayertable = TableQuery[SlickPlayer]
  val slickmatchfieldtable = TableQuery[SlickMatchfield]
  val tables = List(slickplayertable, slickmatchfieldtable)

  tables.foreach(e => Await.result(database.run(e.schema.createIfNotExists), Duration.Inf))

  def delete(): Unit =
    Await.ready(database.run(slickplayertable.delete), Duration.Inf)
    Await.ready(database.run(slickmatchfieldtable.delete), Duration.Inf)

  def update(id:Int, game: String): Unit =
    delete()
    val json: JsValue = Json.parse(game)
    val newPlayerIndex = (json \ "currentPlayerIndex").get.toString().toInt
    val players = (json \ "players").as[String]
    val sizeOfMatchfield: Int = (json \ "matchField").as[JsArray].value.size
    var matchfield = (0, 0, 0, Option(""), Option(0), Option(0))
    var figName: String = ""
    var figValue: Int = 0
    var colour: Int = 0
    for (index <- 0 until sizeOfMatchfield)
      val row = (json \\ "row") (index).as[Int]
      val col = (json \\ "col") (index).as[Int]
      if (((json \ "matchField") (index) \\ "figName").nonEmpty)
        figName = ((json \ "matchField") (index) \ "figName").as[String]
        figValue = ((json \ "matchField") (index) \ "figValue").as[Int]
        colour = ((json \ "matchField") (index) \ "colour").as[Int]
      else
        figName = ""
        figValue = 0
        colour = 0

      matchfield = (0, row, col, Option(figName), Option(figValue), Option(colour))
      database.run(slickmatchfieldtable += matchfield).andThen{
        case Success(_) => println("Success")
        case Failure(e) => println(s"Failure: ${e.getMessage}")
      }

    database.run(slickplayertable += (0, newPlayerIndex, players, sizeOfMatchfield))

  override def read(id:Int): String =
    val player: (Int, Int, String, Int) = readPlayer
    val matchfieldlist: ListBuffer[(Int, Int, Int, Option[String], Option[Int], Option[Int])] = ListBuffer.empty
    Await.result(database.run(slickmatchfieldtable.result.map(_.foreach(f => matchfieldlist.append((f._1, f._2, f._3, f._4, f._5, f._6))))), Duration.Inf)
    val matchfield: ListBuffer[(Int, Int, Int, Option[String], Option[Int], Option[Int])] = matchfieldlist
    val string = Json.prettyPrint(Json.obj(
      "currentPlayerIndex" -> JsNumber(player._2),
      "players" -> JsString(player._3).value,
      "matchField" -> Json.toJson(
        for {
          row <- 0 until Math.sqrt(player._4).toInt
          col <- 0 until Math.sqrt(player._4).toInt
        } yield {
          var obj = Json.obj(
            "row" -> row,
            "col" -> col
          )
          matchfield.foreach(f => {
            if (f._2 == row && f._3 == col && !f._4.equals(Option(""))) {
              obj = obj.++(Json.obj(
                "figName" -> f._4,
                "figValue" -> f._5,
                "colour" -> f._6
              )
              )
            }
          })
          obj
        })))
    string

  def readPlayer: (Int, Int, String, Int) =
    val player@(id, playerIndex, players, sizeOfMatchfield) = Await.result(database.run(slickplayertable.result.head), Duration.Inf)

    (id, playerIndex, players, sizeOfMatchfield)


