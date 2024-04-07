package simulation;

import model.Task;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTaskGenerator {
    private final int numberOfTasks, simulationInterval, minArrival, maxArrival, minProcessingTime, maxProcessingTime;

    public RandomTaskGenerator(int numberOfTasks, int simulationInterval, int minArrival, int maxArrival, int minProcessingTime, int maxProcessingTime) {
        this.numberOfTasks = numberOfTasks;
        this.simulationInterval = simulationInterval;
        this.minArrival = minArrival;
        this.maxArrival = maxArrival;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
    }

    /**
     * Generates an ordered task list for parameters.
     * @return List of Task Objects.
     */
    public List<Task> generateTaskList() {
        List<Task> tasks = new ArrayList<>();
        Random rand = new Random(Instant.now().getEpochSecond());
        for (int i = 0; i < numberOfTasks; i++) {
            Task task = new Task();
            task.setID(i);
            task.setArrivalTime(rand.nextInt(maxArrival - minArrival) + minArrival);
            task.setServiceTime(rand.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime);
            // Fix any tasks that are over the time limit.
            if (task.getArrivalTime() + task.getServiceTime() > simulationInterval){
                task.setServiceTime(simulationInterval - task.getArrivalTime());
            }
            tasks.add(task);
        }
        tasks.sort((o1, o2) -> {
            if(o1.getArrivalTime() == o2.getArrivalTime())
                return 0;
            else if(o1.getArrivalTime() > o2.getArrivalTime()){
                return 1;
            }
            else return -1;
        });
        return tasks;
    }
}
