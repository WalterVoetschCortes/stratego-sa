package de.htwg.se.stratego.model.fileIoComponent

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCode}
import akka.http.scaladsl.server.Directives.*
import de.htwg.se.stratego.model.fileIODatabaseComponent.fileIOSlick.FileIOSlick
import de.htwg.se.stratego.model.fileIoComponent.fileIoJsonImpl.FileIO
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import com.google.inject.Guice
import de.htwg.se.stratego.model.fileIODatabaseComponent.FileIOModule
import de.htwg.se.stratego.model.fileIODatabaseComponent.FileIODatabaseInterface

import scala.util.{Success, Failure}

case object FileIOService {

  def main(args: Array[String]): Unit = {

    val fileIO = new FileIO
    val injector = Guice.createInjector(new FileIOModule)
    val db = injector.getInstance(classOf[FileIODatabaseInterface])
    implicit val system = ActorSystem(Behaviors.empty, "fileIO")
    implicit val executionContext = system.executionContext

    val fileIOPort = 8081
    val fileIOUri = "localhost" //"fileio-service"

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
              db.update(0, game)
              complete(HttpResponse.apply(StatusCode.int2StatusCode(200)))
            }
            }
          }
        },
        get {
          path("loadDB") {
            complete(HttpEntity(ContentTypes.`application/json`, db.read(0)))
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
            entity(as [String]) { game =>
              fileIO.save(game)
              complete(HttpResponse.apply(StatusCode.int2StatusCode(200)))
            }
          }
        }
      )

    val bindingFuture = Http().newServerAt(fileIOUri, fileIOPort).bind(route)

    bindingFuture.onComplete{
      case Success(binding) => {
        val address = binding.localAddress
        println(s"File IO Save: http://${address.getHostName}:${address.getPort}/${"save"}\n" +
          s"File IO Load: http://${address.getHostName}:${address.getPort}/${"load"} \n")
      }
      case Failure(exception) => {
        println("File IO REST service couldn't be started! Error: " + exception + "\n")
      }
    }
  }
}
