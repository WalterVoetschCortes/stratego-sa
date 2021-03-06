package de.htwg.se.stratego.model

import de.htwg.se.stratego.model.matchFieldComponent.matchFieldBaseImpl.Colour
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ColorSpec extends AnyWordSpec with Matchers {

  "A Colour" when {

    "gets string" should {
      val blue = 0
      val red = 1

      "return correct enum" in {
        blue should be(Colour.BLUE.value)
        red should be(Colour.RED.value)
        Colour.BLUE.toString should be("0")
        Colour.RED.toString should be("1")
      }
    }
  }

}
