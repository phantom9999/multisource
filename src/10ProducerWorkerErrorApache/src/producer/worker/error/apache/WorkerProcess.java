package producer.worker.error.apache;

import core.productor.LogWorkerBase;

public class WorkerProcess extends LogWorkerBase{
    public WorkerProcess() {
        super();
        setPattern(ErrorPattern.class);
    }
}
