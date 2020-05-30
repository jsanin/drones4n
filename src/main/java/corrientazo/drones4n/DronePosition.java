package corrientazo.drones4n;

import java.util.Objects;

/**
 * Representation of position of a drone. Think of a cartesian plane where x and y represent the position, and
 * direction represents the direction where the drone is looking at. <code>direction</code> can be 0, 1, 2, 3;
 * starting with 0 as the NORTH orientation and moving clockwise we get that 1 is EAST, 2 is SOUTH and 3 is WEST.
 * <code>x</code> and <code>y</code> can be positive and negative integers.
 */
public class DronePosition {
    private int x;
    private int y;
    private int direction;

    public DronePosition(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirection() {
        return direction;
    }

    public String getDirectionAsString() {
        String str;
        switch (this.direction) {
            case 0:
                str = "Norte";
                break;
            case 1:
                str = "Oriente";
                break;
            case 2:
                str = "Sur";
                break;
            case 3:
                str = "Occidente";
                break;
            default:
                str = "UNDEF";
        }
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DronePosition)) return false;
        DronePosition that = (DronePosition) o;
        return x == that.x &&
                y == that.y &&
                direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, direction);
    }

    /**
     *
     * @return String representation of this file in format (-2, 4) dirección Norte
     */
    @Override
    public String toString() {
        return "(" + x +
                ", " + y +
                ") dirección " + getDirectionAsString();
    }
}
