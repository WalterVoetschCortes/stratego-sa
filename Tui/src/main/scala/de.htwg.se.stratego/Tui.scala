package de.htwg.se.stratego

import de.htwg.se.stratego.aview.Tui
import de.htwg.se.stratego.controller.restController.RestController
import de.htwg.se.stratego.controller.{ControllerInterface, TuiService}

import scala.io.StdIn.readLine

object Tui {
  val controller: ControllerInterface = new RestController
  val tui = new Tui(controller)
  val restController: TuiService.type = TuiService


  def main(args: Array[String]): Unit = {

    val server = restController.start()

    var input: String = ""

    while (!input.equals("q"))
      input = readLine
      tui.processInputLine(input)

    restController.stop(server)

  }
}
