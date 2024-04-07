package singleton;

import misc.StatisticsKeeper;
import misc.impl.StatisticsKeeperImpl;

public class StatisticsKeeperSingleton {
    private static StatisticsKeeper statisticsKeeper;
    static {
        statisticsKeeper = new StatisticsKeeperImpl();
    }

    public static StatisticsKeeper getStatisticsKeeper() {
        return statisticsKeeper;
    }
}
