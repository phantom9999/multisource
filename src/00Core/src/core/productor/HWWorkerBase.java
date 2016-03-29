package core.productor;


import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.SigarProxyCache;

public abstract class HWWorkerBase extends ProducerWorker {
    protected Sigar sigar;
    protected SigarProxy proxy;

    public HWWorkerBase() {
        super();
        sigar = new Sigar();
        proxy = SigarProxyCache.newInstance(this.sigar);
    }

    protected abstract String getData();

    public void run() {
        try {
            while (true) {
                String strJson = getData();
                System.out.println(strJson);
                sender.send(strJson);
                //sleep(Integer.valueOf(properties.getProperty("sleeptime")));

                Thread.sleep(Long.valueOf(config.getProperty("producer.sleeptime")));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
