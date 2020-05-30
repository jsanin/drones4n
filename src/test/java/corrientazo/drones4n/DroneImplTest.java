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
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, -1);


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
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, -1);


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
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, -1);


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
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, -1);


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
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, -1);


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
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, -1);


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
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, -1);


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
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, -1);


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
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, -1);


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
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, -1);


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
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, -1);


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
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, -1);


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
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, -1);


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
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, -1);


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

    @Test
    public void move_move1OutOfRangeNorth() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, 5);

        //when
        boolean moveRet = drone.move("AAAAAA");

        //then
        assertFalse(moveRet);
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(5, 0, 0);
        assertEquals(expected, pos);

        List<DronePosition> positions = drone.getAllPositions();
        assertEquals(0, positions.size());

    }

    @Test
    public void move_move1OutOfRangeEast() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, 5);

        //when
        boolean moveRet = drone.move("DAAAAAA");

        //then
        assertFalse(moveRet);
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(0, 5, 1);
        assertEquals(expected, pos);

        List<DronePosition> positions = drone.getAllPositions();
        assertEquals(0, positions.size());

    }

    @Test
    public void move_move1OutOfRangeSouth() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, 5);

        //when
        boolean moveRet = drone.move("DDAAAAAA");

        //then
        assertFalse(moveRet);
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(-5, 0, 2);
        assertEquals(expected, pos);

        List<DronePosition> positions = drone.getAllPositions();
        assertEquals(0, positions.size());

    }

    @Test
    public void move_move1OutOfRangeWest() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, 5);

        //when
        boolean moveRet = drone.move("IAAAAAA");

        //then
        assertFalse(moveRet);
        assertEquals(DroneStatus.ERROR_OUT_OF_RANGE, drone.status());
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(0, -5, 3);
        assertEquals(expected, pos);

        List<DronePosition> positions = drone.getAllPositions();
        assertEquals(0, positions.size());

    }

    @Test
    public void move_move2OutOfRangeNorth() {
        //given
        final String id = "1";
        final int capacity = 3;
        DronePosition initialPosition = new DronePosition(0, 0, 0);
        DroneImpl drone = new DroneImpl(id, capacity, initialPosition, 5);

        //when
        boolean moveRet = drone.move("AA");
        assertTrue(moveRet);
        assertEquals(DroneStatus.REACH_DESTINATION, drone.status());
        moveRet = drone.move("DAIAAAA");

        //then
        assertFalse(moveRet);
        assertEquals(DroneStatus.ERROR_OUT_OF_RANGE, drone.status());
        DronePosition pos = drone.getCurrentPosition();
        DronePosition expected = new DronePosition(5, 1, 0);
        assertEquals(expected, pos);

        List<DronePosition> positions = drone.getAllPositions();
        assertEquals(1, positions.size());
        assertEquals(new DronePosition(2, 0, 0), positions.get(0));

    }



}