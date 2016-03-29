package producer.worker.hardware.io;


import core.productor.HWWorkerBase;
import net.sf.json.JSONArray;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

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
            FileSystem[] fslist = this.proxy.getFileSystemList();
            ArrayList<Map> data = new ArrayList<Map>();
            for (int i=0; i<fslist.length; i++) {
                if (fslist[i].getType() == FileSystem.TYPE_LOCAL_DISK) {
                    FileSystem fs = fslist[i];
                    FileSystemUsage usage = this.sigar.getFileSystemUsage(fs.getDirName());
                    Map<String, String> item = new HashMap<String, String>();

                    item.put("Filesystem", fs.getDevName());
                    item.put("Mounted on", fs.getDirName());
                    item.put("Reads", String.valueOf(usage.getDiskReads()));
                    item.put("Writes", String.valueOf(usage.getDiskWrites()));

                    if (usage.getDiskReadBytes() == Sigar.FIELD_NOTIMPL) {
                        item.put("R-bytes", "-");
                        item.put("W-bytes", "-");
                    }
                    else {
                        item.put("R-bytes", String.valueOf(usage.getDiskReadBytes()));
                        item.put("W-bytes", String.valueOf(usage.getDiskWriteBytes()));
                    }

                    if (usage.getDiskQueue() == Sigar.FIELD_NOTIMPL) {
                        item.put("Queue", "-");
                    }
                    else {
                        item.put("Queue", String.valueOf(usage.getDiskQueue()));
                    }
                    if (usage.getDiskServiceTime() == Sigar.FIELD_NOTIMPL) {
                        item.put("Svctm", "-");
                    }
                    else {
                        item.put("Svctm", String.valueOf(usage.getDiskServiceTime()));
                    }

                    item.put("datetime", String.valueOf(new Date().getTime()));

                    data.add(item);
                }
            }
            return JSONArray.fromObject(data).toString();
        } catch (SigarException e) {
            e.printStackTrace();
            return "[]";
        }

    }
}
