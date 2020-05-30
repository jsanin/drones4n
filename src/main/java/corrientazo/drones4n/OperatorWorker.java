package corrientazo.drones4n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Operates a drone with the given set of instructions.
 * This send instructions to the drone and when it gets to its maximum capacity returned it to the starting
 * point to get loaded again with the next set of deliveries and continue with the rest of instructions until
 * there is no more instructions to execute. In such case it returns to the starting point.
 */
public class OperatorWorker {
    private static final Logger logger = LoggerFactory.getLogger(OperatorWorker.class);

    private Drone drone;
    private List<String> instructions;

    public OperatorWorker(Drone drone, List<String> instructions) {
        this.drone = drone;
        this.instructions = instructions;
    }

    public void start() {
        int droneCapacity = drone.getCapacity();
        int counter = 0;
        List<String> insToDrone = new ArrayList<>(droneCapacity);
        for (String instruction : instructions) {
            if(counter < droneCapacity) {
                insToDrone.add(instruction);
                counter++;
            } else {
                // capacity full, move drone
                applyMoves(drone, insToDrone);
                insToDrone.clear();
                counter = 1;
                insToDrone.add(instruction);
                drone.goToStartingPoint();
            }
        }
        if(!insToDrone.isEmpty()) {
            applyMoves(drone, insToDrone);
            drone.goToStartingPoint();
        }
    }

    protected void applyMoves(Drone drone, List<String> insToDrone) {
        for (String ins : insToDrone) {
            if(!drone.move(ins)) {
                logger.error("Drone {} could not move to {}. Probably it is out of range. Trying next move ...", drone.getId(), ins);
            }
        }
    }
}
