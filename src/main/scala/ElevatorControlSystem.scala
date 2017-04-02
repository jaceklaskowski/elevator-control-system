sealed trait Direction
object Down extends Direction
object Up extends Direction
object Direction {
  implicit def direction2Int(d: Direction): Int = d match {
    case Down => -1
    case Up => 1
  }
}

case class Elevator(id: Int) {
  def canAcceptPickup(floor: Int, direction: Int): Boolean = true

  var _state = ElevatorState()

  def update(floor: Int, goalFloor: Int) = {
    _state = ElevatorState(floor, Seq(goalFloor))
  }

  def move(floor: Int, direction: Int): Unit = synchronized {
    _state = _state.copy(goalFloors = _state.goalFloors :+ floor)
  }

  def step(): Unit = {
    val step = if (state.isGoingUp) {
      1
    } else if (state.isGoingDown) {
      -1
    } else {
      0 // do nothing => stand still
    }
    _state = _state.copy(floor = _state.floor + step)

    // unload/load the people
    _state = _state.copy(goalFloors = _state.goalFloors.filterNot(floor => floor == _state.floor))
  }

  // the closer to 0, the better
  // perhaps we could correlate it somehow with time ticks/steps?
  def movingCost(floor: Int, direction: Int): Int = {
    val cost = if (state.standingStill) {
      math.abs(state.floor - floor)
    }
    else if (state.goalFloors.contains(floor)) {
      math.abs(state.floor - floor)
    }
    else if (state.isGoingUp && state.floor <= floor) {
      // we're going up to the requested floor
      math.abs(state.floor - floor)
    }
    else if (state.isGoingDown && floor <= state.floor) {
      math.abs(state.floor - floor)
    } else {
      // FIXME Max #Floors < 1000 / 2
      1000 * math.signum(state.floor - floor)
    }
    cost
  }

  def state: ElevatorState = _state
  def toStatus: (Int, Int, Seq[Int]) = (id, state.floor, state.goalFloors)
}

case class ElevatorState(floor: Int = 0, goalFloors: Seq[Int] = Seq.empty) {
  def isGoingUp: Boolean = !standingStill && floor < goalFloors.min
  def isGoingDown: Boolean = !standingStill && floor > goalFloors.min
  def standingStill: Boolean = goalFloors.isEmpty
  def isMoving: Boolean = !standingStill
}

trait ElevatorControlSystem {
  def status(): Seq[(Int, Int, Seq[Int])]
  def update(elevatorId: Int, floor: Int, goalFloor: Int)
  def pickup(floor: Int, direction: Int)
  def step()
}