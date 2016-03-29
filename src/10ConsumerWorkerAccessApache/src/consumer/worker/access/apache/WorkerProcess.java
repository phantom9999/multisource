package consumer.worker.access.apache;

import core.consumer.ReceiverThread;
import core.consumer.ReceiverWorker;

public class WorkerProcess extends ReceiverWorker {

    public WorkerProcess() {
        super();
        setWorkerThread(ReceiverThread.class);
        setWorkerMethod(ApacheMethed.class);
    }
}

















