package de.htwg.se.stratego.model
import de.htwg.se.stratego.model.matchFieldComponent.matchFieldBaseImpl.Field
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class FieldSpec extends AnyWordSpec with Matchers {
  "A Field" when { "new and is set" should {
    val field = Field(true)
    "can be set"  in {
      field.isSet should be(true)
    }
  }
  "new and is not set" should {
      val field = Field(false)
      "is not set"  in {
        field.isSet should be(false)
      }
  }
}}
