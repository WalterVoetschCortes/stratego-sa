package de.htwg.se.stratego.aview.gui

import java.awt.{Color, Font, Point, Toolkit}

import de.htwg.se.stratego.controller.controllerComponent.{ControllerInterface, PlayerChanged}
import javax.imageio.ImageIO
import javax.swing.border.LineBorder
import javax.swing.{BorderFactory, ImageIcon, JFrame, WindowConstants}

import scala.swing.event.ButtonClicked
import scala.swing.{Button, Dimension, FlowPanel, Frame, GridPanel, Label, TextField}
import java.io.File

class PlayerFrame(controller:ControllerInterface) extends Frame{

  listenTo(controller)

  val strategoImg = ImageIO.read(new File("src/main/scala/de/htwg/se/stratego/aview/gui/stratego.png"))
  val strategoI = new ImageIcon(strategoImg)
  val defaultColor = new Color(143,138,126)
  val lightG = new Color(192,192,192)
  val lightF = new Font("Calibri", 1, 25)
  val defaultFont = new Font("Calibri", Font.BOLD, 30)
  val defaultBorder = new LineBorder(java.awt.Color.WHITE,10)
  val iconImg = ImageIO.read(new File("src/main/scala/de/htwg/se/stratego/aview/gui/iconS.png"))

  title = "Stratego"
  iconImage = iconImg
  peer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  resizable= false
  //peer.setLocationRelativeTo(null)
  visible= true

  val player1 = new TextField("", 20){
    foreground= lightG
    font = lightF
    border = BorderFactory.createEmptyBorder(0,20,0,0)
  }

  val player2 = new TextField("", 20){
    foreground= lightG
    font = lightF
    border = BorderFactory.createEmptyBorder(0,20,0,0)
  }

  def img = new Label{
    icon = strategoI
  }

  def welcomeString = new Label{
    text = "Welcome to"
    foreground= defaultColor
    font = defaultFont
    opaque = true
  }

  def welcomePanel = new FlowPanel() {
    contents += welcomeString
    contents += img
  }

  def setPanel = new GridPanel(2,2){
    contents += new Label {
      text = "Player 1:"
      foreground= defaultColor
      font = defaultFont
    }
    contents += player1
    vGap = 10
    contents += new Label {
      text = "Player 2:"
      foreground= defaultColor
      font = defaultFont
    }
    contents += player2
  }


  val next = new Button{
    text = "Play"
    font = defaultFont
    foreground= defaultColor
    opaque = true
  }

  val load = new Button{
    text = "Load Game"
    font = defaultFont
    foreground = defaultColor
    opaque = true
  }

  val quit = new Button{
    text = "Quit"
    font = defaultFont
    foreground = defaultColor
    opaque = true
  }

  def emptyPanel = new FlowPanel

  def buttonPanel = new GridPanel(3,2) {
    border = BorderFactory.createEmptyBorder(40,0,0,0)
    vGap = 30
    contents += emptyPanel
    contents += next
    contents += emptyPanel
    contents += load
    contents += emptyPanel
    contents += quit
  }

  listenTo(next)

  reactions += {
    case ButtonClicked(`next`) =>
        listenTo(controller)
      if(player1.text.isEmpty || player2.text.isEmpty) {
        controller.handle("Player1"+ " "+ "Player2")
      }
      controller.handle(player1.text+ " "+ player2.text)
  }
  reactions += {
    case event: PlayerChanged     =>
      visible = false
      deafTo(controller)
      close()
      new SetFrame(controller)
  }

  listenTo(load)
  reactions += {
    case ButtonClicked(`load`) =>
      controller.load
      visible = false
      deafTo(controller)
      close()
      new GameFrame(controller)
  }

  listenTo(quit)
  reactions += {
    case ButtonClicked(`quit`) =>
      System.exit(0)
  }

  val mainPanel = new GridPanel(3,1) {
    contents += img
    vGap = 30
    contents += setPanel
    contents += buttonPanel
    border = BorderFactory.createEmptyBorder(0,0,80,90)
  }

  contents = mainPanel

}
