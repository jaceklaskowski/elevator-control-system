case class PrototypeECS(elevatorsNum: Int = 3) extends ElevatorControlSystem {

  require(elevatorsNum >= 0 && elevatorsNum <= 16,
    "Elevator control system supports up to 16 elevators")

  val elevators = (0 until elevatorsNum).map(n => Elevator(n))

  override def status(): Seq[(Int, Int, Seq[Int])] = {
    elevators.map(_.toStatus)
  }

  override def update(elevatorId: Int, floor: Int, goalFloor: Int): Unit = synchronized {
    elevators.find(e => e.id == elevatorId).headOption.foreach { e =>
      e.update(floor, goalFloor)
    }
  }

  override def pickup(floor: Int, direction: Int): Unit = synchronized {
    elevators.minBy(_.movingCost(floor, direction)).move(floor, direction)
  }

  // could par or random it
  override def step(): Unit = elevators.foreach(_.step)
}
