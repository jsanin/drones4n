package corrientazo.drones4n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * In memory implementation of a Drone
 */
public class DroneImpl implements Drone {

    private static final Logger logger = LoggerFactory.getLogger(DroneImpl.class);

    private String id;
    private DronePosition position;
    private DroneStatus status;
    private int capacity;
    private List<DronePosition> positions;

    public DroneImpl(String id, int capacity, DronePosition initialPosition) {
        this.id = id;
        this.capacity = capacity;
        this.position = initialPosition;
        this.status = DroneStatus.STARTED;
        positions = new ArrayList<>();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean move(String instruction) {
        this.status = DroneStatus.MOVING;
        logger.info("DroneId {} moving to {}", this.id, instruction);
        this.status = DroneStatus.REACH_DESTINATION;
        return true;
    }

    @Override
    public DronePosition getCurrentPosition() {
        return position;
    }

    @Override
    public List<DronePosition> getAllPositions() {
        return positions;
    }

    @Override
    public void goToStartingPoint() {
        logger.info("DroneId {} going to starting point", this.id);
    }

    @Override
    public DroneStatus status() {
        return status;
    }
}
