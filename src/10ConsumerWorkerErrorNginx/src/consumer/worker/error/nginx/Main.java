package consumer.worker.error.nginx;

import consumer.worker.error.apache.WorkerProcess;

/**
 * @author phantom
 */
public class Main {
    public static void main(String[] args) {
        new WorkerProcess().run();
    }
}
