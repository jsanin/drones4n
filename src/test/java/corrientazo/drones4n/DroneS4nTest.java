package corrientazo.drones4n;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DroneS4nTest {

    private DroneS4n droneS4n;

    @Before
    public void init() {
        droneS4n = new DroneS4n();
    }

    @Test
    public void start() {
    }

    @Test
    public void getDroneId_GoodName() {
        final String fileName02 = "in02.txt";
        final String fileName401 = "in401.txt";
        final String fileName9 = "in9.txt";

        assertEquals(2, droneS4n.getDroneId(fileName02));
        assertEquals(401, droneS4n.getDroneId(fileName401));
        assertEquals(401, droneS4n.getDroneId(fileName401));

    }

    @Test
    public void fileNameMatches_Matches() {
        final String fileName02 = "in02.txt";
        final String fileName401 = "in401.txt";
        final String fileName9 = "in9.txt";

        assertTrue(droneS4n.fileNameMatches(fileName02));
        assertTrue(droneS4n.fileNameMatches(fileName401));
        assertTrue(droneS4n.fileNameMatches(fileName9));

    }

    @Test
    public void fileNameMatches_DoNotMatch() {
        final String fileNameNotMatch1 = "in02x.txt";
        final String fileNameNotMatch2 = "401in.txt";
        final String fileNameNotMatch3 = "int9.txt";
        final String fileNameNotMatch4 = "in9.pub";
        final String fileNameNotMatch5 = "in.txt";

        assertFalse(droneS4n.fileNameMatches(fileNameNotMatch1));
        assertFalse(droneS4n.fileNameMatches(fileNameNotMatch2));
        assertFalse(droneS4n.fileNameMatches(fileNameNotMatch3));
        assertFalse(droneS4n.fileNameMatches(fileNameNotMatch4));
        assertFalse(droneS4n.fileNameMatches(fileNameNotMatch5));

    }

}