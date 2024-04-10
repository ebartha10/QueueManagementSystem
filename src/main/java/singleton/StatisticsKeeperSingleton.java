package singleton;

import misc.StatisticsKeeper;
import misc.impl.StatisticsKeeperImpl;

public class StatisticsKeeperSingleton {
    private final static StatisticsKeeper statisticsKeeper = new StatisticsKeeperImpl();

    public static StatisticsKeeper getStatisticsKeeper() {
        return statisticsKeeper;
    }
}
