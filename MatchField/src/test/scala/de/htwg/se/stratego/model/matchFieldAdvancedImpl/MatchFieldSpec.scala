package de.htwg.se.stratego.model.matchFieldAdvancedImpl

import de.htwg.se.stratego.model.matchFieldComponent.matchFieldAvancedImpl.MatchField
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class MatchFieldSpec extends AnyWordSpec with Matchers{
  "A MatchField" should {
    val matchField = new MatchField(4, 4, false)
    "have beend created new" in {
      matchField.createNewMatchField.toString should be("   0     1     2     3  \n+-----+-----+-----+-----+\n|" +
        "     |     |     |     | 0\n+-----+-----+-----+-----+\n|     |     |     |     | 1\n+-----+-----+-----" +
        "+-----+\n|     |     |     |     | 2\n+-----+-----+-----+-----+\n|     |     |     |     | 3\n+-----+-" +
        "----+-----+-----+\n**********  STRATEGO  **********\n\nn:   create a new empty machtfield\nz:   undo\ny:" +
        "   redo\nq:   quit the programm\n")
    }
  }
}
