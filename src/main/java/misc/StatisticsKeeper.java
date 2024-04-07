package misc;

public interface StatisticsKeeper {

    int getServiceTime();
    int getWaitingTime();
    void setServiceTime(int serviceTime);
    void setWaitingTime(int waitingTime);
    void updateServiceTime(int serviceTime);
    void updateWaitingTime(int waitingTime);
}
