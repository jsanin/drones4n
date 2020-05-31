package corrientazo.drones4n.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FlatFileIntegration implements InstructionProvider {

    private static final Logger logger = LoggerFactory.getLogger(FlatFileIntegration.class);

    protected final static String PROCESSED_DIR_NAME = "processed";

    private Path dir;
    private Path processedDir;
    private Pattern pattern = Pattern.compile("^in(\\d+)\\.txt");


    public FlatFileIntegration(Path dir) throws IOException {
        this.dir = dir;
        this.processedDir = this.dir.getParent().resolve(PROCESSED_DIR_NAME);
        if(Files.notExists(this.dir)) {
            Files.createDirectories(this.dir);
        }
        if(Files.notExists(processedDir)) {
            Files.createDirectories(processedDir);
        }

    }

    @Override
    public Map<Integer, List<String>> nextBatch() {
        Map<Integer, List<String>> instructions = new HashMap<>();
        try {
            Set<Path> files = getFilesInDir(dir, 1);
            for (Path path : files) {
                try {
                    instructions.put(
                            getDroneId(path.getFileName().toString()),
                            Files.readAllLines(path));
                    Files.move(path, processedDir.resolve(path.getFileName()), REPLACE_EXISTING);
                } catch(Exception e) {
                    logger.error("Error reading file {}", path, e);
                }
            }
        } catch (IOException e) {
            logger.error("Error getting next batch in dir {}", dir, e);
        }
        return instructions;
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

    protected Set<Path> getFilesInDir(Path dir, int depth) throws IOException {
        try (Stream<Path> stream = Files.walk(dir, depth)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> fileNameMatches(file.getFileName().toString()))
                    .collect(Collectors.toSet());
        }
    }


}
