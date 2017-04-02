# Elevator Control System (ECS)

## Design

**Thought**: ECS is like a cluster manager with elevators as agents.

Elevator Control System (ECS) controls elevators that can stand still (and hence no people inside) or go up or down.

There can be one or many people going in different directions (up and down).

Choosing what elevator to take on `UP` or `DOWN` requests is a function of cost that an elevator computes on demand when such a request is received.

## How to run (aka Build Instructions)
 
Install [sbt](http://www.scala-sbt.org/) and execute `sbt test` command. Expect `[success]` at the end.

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