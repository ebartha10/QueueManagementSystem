package server;

import model.Task;

public interface Server {
    void addClient(Task givenTask);
}
