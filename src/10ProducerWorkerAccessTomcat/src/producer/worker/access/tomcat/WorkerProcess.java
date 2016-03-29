package producer.worker.access.tomcat;

import core.productor.LogWorkerBase;

public class WorkerProcess extends LogWorkerBase {
    public WorkerProcess() {
        super();
        setPattern(AccessPattern.class);
    }
}
