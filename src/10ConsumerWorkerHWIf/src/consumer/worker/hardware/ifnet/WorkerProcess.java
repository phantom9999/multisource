package consumer.worker.hardware.ifnet;

import core.consumer.ReceiverThread;
import core.consumer.ReceiverWorker;

/**
 * @author phantom
 */
public class WorkerProcess extends ReceiverWorker {


    public WorkerProcess() {
        super();
        setWorkerThread(ReceiverThread.class);
        setWorkerMethod(IfMethod.class);
    }
}
