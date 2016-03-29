package producer.worker.hardware.cpu;

import core.productor.HWWorkerBase;
import net.sf.json.JSONArray;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarLoader;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WorkerProcess extends HWWorkerBase {

    public WorkerProcess() {
        super();
    }
    @Override
    protected String getData() {
        try {
            CpuPerc[] cpus = sigar.getCpuPercList();
            ArrayList<Map> data = new ArrayList<Map>();
            for (int i=0;i < cpus.length;++i) {
                CpuPerc cpu = cpus[i];
                Map<String, String> item = new HashMap<String, String>();
                item.put("user", String.valueOf(cpu.getUser()));
                item.put("sys", String.valueOf(cpu.getSys()));
                item.put("idle", String.valueOf(cpu.getIdle()));
                item.put("wait", String.valueOf(cpu.getWait()));
                item.put("nice", String.valueOf(cpu.getNice()));
                item.put("combined", String.valueOf(cpu.getCombined()));
                item.put("irq", String.valueOf(cpu.getIrq()));
                if (SigarLoader.IS_LINUX) {
                    item.put("softirq", String.valueOf(cpu.getSoftIrq()));
                    item.put("stolen", String.valueOf(cpu.getStolen()));
                }

                item.put("datetime", String.valueOf( new Date().getTime()));

                data.add(item);
            }
            return JSONArray.fromObject(data).toString();
        } catch (SigarException e) {
            // e.printStackTrace();
            return "[]";
        }
    }
}
