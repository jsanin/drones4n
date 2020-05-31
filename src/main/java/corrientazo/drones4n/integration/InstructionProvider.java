package corrientazo.drones4n.integration;

import java.util.List;
import java.util.Map;

public interface InstructionProvider {

    /**
     * Returns next batch to process or an empty map in case there is nothing to process
     * @return key map is the droneId and the value is a list of instructions
     */
    Map<Integer, List<String>> nextBatch();
}
