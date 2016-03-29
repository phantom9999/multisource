package core.productor;


import java.lang.reflect.Constructor;

public abstract class LogWorkerBase extends ProducerWorker {

    private Class pattern;

    public LogWorkerBase() {
        super();
    }

    @Override
    public void run() throws Exception {
        LogReader logReader = new LogReader(
                config.getProperty("producer.dir"),
                config.getProperty("producer.filename"));

        // 文件检测
        logReader.checkFile();

        try {
            Thread.sleep(Integer.valueOf(config.getProperty("producer.sleeptime")));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logReader.adjust4begin();

        try {
            while (true) {
                LogWorkerThread worker = new LogWorkerThread();
                worker.setBeginPoint(logReader.begin);
                worker.setEndPoint(logReader.end);
                worker.setFile(logReader.file);
                Constructor constructor = pattern.getConstructor(String.class);
                worker.setPattern((Pattern) constructor.newInstance(config.getProperty("producer.format")));
                worker.setSender(this.sender);//new Sender(kafka, config));
                worker.run();

                Thread.sleep(Integer.valueOf(config.getProperty("producer.sleeptime")));

                // 位置修正
                logReader.adjust();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setPattern(Class pattern) {
        this.pattern = pattern;
    }
}
