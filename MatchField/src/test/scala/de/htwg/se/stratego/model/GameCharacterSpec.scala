package de.htwg.se.stratego.model
import de.htwg.se.stratego.model.matchFieldComponent.matchFieldBaseImpl.{Figure, GameCharacter}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameCharacterSpec extends AnyWordSpec with Matchers {
  "A Character" when { "new" should {
    val character = GameCharacter(Figure.Bomb)
    "has a figure"  in {
      character.figure should be(Figure.Bomb)
    }
    "has a nice String representation" in {
      character.toString should be("B")
    }
  }}
}
