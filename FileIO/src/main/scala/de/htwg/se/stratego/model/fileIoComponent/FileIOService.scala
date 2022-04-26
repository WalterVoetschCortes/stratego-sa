package de.htwg.se.stratego.model.fileIoComponent

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import de.htwg.se.stratego.model.fileIoComponent.fileIoJsonImpl.FileIO

case object FileIOService {

  def main(args: Array[String]): Unit = {

    val fileIO = new FileIO
    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext

    val fileIOPort = 8081
    val fileIOUri = "fileio-service"

    val route =
      concat (
        get {
          path("load") {
            complete(HttpEntity(ContentTypes.`application/json`, fileIO.load))
          }
        },
        post {
          path("save") {
            entity(as [String]) { game =>
              fileIO.save(game)
              println("GAME SAVED")
              complete("game saved")
            }
          }
        }

      )

    val bindingFuture = Http().newServerAt(fileIOUri, fileIOPort).bind(route)
  }
}