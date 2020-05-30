package corrientazo.drones4n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Class where all starts.
 */
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) {
        logger.info("Starting ...");
        final DroneS4n droneS4n = new DroneS4n();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                logger.info("Shutting down ...");
                droneS4n.shutdown();
            }
        });

        try {
            droneS4n.start(
                    "/Users/jsanin/dev/workspace_personal/drones4n/workDir",
                    3, 2, 10);

        } catch (IOException e) {
            logger.error("Error", e);
        }
        logger.info("Ended");
    }
}
