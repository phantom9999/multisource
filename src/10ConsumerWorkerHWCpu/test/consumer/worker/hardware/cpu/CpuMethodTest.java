package consumer.worker.hardware.cpu;

import Entity.cpuinfo;
import Imple.inserthardwareinfoimple;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author phantom
 */
public class CpuMethodTest {

    @Test
    public void testStore() throws Exception {
        inserthardwareinfoimple helper = new inserthardwareinfoimple();
        List<cpuinfo> cpuinfoList = new ArrayList<cpuinfo>();

        for (int i=0;i<4;++i) {
            cpuinfo item = new cpuinfo();
            item.setCombined(String.valueOf(i));
            item.setIdle(String.valueOf(i));
            item.setIrq(String.valueOf(i));
            item.setNice(String.valueOf(i));
            item.setSoftirq(String.valueOf(i));
            item.setStolen(String.valueOf(i));
            item.setSys(String.valueOf(i));
            item.setUser(String.valueOf(i));
            item.setWaitr(String.valueOf(i));

            item.setTime("2015-02-02");


            item.setMacadr("asdasdasdasdasd");

            cpuinfoList.add(item);


        }
        helper.insertCpuInfo(cpuinfoList);


    }
}