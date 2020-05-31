package corrientazo.drones4n;

import corrientazo.drones4n.integration.InstructionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class DroneS4n {

    private static final Logger logger = LoggerFactory.getLogger(DroneS4n.class);

    protected final static String OUT_DIR_NAME = "out";
    protected final static String PREFIX_OUT_NAME = "out";
    protected final static String SUFFIX_OUT_NAME = ".txt";
    protected final static String CONTENT_FILE_HEADER = "== Reporte de entregas ==";

    final protected Map<Integer, Drone> drones;
    final protected InstructionProvider instructionProvider;

    public DroneS4n(Map<Integer, Drone> drones, InstructionProvider instructionProvider) {
        this.instructionProvider = instructionProvider;
        this.drones = drones;
    }


    public void start(String workDir) throws IOException {
        logger.info("Just started!");
        int sizeDrones = drones.size();
        final int numberDigits = getNumberDigits(sizeDrones);
        final String outFileFormat = buildOutFileFormat(numberDigits);

        final Path outDir = Paths.get(workDir, OUT_DIR_NAME);
        if(Files.notExists(outDir)) {
            Files.createDirectories(outDir);
        }

        ExecutorService executor = Executors.newFixedThreadPool(sizeDrones);

        while(true) {

            Map<Integer, List<String>> droneInstructions = instructionProvider.nextBatch();
            if(!droneInstructions.isEmpty()) {
                droneInstructions.entrySet().stream().map(droneInst -> {
                    OperatorWorker worker = null;
                    int droneId = droneInst.getKey();
                    Drone drone = drones.get(droneId);
                    if(drone != null) {
                        List<String> lines = droneInst.getValue();
                        logger.debug("DroneId {} Instructions size {}", droneId, lines.size());
                        worker = new OperatorWorker(drone, lines);
                    } else {
                        logger.warn("DroneId {} does not exist", droneId);
                    }
                    return worker;
                }).filter(operatorWorker -> operatorWorker != null)
                  .map(operatorWorker ->
                        CompletableFuture.supplyAsync(() -> operatorWorker.start(), executor).
                                thenAccept(drone -> {
                                    try {
                                        writeDeliveriesReport(outDir, outFileFormat, CONTENT_FILE_HEADER,
                                                Integer.parseInt(drone.getId()), drone.getAllPositions());
                                    } catch (IOException e) {
                                        logger.error("Error writing output for droneId {}", drone.getId(), e);
                                    }
                                })
                ).collect(Collectors.toList());
            } else {
                logger.debug("No instructions");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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
