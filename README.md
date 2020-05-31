# drones4n

Quality attributes

- It should be adjustable to business needs. Number of drones, Drone capacity, maximum blocks around for delivery
- It should be able to manage 20 drones at the same time

- The source of the instructions could change
- The model of drones could change
- Drones instruction could vary according to drone model

Assumptions
- Drone is loaded with full capacity and the instructions should send it back to the restaurant to be loaded again.
- The drone know how to go back to the initial point with the instruction goToStartingPoint
- Code was done in English, though the output text was done according to the requirement, which is in Spanish

Not included
- Need to define what to do in case the drone is out of range. A route validation should be included so it is known 
when a route is heading out of range.

#Usage
Usage:
java Application [numberOfDrones] [droneCapacity] [maxBlocksAround] [workDir] [Drone implementation class name] [Instruction provider class name]

Example:
java Application 10 2 10 /Users/jsanin/dev/workspace_personal/drones4n/workDir corrientazo.drones4n.DroneImpl corrientazo.drones4n.integrationFlatFileIntegration

With gradle:
 ./gradlew run --args="20 4 10 /Users/jsanin/dev/workspace_personal/drones4n/workDir corrientazo.drones4n.DroneImpl corrientazo.drones4n.integration.FlatFileIntegration"
