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
  var _state = ElevatorState(floor = 0, goalFloor = 0)

  def update(floor: Int, goalFloor: Int) = {
    _state = ElevatorState(floor, goalFloor)
  }

  def move(floor: Int, direction: Int): Unit = synchronized {
    _state = _state.copy(goalFloor = floor)
  }

  def step(): Unit = {
    if (state.isGoingUp) {
      _state = _state.copy(floor = _state.floor + 1)
    } else if (state.isGoingDown) {
      _state = _state.copy(floor = _state.floor - 1)
    } else {} // do nothing => I stand still
  }

  // the closer to 0, the better
  // perhaps we could correlate it somehow with time ticks/steps?
  def movingCost(floor: Int, direction: Int): Int = {
    val cost = if (state.standingStill) {
      math.abs(state.floor - floor)
    }
    else if (state.isGoingUp && state.goalFloor == floor) 0
    else if (state.isGoingUp) {
      math.abs(floor - state.goalFloor)
    } else if (state.isGoingDown && state.goalFloor < floor) {
      state.floor - state.goalFloor + (floor - state.goalFloor)
    } else {
      0
    }
    cost
  }

  def state: ElevatorState = _state
  def toStatus: (Int, Int, Int) = (id, state.floor, state.goalFloor)
}

case class ElevatorState(floor: Int, goalFloor: Int) {
  def isGoingUp: Boolean = floor < goalFloor
  def isGoingDown: Boolean = floor > goalFloor
  def standingStill: Boolean = floor == goalFloor
  def isMoving: Boolean = !standingStill
}

trait ElevatorControlSystem {
  def status(): Seq[(Int, Int, Int)]
  def update(elevatorId: Int, floor: Int, goalFloor: Int)
  def pickup(floor: Int, direction: Int)
  def step()
}