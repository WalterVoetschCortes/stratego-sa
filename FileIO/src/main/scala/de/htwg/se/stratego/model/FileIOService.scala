package de.htwg.se.stratego.model

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCode}
import akka.http.scaladsl.server.Directives.{as, complete, concat, entity, get, path, post}
import com.google.inject.Guice
import de.htwg.se.stratego.model.fileIODatabaseComponent.{FileIODatabaseInterface, FileIODatabaseProxy}
import de.htwg.se.stratego.model.fileIoComponent.fileIoJsonImpl.FileIO
import de.htwg.se.stratego.model.FileIOModule
import de.htwg.se.stratego.model.fileIODatabaseComponent.FileIODatabaseProxy
import de.htwg.se.stratego.model.fileIoComponent.FileIOInterface
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

case object FileIOService {

  def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector(new FileIOModule)
    val fileIO = injector.getInstance(classOf[FileIOInterface])
    val db = new FileIODatabaseProxy
    implicit val system = ActorSystem(Behaviors.empty, "fileIO")
    implicit val executionContext = system.executionContext

    val fileIOPort = 8081
    val fileIOUri = "localhost" //"fileio-service"

    val route =
      concat(
        post {
          path("saveDB") {
            entity(as[String]) { game => {
              db.update(0, game)
              complete(HttpResponse.apply(StatusCode.int2StatusCode(200)))
            }
            }
          }
        },
        get {
          path("loadDB") {
            complete(HttpEntity(ContentTypes.`application/json`, Await.result(db.read(0), Duration.Inf)))
          }
        },
        get {
          path("deleteDB") {
            db.delete()
            complete(HttpResponse.apply(StatusCode.int2StatusCode(200)))
          }
        },
        get {
          path("load") {
            complete(HttpEntity(ContentTypes.`application/json`, fileIO.load))
          }
        },
        post {
          path("save") {
            entity(as[String]) { game =>
              fileIO.save(game)
              complete(HttpResponse.apply(StatusCode.int2StatusCode(200)))
            }
          }
        }
      )

    val bindingFuture = Http().newServerAt(fileIOUri, fileIOPort).bind(route)

    bindingFuture.onComplete {
      case Success(binding) => {
        val address = binding.localAddress
        println(s"File IO Save: http://${address.getHostName}:${address.getPort}/${"saveDB"}\n" +
          s"File IO Load: http://${address.getHostName}:${address.getPort}/${"loadDB"} \n")
      }
      case Failure(exception) => {
        println("File IO REST service couldn't be started! Error: " + exception + "\n")
      }
    }
  }
}
