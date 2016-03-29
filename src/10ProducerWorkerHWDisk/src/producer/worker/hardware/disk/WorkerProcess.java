package producer.worker.hardware.disk;


import core.productor.HWWorkerBase;
import net.sf.json.JSONArray;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.NfsFileSystem;
import org.hyperic.sigar.SigarException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WorkerProcess extends HWWorkerBase{
    public WorkerProcess() {
        super();
    }
    @Override
    protected String getData() {
        try {
            ArrayList<Map> data = new ArrayList<Map>();
            FileSystem[] fslist = this.proxy.getFileSystemList();

            for (FileSystem fs: fslist) {
                long used, avail, total, pct;
                try {
                    FileSystemUsage usage;
                    if (fs instanceof NfsFileSystem) {
                        NfsFileSystem nfs = (NfsFileSystem)fs;
                        if (!nfs.ping()) {
                            continue;
                        }
                    }
                    usage = sigar.getFileSystemUsage(fs.getDirName());
                    used = usage.getTotal() - usage.getFree();
                    avail = usage.getAvail();
                    total = usage.getTotal();

                    pct = (long)(usage.getUsePercent() * 100);
                } catch (SigarException e) {
                    //e.g. on win32 D:\ fails with "Device not ready"
                    //if there is no cd in the drive.
                    used = avail = total = pct = 0;
                }
                String usePct = pct + "%";

                Map<String, String> item = new HashMap<String, String>();
                item.put("devname", fs.getDevName());
                item.put("total", String.valueOf(total));
                item.put("used", String.valueOf(used));
                item.put("avail", String.valueOf(avail));
                item.put("usePct", usePct);
                item.put("dirname", fs.getDirName());
                item.put("mounts", fs.getSysTypeName() + "/" + fs.getTypeName());

                item.put("datetime", String.valueOf(new Date().getTime()));

                data.add(item);
            }

            return JSONArray.fromObject(data).toString();

        } catch (SigarException e) {
            // e.printStackTrace();
            return "[]";
        }

    }
}
