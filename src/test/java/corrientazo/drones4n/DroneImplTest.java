package corrientazo.drones4n;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DroneImplTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void move_oneStep() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition);


        //when
        drone.move("A");

        //then
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(1, 0, 0);
        assertEquals(expected, pos);


    }

    @Test
    public void move_turnRight() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition);


        //when
        drone.move("D");

        //then
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(0, 0, 1);
        assertEquals(expected, pos);


    }

    @Test
    public void move_turnRight2() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition);


        //when
        drone.move("DD");

        //then
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(0, 0, 2);
        assertEquals(expected, pos);


    }

    @Test
    public void move_turnRight3() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition);


        //when
        drone.move("DDD");

        //then
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(0, 0, 3);
        assertEquals(expected, pos);


    }

    @Test
    public void move_turnRight4() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition);


        //when
        drone.move("DDDD");

        //then
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(0, 0, 0);
        assertEquals(expected, pos);


    }

    @Test
    public void move_turnLeft() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition);


        //when
        drone.move("I");

        //then
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(0, 0, 3);
        assertEquals(expected, pos);


    }

    @Test
    public void move_turnLeft2() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition);


        //when
        drone.move("II");

        //then
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(0, 0, 2);
        assertEquals(expected, pos);


    }

    @Test
    public void move_turnLeft3() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition);


        //when
        drone.move("III");

        //then
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(0, 0, 1);
        assertEquals(expected, pos);

    }

    @Test
    public void move_turnLeft4() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition);


        //when
        drone.move("IIII");

        //then
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(0, 0, 0);
        assertEquals(expected, pos);

    }

    @Test
    public void move_turnRightThenAdvance() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition);


        //when
        drone.move("DA");

        //then
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(0, 1, 1);
        assertEquals(expected, pos);

    }

    @Test
    public void move_turnRight2ThenAdvance() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition);


        //when
        drone.move("DDA");

        //then
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(-1, 0, 2);
        assertEquals(expected, pos);

    }

    @Test
    public void move_turnLeftThenAdvance() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition);


        //when
        drone.move("IA");

        //then
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(0, -1, 3);
        assertEquals(expected, pos);

    }

    @Test
    public void move_turnLeft2ThenAdvance() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition);


        //when
        drone.move("IIA");

        //then
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(-1, 0, 2);
        assertEquals(expected, pos);

    }

    @Test
    public void move_multipleMoves() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition);


        //when
        List<String> instructions = Arrays.asList("AAAAIAA", "DDDAIAD", "AAIADAD");
        for (String inst : instructions) {
            drone.move(inst);
        }


        //then
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(0, 0, 3);
        assertEquals(expected, pos);

        List<DronePosition> positions = drone.getAllPositions();
        assertEquals(3, positions.size());
        assertEquals(new DronePosition(4, -2, 3), positions.get(0));
        assertEquals(new DronePosition(3, -1, 2), positions.get(1));
        assertEquals(new DronePosition(0, 0, 3), positions.get(2));


    }

}