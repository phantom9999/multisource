package core.productor;


import core.master.WorkerBase;
import core.productor.Sender;

public abstract class ProducerWorker extends WorkerBase {
    public Sender sender;
    public ProducerWorker() {
        super();
        System.out.println(config.getProperty("key"));
        sender = new Sender(kafka, config);
    }
}
