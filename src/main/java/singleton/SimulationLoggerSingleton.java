package singleton;

import misc.SimulationLogger;
import misc.impl.SimulationLoggerImpl;

public class SimulationLoggerSingleton {
    private static final SimulationLogger simulationLogger = new SimulationLoggerImpl();

    public static SimulationLogger getSimulationLogger(){
        return simulationLogger;
    }
}
