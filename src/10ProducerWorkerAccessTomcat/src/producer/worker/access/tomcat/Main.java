package producer.worker.access.tomcat;


public class Main {
    public static void main(String[] args) throws Exception {
        // System.setProperty("env", "::1");
        // System.setProperty("target", "producer");
        // System.setProperty("kafka.config", "/home/phantom/文档/java/multisource/etc/producer.xml");
        // System.setProperty("worker.config", "/home/phantom/文档/java/multisource/etc/producer/access.apache.xml");

        new WorkerProcess().run();
    }
}
