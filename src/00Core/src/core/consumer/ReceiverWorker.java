package core.consumer;

import core.config.ConfigReader;
import core.master.WorkerBase;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


public abstract class ReceiverWorker extends WorkerBase {

    protected ArrayList<Properties> methodConfigList;
    protected Class workerThread;
    protected Class workerMethod;

    public ReceiverWorker() {
        super();
        kafka.put("group.id", config.getProperty("consumer.group"));
    }


    public void setWorkerThread(Class workerThread) {
        this.workerThread = workerThread;
    }

    public void setWorkerMethod(Class workerMethod) {
        this.workerMethod = workerMethod;
    }


    public void run() {
        // 启动特定的java
        ArrayList<ReceiverThread> threadArrayList = new ArrayList<ReceiverThread>();
        int times = Integer.valueOf(config.getProperty("consumer.threads"));
        try {
            for (int i=0;i<times;++i) {
                ReceiverThread receiverThread = (ReceiverThread) workerThread.newInstance();
                Constructor constructor = workerMethod.getConstructor(Properties.class);

                receiverThread.setMethodIfMap((MethodIf) constructor.newInstance(config));
                receiverThread.setKafkaConfig(kafka);
                receiverThread.setTopic(config.getProperty("consumer.topic"));
                receiverThread.start();
                threadArrayList.add(receiverThread);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        try {
            for (Iterator iterator=threadArrayList.iterator();iterator.hasNext();) {
                ReceiverThread tmp = (ReceiverThread)iterator.next();
                tmp.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
}
