import org.scalatest._

class ElevatorControlSystemSpec extends FlatSpec with Matchers with BeforeAndAfter {

  "A ElevatorControlSystem" should "handle up to 16 elevators" in {
    PrototypeECS(elevatorsNum = 2).elevatorsNum should be(2)
    PrototypeECS(elevatorsNum = 16).elevatorsNum should be(16)

    an [IllegalArgumentException] should be thrownBy PrototypeECS(elevatorsNum = 200)
  }

  it should "query the state of the elevators (what floor are they on and where they are going)" in {
    val ecs = PrototypeECS(elevatorsNum = 2)
    ecs.status() should contain theSameElementsAs Seq((0, 0, 0), (1, 0, 0))
  }

  it should "receive a pickup request" in {
    val ecs = PrototypeECS()

    // all elevators at level 0

    // take me from 1 to ground floor
    ecs.pickup(floor = 1, direction = Down)

    // go over resources and pick the first fulfilling the request
    // in current impl it'd be the first elevator
    // no randomness or letting them to offer resources/themselves
    ecs.status() should contain theSameElementsAs Seq((0, 0, 1), (1, 0, 0), (2, 0, 0))

    // Time steps/ticks = one floor at a time across all elevators?
    ecs.step()

    // first elevator at 1st floor
    ecs.status() should contain theSameElementsAs Seq((0, 1, 1), (1, 0, 0), (2, 0, 0))

    // take me from level 4 to 2
    ecs.pickup(floor = 4, direction = Down)
    ecs.status() should contain theSameElementsAs Seq((0, 1, 4), (1, 0, 0), (2, 0, 0))
    ecs.step()

    ecs.status() should contain theSameElementsAs Seq((0, 2, 4), (1, 0, 0), (2, 0, 0))
    ecs.step()
    ecs.step()
    ecs.status() should contain theSameElementsAs Seq((0, 4, 4), (1, 0, 0), (2, 0, 0))

    // Why do I have to update?!
    // Is update to tell an elevator where to go? If so, why do I have to pass the current level?
    // Or is this to tell what elevator is coming to handle a passenger at goalFloor?
    // Let's assume it's to tell where an elevator should go to
    ecs.update(0, 4, 2)

    // elevator #0 is going down from 4 to 2
    ecs.status() should contain theSameElementsAs Seq((0, 4, 2), (1, 0, 0), (2, 0, 0))

    // take me from level 5 down
    ecs.pickup(floor = 5, direction = Down)
    ecs.status() should contain theSameElementsAs Seq((0, 4, 5), (1, 0, 0), (2, 0, 0))

    // take me from level 5 up
    ecs.pickup(floor = 1, direction = Up)
    ecs.status() should contain theSameElementsAs Seq((0, 4, 5), (1, 0, 1), (2, 0, 0))
  }
}