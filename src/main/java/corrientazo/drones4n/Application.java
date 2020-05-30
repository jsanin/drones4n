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
        try {
            new DroneS4n().start("/Users/jsanin/dev/workspace_personal/drones4n/workDir", 1, 2);
        } catch (IOException e) {
            logger.error("Error", e);
        }
        logger.info("Ended");
    }
}
