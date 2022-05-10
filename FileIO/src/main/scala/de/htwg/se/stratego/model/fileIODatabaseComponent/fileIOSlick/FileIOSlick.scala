package de.htwg.se.stratego.model.fileIODatabaseComponent.fileIOSlick

import de.htwg.se.stratego.model.fileIODatabaseComponent.FileIODatabaseInterface
import play.api.libs.json.{JsArray, JsNumber, JsObject, JsValue, Json}
import slick.jdbc.PostgresProfile.api.*
import slick.jdbc.JdbcBackend.Database
import slick.lifted.TableQuery

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration.Duration

case class FileIOSlick() extends FileIODatabaseInterface:
  val database = Database.forURL("jdbc:postgresql://db:5432/postgres", "postgres", "postgres", driver = "org.postgresql.Driver")

  val slickplayertable = TableQuery[SlickPlayer]
  val slickmatchfieldtable = TableQuery[SlickMatchfield]
  val tables = List(slickplayertable, slickmatchfieldtable)

  tables.foreach(e => Await.result(database.run(e.schema.createIfNotExists), Duration.Inf))

  def delete(): Unit =
    Await.ready(database.run(slickplayertable.delete), Duration.Inf)
    Await.ready(database.run(slickmatchfieldtable.delete), Duration.Inf)

  def update(game: String): Unit =
    val json: JsValue = Json.parse(game)
    val newPlayerIndex = (json \ "currentPlayerIndex").get.toString().toInt
    val players = (json \ "players").get.toString()
    val sizeOfMatchfield: Int = (json \ "matchField").as[JsArray].value.size
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
      database.run(slickmatchfieldtable += (0, row, col, Option(figName), Option(figValue), Option(colour)))

    database.run(slickplayertable += (0, newPlayerIndex, players, sizeOfMatchfield))

  override def readMatchfield: String =
    val player: (Int, Int, String, Int) = readPlayer
    val matchfield: ListBuffer[(Int, Int, Int, Option[String], Option[Int], Option[Int])] = readMatchfieldfromdb
    val string = Json.prettyPrint(Json.obj(
      "currentPlayerIndex" -> JsNumber(player._2),
      "players" -> player._3,
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
            if (f(1) == row && f(2) == col && !f(3).equals(Option(""))) {
              obj = obj.++(Json.obj(
                "figName" -> f(3),
                "figValue" -> f(4),
                "colour" -> f(5)
              )
              )
            }
          })
          obj
        })))
    string

  def readPlayer: (Int, Int, String, Int) =
    val player@(id, playerIndex, players, sizeOfMatchfield) = Await.result(database.run(slickplayertable.result.head), Duration.Inf)
    println(id.toString + " " + playerIndex.toString + " " + players + " " + sizeOfMatchfield)
    (id, playerIndex, players, sizeOfMatchfield)

  def readMatchfieldfromdb: ListBuffer[(Int, Int, Int, Option[String], Option[Int], Option[Int])] =
    var matchfieldlist: ListBuffer[(Int, Int, Int, Option[String], Option[Int], Option[Int])] = ListBuffer.empty
    Await.result(database.run(slickmatchfieldtable.result.map(_.foreach(f => matchfieldlist.append((f(0), f(1), f(2), f(3), f(4), f(5)))))), Duration.Inf)
    matchfieldlist.foreach(f => println(f))
    matchfieldlist
