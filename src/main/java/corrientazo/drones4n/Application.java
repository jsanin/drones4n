package corrientazo.drones4n;

import corrientazo.drones4n.integration.InstructionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Class where all starts.
 */
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    protected final static String IN_DIR_NAME = "in";


    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
        logger.info("Starting ...");
        if(args.length < 6) {
            printUsage();
            System.exit(1);
        }
        Map<Integer, Drone> drones = new HashMap<>();
        int countDrones = Integer.parseInt(args[0]);
        int droneCapacity = Integer.parseInt(args[1]);
        int maxBlocksAround = Integer.parseInt(args[2]);
        Class<Drone> droneClass = (Class<Drone>) Class.forName(args[4]);
        for (int i = 1; i <= countDrones; i++) {
            Drone drone = droneClass.
                    getConstructor(String.class, Integer.TYPE, DronePosition.class, Integer.TYPE).
                    newInstance(String.valueOf(i), droneCapacity, new DronePosition(0, 0, 0), maxBlocksAround);
            drones.put(i, drone);
        }

        String workDir = args[3];
        Class<InstructionProvider> instructionProviderClass =
                (Class<InstructionProvider>) Class.forName(args[5]);
        InstructionProvider instructionProvider =
                instructionProviderClass.getConstructor(Path.class).newInstance(Paths.get(workDir, IN_DIR_NAME));
        final DroneS4n droneS4n = new DroneS4n(drones, instructionProvider);

        try {
            droneS4n.start(workDir);
        } catch (IOException e) {
            logger.error("Error", e);
        }
    }

    private static void printUsage() {
        System.out.println("Usage:\njava Application [numberOfDrones] [droneCapacity] [maxBlocksAround] " +
                "[workDir] [Drone implementation class name] [Instruction provider class name]\n\n" +
                "Example:\n" +
                "java Application 10 2 10 /Users/jsanin/dev/workspace_personal/drones4n/workDir corrientazo.drones4n.DroneImpl corrientazo.drones4n.integrationFlatFileIntegration");
    }
}
