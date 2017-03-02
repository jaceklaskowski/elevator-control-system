# Elevator Control System

## How to run
 
Install [sbt](http://www.scala-sbt.org/) and execute `test` command.

```bash
$ sbt test
...
[info] MovingCostSpec:
[info] Elevator
[info] - should calculate its moving cost
[info] ElevatorControlSystemSpec:
[info] A ElevatorControlSystem
[info] - should handle up to 16 elevators
[info] - should query the state of the elevators (what floor are they on and where they are going)
[info] - should receive a pickup request
[info] Run completed in 308 milliseconds.
[info] Total number of tests run: 4
[info] Suites: completed 2, aborted 0
[info] Tests: succeeded 4, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[success] Total time: 8 s, completed Mar 2, 2017 3:21:39 PM
```