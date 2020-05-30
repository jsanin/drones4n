package corrientazo.drones4n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class DroneS4n {

    private static final Logger logger = LoggerFactory.getLogger(DroneS4n.class);

    protected final static String IN_DIR_NAME = "in";
    protected final static String PROCESSED_DIR_NAME = "processed";
    protected final static String OUT_DIR_NAME = "out";
    protected final static String PREFIX_OUT_NAME = "out";
    protected final static String SUFFIX_OUT_NAME = ".txt";
    protected final static String CONTENT_FILE_HEADER = "== Reporte de entregas ==";

    Pattern pattern = Pattern.compile("in(\\d+)\\.txt");

    Map<Integer, Drone> drones = new HashMap<>();



    public void start(String workDir, int countDrones, int droneCapacity) throws IOException {
        logger.info("Just started!");
        final int numberDigits = getNumberDigits(countDrones);
        final String outFileFormat = buildOutFileFormat(numberDigits);

        final Path inDir = Paths.get(workDir, IN_DIR_NAME);
        final Path processedDir = Paths.get(workDir, PROCESSED_DIR_NAME);
        final Path outDir = Paths.get(workDir, OUT_DIR_NAME);
        if(Files.notExists(inDir)) {
            Files.createDirectories(inDir);
        }
        if(Files.notExists(processedDir)) {
            Files.createDirectories(processedDir);
        }
        if(Files.notExists(outDir)) {
            Files.createDirectories(outDir);
        }

        for (int i = 1; i <= countDrones; i++) {
            drones.put(i, new DroneImpl(String.valueOf(i), droneCapacity, new DronePosition(0, 0, 0)));
        }

        while(true) {

            Set<Path> files = getFilesInDir(inDir, 1);
            if(files.size() > 0) {
                logger.info("Files found: {}", files.size());
                files.stream().forEach(path -> {
                    try {
                        int droneId = getDroneId(path.getFileName().toString());
                        Drone drone = drones.get(droneId);
                        if(drone != null) {
                            logger.debug("Processing file {}", path);
                            List<String> lines = Files.readAllLines(path);
                            logger.debug("File {} lines {}", path, lines.size());
                            Files.move(path, processedDir.resolve(path.getFileName()), REPLACE_EXISTING);
                            new OperatorWorker(drone, lines).start();
                            writeDeliveriesReport(outDir, outFileFormat, CONTENT_FILE_HEADER,
                                    droneId, drone.getAllPositions());
                        } else {
                            logger.warn("DroneId {} does not exist", droneId);
                            Files.move(path, processedDir.resolve(path.getFileName()), REPLACE_EXISTING);
                        }
                    } catch (IOException e) {
                        logger.error("Error", e);
                    }
                });
            } else {
                logger.debug("No files found");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //logger.info("bye!");
    }

    private Set<Path> getFilesInDir(Path dir, int depth) throws IOException {
        try (Stream<Path> stream = Files.walk(dir, depth)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> fileNameMatches(file.getFileName().toString()))
                    .collect(Collectors.toSet());
        }
    }

    protected int getDroneId(String fileName) {
        Matcher m = pattern.matcher(fileName);
        if(m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return -1;
    }

    protected boolean fileNameMatches(String fileName) {
        return pattern.matcher(fileName).matches();
    }

    protected void writeDeliveriesReport(
            Path outDir, String outFileFormat, String contentHeader, int droneId,
            List<DronePosition> positions) throws IOException {
        String outFileName = String.format(outFileFormat, droneId);
        StringBuilder sb = new StringBuilder();
        if(contentHeader != null && contentHeader.length() > 0) {
            sb.append(contentHeader).append("\n");
        }
        for (DronePosition pos :
                positions) {
            sb.append(pos).append("\n");

        }
        Path outPath = outDir.resolve(outFileName);
        Files.write(outPath, sb.toString().getBytes());
    }

    private int getNumberDigits(int integer) {
        return (int) (Math.log10(integer) + 1);
    }

    protected String buildOutFileFormat(int numberDigits) {
        return new StringBuilder()
                .append(PREFIX_OUT_NAME)
                .append("%0").append(numberDigits).append("d")
                .append(SUFFIX_OUT_NAME).toString();
    }


    public void shutdown() {
        logger.info("Releasing resources ...");
    }
}
