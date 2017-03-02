import org.scalatest._

class MovingCostSpec extends FlatSpec with Matchers {

  "Elevator" should "calculate its moving cost" in {
    val e = Elevator(0)
    e.movingCost(floor = 5, direction = Up) should be(5)

    e.move(floor = 4, direction = Up)
    e.movingCost(floor = 5, direction = Up) should be(1)

    e.state.isGoingUp should be(true)

    e.update(floor = 5, goalFloor = 5)

    e.move(floor = 4, direction = Up)
    e.state.isGoingDown should be(true)

    e.update(floor = 4, goalFloor = 4)

    e.movingCost(floor = 5, direction = Up) should be(1)
  }
}
