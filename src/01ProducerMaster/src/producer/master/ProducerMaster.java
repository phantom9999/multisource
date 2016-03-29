package producer.master;

public class ProducerMaster {

    public static void main(String[] args) {

        MasterProcess process = new MasterProcess();
        if (process.check()) {
            System.out.println("运行");
            process.run();
        } else {
            System.out.println("错误");
        }
    }
}
