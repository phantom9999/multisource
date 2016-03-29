package producer.worker.access.apache;

import core.productor.LogWorkerBase;

public class WorkerProcess extends LogWorkerBase {
    public WorkerProcess() {
        super();
        setPattern(AccessPattern.class);
    }
}
