
// import org.scalatest.matchers.should.Matchers
// import org.scalatest.wordspec.AnyWordSpec
// import de.htwg.se.stratego.util.Observer
// import de.htwg.se.stratego.util.Observable

// class ObservableSpec extends AnyWordSpec with Matchers {
//   "An Observable" should {
//     val observable = new Observable
//     val obs = new Observer {
//       var updated: Boolean = false
//       def isUpdated: Boolean = updated
//       override def update: Boolean = {updated = true; updated}
//     }
//     "add an Observer" in {
//       observable.add(obs)
//       observable.subscribers should contain (obs)
//     }
//     "notify an Observer" in {
//       obs.isUpdated should be(true)
//       observable.notifyObservers()
//       obs.isUpdated should be(true)
//     }
//     "remove an Observer" in {
//       observable.remove(obs)
//       observable.subscribers should not contain ()
//     }

//   }

// }
