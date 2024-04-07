package simulation;

import misc.SharedCounter;
import misc.SimulationLogger;
import misc.StatisticsKeeper;
import misc.impl.SimulationLoggerImpl;
import misc.impl.StatisticsKeeperImpl;
import model.Task;
import scheduler.Scheduler;
import scheduler.impl.SchedulerImpl;
import server.impl.ServerImpl;
import singleton.SharedCounterSingleton;
import singleton.SimulationLoggerSingleton;

import java.util.*;


public class SimulationManager implements Runnable {
    private int timeLimit;
    private Scheduler scheduler;
    private List<Task> taskList;
    private List<ServerImpl> serverList;
    private int strategyType;
    private RandomTaskGenerator randomTaskGenerator;
    private final StatisticsKeeper statisticsKeeper;
    private final SharedCounter sharedCounter;
    private final SimulationLogger simulationLogger;

    public SimulationManager(int numberOfServers, int timeLimit, int minProcessingTime, int maxProcessingTime, int minArrival, int maxArrival, int numberOfTasks) {
        this.timeLimit = timeLimit;
        sharedCounter = SharedCounterSingleton.getSharedCounter();
        simulationLogger = SimulationLoggerSingleton.getSimulationLogger();
        statisticsKeeper = new StatisticsKeeperImpl();

        serverList = generateServers(numberOfServers);
        scheduler = new SchedulerImpl(numberOfServers, serverList);
        randomTaskGenerator = new RandomTaskGenerator(numberOfTasks, timeLimit, minArrival, maxArrival, minProcessingTime, maxProcessingTime);
        taskList = randomTaskGenerator.generateTaskList();
    }
    private List<ServerImpl> generateServers(int serverNumber){
        List<ServerImpl> serverList = new ArrayList<>();
        for (int i = 0; i < serverNumber; i++) {
            ServerImpl server = new ServerImpl();
            Thread thread = new Thread(server);
            serverList.add(server);
            thread.start();
        }
        return serverList;
    }

    @Override
    public void run() {
        sharedCounter.setSimulationTime(-1);
        sharedCounter.setFinishedTasks(0);
        simulationLogger.logSimulationStart(taskList, serverList.size(), timeLimit);

        while (sharedCounter.getSimulationTime() < timeLimit) {
            sharedCounter.countTime();
            //System.out.println("TIME :" + sharedCounter.getSimulationTime() + scheduler.toString());

            int serviceTime = dispatchTasksForCurrentTime();
            statisticsKeeper.updateServiceTime(serviceTime);


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("SIMULATION INTERRUPTED!");
                return;
            }
        }
        for (Task t : taskList){
            statisticsKeeper.updateWaitingTime(t.getWaitingTime());
        }
        simulationLogger.logSimulationEnd((float) statisticsKeeper.getServiceTime() / taskList.size(), (float) statisticsKeeper.getWaitingTime() / taskList.size());
        //System.out.println("END SIM\nAVERAGE SERVICE TIME: " + (float) serviceTime / numberOfTasks + "\nAVERAGE WAITING TIME: " + (float) waitingTime / numberOfTasks);
    }
    private int dispatchTasksForCurrentTime(){
        int serviceTime = 0;
        Iterator<Task> it = taskList.iterator();
        while (it.hasNext()) {
            Task t = it.next();
            if (t.getArrivalTime() == sharedCounter.getSimulationTime() && !t.isDispatched()) {
                scheduler.dispatchTask(t, strategyType);
                t.setDispatched(true);
                serviceTime += t.getServiceTime();
            }
        }
        return serviceTime;
    }
    public Scheduler getScheduler() {
        return scheduler;
    }

    public int getNumberOfServers() {
        return scheduler.getNumberOfServers();
    }

    public List<ServerImpl> getServerList() {
        return serverList;
    }
}
