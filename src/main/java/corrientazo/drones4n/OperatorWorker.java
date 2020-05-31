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

    public Drone start() {
        logger.debug("Waiting for droneId {} to be available...", drone.getId());
        synchronized (drone) {
            logger.debug("droneId {} available! Starting process", drone.getId());
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
        logger.debug("droneId {} released", drone.getId());
        return drone;
    }

    protected void applyMoves(Drone drone, List<String> insToDrone) {
        for (String ins : insToDrone) {
            logger.debug("DroneId {} moving ", drone.getId());
            if(!drone.move(ins)) {
                logger.error("Drone {} could not move to {}. Probably it is out of range. Trying next move ...", drone.getId(), ins);
            }
            /*
            THIS BLOCK OF CODE IS FOR TESTING PURPOSE
            I want to simulate that some time goes by
             */
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.debug("DroneId {} moved ", drone.getId());

        }
    }
}
