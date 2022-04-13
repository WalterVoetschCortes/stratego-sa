package de.htwg.se.stratego.controller.controllerComponent

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest}
import akka.http.scaladsl.server.Directives._
import de.htwg.se.stratego.Stratego.controller
import de.htwg.se.stratego.aview.gui.GameFrame

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.swing.Reactor

object RootService extends Reactor {

  listenTo(controller)

  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  val port = 7070

  def server(): Future[Http.ServerBinding] = {
    val route =
      concat(
        get {
          path("controller" / "load") {
            controller.load
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, GameStatus.getMessage(controller.gameStatus)))
          }
        },
        get {
          path("controller" / "save") {
            controller.save
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, GameStatus.getMessage(controller.gameStatus)))
          }
        },
      )
    Http().newServerAt("localhost", port).bind(route)
  }
  
  def stop(server: Future[Http.ServerBinding]): Unit = {
    server
      .flatMap(_.unbind()).onComplete(_ => println(port + " released"))
  }
}
