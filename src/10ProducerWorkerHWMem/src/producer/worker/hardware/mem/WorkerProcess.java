package producer.worker.hardware.mem;


import core.productor.HWWorkerBase;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

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
            Mem mem = sigar.getMem();
            Swap swap = sigar.getSwap();

            ArrayList<Map> data = new ArrayList<Map>();

            Map<String, Long> item1 = new HashMap<String, Long>();
            item1.put("mem total", mem.getTotal());
            item1.put("mem used", mem.getUsed());
            item1.put("mem free", mem.getFree());
            item1.put("mem actualUsed", mem.getActualUsed());
            item1.put("mem actualFree", mem.getActualFree());
            item1.put("mem datetime", new Date().getTime());
            item1.put("swap total", swap.getTotal());
            item1.put("swap used", swap.getUsed());
            item1.put("swap free", swap.getFree());
            item1.put("swap datetime", new Date().getTime());

            data.add(item1);


            return JSONArray.fromObject(data).toString();
        } catch (SigarException e) {
            // e.printStackTrace();
            return "[]";
        }

    }
}
