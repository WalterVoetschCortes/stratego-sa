package de.htwg.se.stratego.model.fileIoComponent

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCode}
import akka.http.scaladsl.server.Directives.*
import de.htwg.se.stratego.model.fileIODatabaseComponent.fileIOSlick.FileIOSlick
import de.htwg.se.stratego.model.fileIoComponent.fileIoJsonImpl.FileIO

case object FileIOService {

  def main(args: Array[String]): Unit = {

    val fileIO = new FileIO
    val slickDB = FileIOSlick()
    implicit val system = ActorSystem(Behaviors.empty, "fileIO")
    implicit val executionContext = system.executionContext

    val fileIOPort = 8081
    val fileIOUri = "fileio-service"

    val route =
      concat (
        get {
          path("createTables") {
            complete(HttpResponse.apply(StatusCode.int2StatusCode(200)))
          }
        },
        post {
          path("saveDB") {
            entity(as [String]) { game => {
              slickDB.update(game)
              complete(HttpResponse.apply(StatusCode.int2StatusCode(200)))
            }
            }
          }
        },
        get {
          path("loadDB") {
            println("load json from db")
            complete(HttpEntity(ContentTypes.`application/json`, slickDB.readMatchfield))
          }
        },
        get {
          path("deleteDB") {
            slickDB.delete()
            println("data in tables deleted")
            complete(HttpResponse.apply(StatusCode.int2StatusCode(200)))
          }
        },
        get {
          path("json") {
            println("load json")
            complete(HttpEntity(ContentTypes.`application/json`, fileIO.load))
          }
        },
        post {
          path("json") {
            entity(as [String]) { game =>
              fileIO.save(game)
              println("game saved")
              complete(HttpResponse.apply(StatusCode.int2StatusCode(200)))
            }
          }
        }
      )

    val bindingFuture = Http().newServerAt(fileIOUri, fileIOPort).bind(route)
  }
}
