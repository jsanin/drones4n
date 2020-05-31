package corrientazo.drones4n.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

public class FlatFileIntegrationTest {

    private FlatFileIntegration flatFileIntegration;

    @Before
    public void setUp() throws Exception {
        flatFileIntegration = new FlatFileIntegration(Files.createTempDirectory("flatFileTesting").resolve("in"));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void nextBatch_allMatch() throws IOException {
        //given
        Path dir = Files.createTempDirectory("flatFileTesting").resolve("in");
        FlatFileIntegration flatFileIntegration = new FlatFileIntegration(dir);
        FlatFileIntegration flatFileIntegrationSpy = Mockito.spy(flatFileIntegration);

        createTestingInputFiles(dir);

        //when
        Map<Integer, List<String>> droneInstructions = flatFileIntegrationSpy.nextBatch();

        //then
        assertNotNull(droneInstructions);
        assertEquals(3, droneInstructions.size());
        verify(flatFileIntegrationSpy).getFilesInDir(any(Path.class), anyInt());
        assertTrue(flatFileIntegrationSpy.getFilesInDir(dir, 1).isEmpty());
        assertEquals(3,
                flatFileIntegrationSpy.getFilesInDir(
                        dir.getParent().resolve(FlatFileIntegration.PROCESSED_DIR_NAME), 1).size());

    }

    @Test
    public void nextBatch_OneDoesNotMatch() throws IOException {
        //given
        Path dir = Files.createTempDirectory("flatFileTesting").resolve("in");
        FlatFileIntegration flatFileIntegration = new FlatFileIntegration(dir);
        FlatFileIntegration flatFileIntegrationSpy = Mockito.spy(flatFileIntegration);

        createTestingInputFiles(dir);
        createTestingInputFileDoesNotMatch(dir);

        //when
        Map<Integer, List<String>> droneInstructions = flatFileIntegrationSpy.nextBatch();

        //then
        assertNotNull(droneInstructions);
        assertEquals(3, droneInstructions.size());
        verify(flatFileIntegrationSpy).getFilesInDir(any(Path.class), anyInt());
        try (Stream<Path> stream = Files.walk(dir, 1)) {
            Set<Path> filesInDir = stream.filter(file -> !Files.isDirectory(file))
                    .collect(Collectors.toSet());
            assertEquals(1, filesInDir.size());
        }

        assertEquals(3,
                flatFileIntegrationSpy.getFilesInDir(
                        dir.getParent().resolve(FlatFileIntegration.PROCESSED_DIR_NAME), 1).size());

    }

    private void createTestingInputFileDoesNotMatch(Path dir) throws IOException {
        Path file = Files.createTempFile(dir, "wrongPrefix", ".txt");
        String content1 = "AAAAA\n" +
                "AAAAAA\n" +
                "DDDDDD";
        Files.write(file, content1.getBytes());

    }

    private void createTestingInputFiles(Path dir) throws IOException {
        final String fileName01 = "in01.txt";
        final String fileName02 = "in02.txt";
        final String fileName03 = "in03.txt";

        Path file1 = dir.resolve(fileName01);
        String content1 = "AAAAA\n" +
                "AAAAAA\n" +
                "DDDDDD";
        Files.write(file1, content1.getBytes());


        Path file2 = dir.resolve(fileName02);
        String content2 = "";
        Files.write(file2, content2.getBytes());

        Path file3 = dir.resolve(fileName03);
        String content3 = "AAAAA\n" +
                "AAAIIAAA\n" +
                "DIIII";
        Files.write(file3, content3.getBytes());
    }

    @Test
    public void getDroneId_GoodName() {
        //given
        final String fileName02 = "in02.txt";
        final String fileName401 = "in401.txt";
        final String fileName9 = "in9.txt";

        //when / then
        assertEquals(2, flatFileIntegration.getDroneId(fileName02));
        assertEquals(401, flatFileIntegration.getDroneId(fileName401));
        assertEquals(9, flatFileIntegration.getDroneId(fileName9));

    }

    @Test
    public void getDroneId_doNotMatch() {
        //given
        final String fileName = "xin02.txt";

        //when / then
        assertEquals(-1, flatFileIntegration.getDroneId(fileName));

    }

    @Test
    public void fileNameMatches_Matches() {
        //given
        final String fileName02 = "in02.txt";
        final String fileName401 = "in401.txt";
        final String fileName9 = "in9.txt";

        //when / then
        assertTrue(flatFileIntegration.fileNameMatches(fileName02));
        assertTrue(flatFileIntegration.fileNameMatches(fileName401));
        assertTrue(flatFileIntegration.fileNameMatches(fileName9));

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
        assertFalse(flatFileIntegration.fileNameMatches(fileNameNotMatch1));
        assertFalse(flatFileIntegration.fileNameMatches(fileNameNotMatch2));
        assertFalse(flatFileIntegration.fileNameMatches(fileNameNotMatch3));
        assertFalse(flatFileIntegration.fileNameMatches(fileNameNotMatch4));
        assertFalse(flatFileIntegration.fileNameMatches(fileNameNotMatch5));

    }
}