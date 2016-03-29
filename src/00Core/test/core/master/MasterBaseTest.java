package core.master;

import org.junit.Test;

import static org.junit.Assert.*;


public class MasterBaseTest {

    @Test
    public void testReadEnv() throws Exception {

    }

    @Test
    public void testReadProp() throws Exception {

    }

    @Test
    public void testCheckClass() throws Exception {
        MasterBase mb = new MasterBase("null");
        assertTrue(mb.checkClass("producer.worker.access.apache.Main"));
        assertTrue(mb.checkClass("producer.worker.error.apache.Main"));
        assertTrue(mb.checkClass("producer.worker.hardware.cpu.Main"));
        assertTrue(mb.checkClass("producer.worker.hardware.disk.Main"));
        assertTrue(mb.checkClass("producer.worker.hardware.ifnet.Main"));
        assertTrue(mb.checkClass("producer.worker.hardware.io.Main"));


    }

    @Test
    public void testCheckClassList() throws Exception {

    }

    @Test
    public void testCheckFileList() throws Exception {

    }

    @Test
    public void testCheckWorkerProp() throws Exception {

    }

    @Test
    public void testGetWorkerConfigList() throws Exception {

    }

    @Test
    public void testCheckKafkaConfig() throws Exception {

    }

    @Test
    public void testCheckCoreEnv() throws Exception {

    }

    @Test
    public void testGetMac() throws Exception {

    }

    @Test
    public void testCheck() throws Exception {

    }

    @Test
    public void testInit() throws Exception {

    }

    @Test
    public void testRun() throws Exception {

    }
}