package corrientazo.drones4n;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DroneS4nTest {

    private DroneS4n droneS4n;

    @Before
    public void setUp() {
        droneS4n = new DroneS4n();
    }

    @Test
    public void start() {
    }

    @Test
    public void getDroneId_GoodName() {
        //given
        final String fileName02 = "in02.txt";
        final String fileName401 = "in401.txt";
        final String fileName9 = "in9.txt";

        //when / then
        assertEquals(2, droneS4n.getDroneId(fileName02));
        assertEquals(401, droneS4n.getDroneId(fileName401));
        assertEquals(9, droneS4n.getDroneId(fileName9));

    }

    @Test
    public void fileNameMatches_Matches() {
        //given
        final String fileName02 = "in02.txt";
        final String fileName401 = "in401.txt";
        final String fileName9 = "in9.txt";

        //when / then
        assertTrue(droneS4n.fileNameMatches(fileName02));
        assertTrue(droneS4n.fileNameMatches(fileName401));
        assertTrue(droneS4n.fileNameMatches(fileName9));

    }

    @Test
    public void fileNameMatches_DoNotMatch() {
        //given
        final String fileNameNotMatch1 = "in02x.txt";
        final String fileNameNotMatch2 = "401in.txt";
        final String fileNameNotMatch3 = "int9.txt";
        final String fileNameNotMatch4 = "in9.pub";
        final String fileNameNotMatch5 = "in.txt";

        //when / then
        assertFalse(droneS4n.fileNameMatches(fileNameNotMatch1));
        assertFalse(droneS4n.fileNameMatches(fileNameNotMatch2));
        assertFalse(droneS4n.fileNameMatches(fileNameNotMatch3));
        assertFalse(droneS4n.fileNameMatches(fileNameNotMatch4));
        assertFalse(droneS4n.fileNameMatches(fileNameNotMatch5));

    }

    @Test
    public void writeDeliveriesReport_correct() throws IOException {
        //given
        final int numberDigits = 3;
        int droneId = 9;
        Path outDir = Files.createTempDirectory("drones4nOutDir");
        String outFileFormat = droneS4n.buildOutFileFormat(numberDigits);
        String outFileName = String.format(outFileFormat, droneId);
        String contentHeader = "== Reporte de entregas ==";
        DronePosition dp1 = new DronePosition(1, 1, 0);
        DronePosition dp2 = new DronePosition(-2, -4, 3);
        DronePosition dp3 = new DronePosition(5, 5, 2);

        List<DronePosition> positions = Arrays.asList(dp1, dp2, dp3);

        //when
        droneS4n.writeDeliveriesReport(outDir, outFileFormat, contentHeader, droneId, positions);

        //then
        Path outFile = outDir.resolve(outFileName);
        assertTrue(Files.exists(outFile));
        String fileContent = new String(Files.readAllBytes(outFile));
        assertTrue(fileContent.contains(contentHeader));
        assertTrue(fileContent.contains(dp1.toString()));
        assertTrue(fileContent.contains(dp2.toString()));
        assertTrue(fileContent.contains(dp3.toString()));
    }
}