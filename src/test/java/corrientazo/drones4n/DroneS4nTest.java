package corrientazo.drones4n;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DroneS4nTest {

    private DroneS4n droneS4n;

    @Before
    public void setUp() {
        droneS4n = new DroneS4n(null,null);
    }

    @Test
    public void start() {
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