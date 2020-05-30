package corrientazo.drones4n;

import java.util.List;

/**
 * Definition of a Drone. Implementations of this interface will define specific behavior.
 */
public interface Drone {

    /**
     *
     * @return the id of this drone.
     */
    String getId();

    /**
     *
     * @return capacity of this drone. Example, the amount of deliveries support at a given time.
     */
    int getCapacity();

    /**
     * Move the drone from current point to destination following the given instruction
     * @param instruction instructions that can be interpreted by the drone
     * @return
     */
    boolean move(String instruction);

    /**
     *
     * @return current position
     */
    DronePosition getCurrentPosition();

    /**
     *
     * @return list of positions where the drone has been. It has the positions after each move
     */
    List<DronePosition> getAllPositions();

    /**
     * Move the drone the the starting point
     */
    void goToStartingPoint();

    /**
     *
     * @return current status.
     * @see DroneStatus
     */
    DroneStatus status();

}
