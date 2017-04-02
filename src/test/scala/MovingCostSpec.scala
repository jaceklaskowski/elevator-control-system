import org.scalatest._

class MovingCostSpec extends FlatSpec with Matchers {

  "Elevator" should "calculate its moving cost" in {
    val e = Elevator(0)
    e.movingCost(floor = 5, direction = Up) should be(5)

    e.move(floor = 4, direction = Up)
    Seq.fill(4)(e.step())
    e.movingCost(floor = 5, direction = Up) should be(1)

    e.update(floor = 5, goalFloor = 5)

    e.move(floor = 4, direction = Up)

    e.update(floor = 4, goalFloor = 4)

    e.movingCost(floor = 5, direction = Up) should be(-1000)
  }
}
