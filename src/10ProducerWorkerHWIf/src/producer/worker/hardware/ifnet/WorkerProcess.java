package producer.worker.hardware.ifnet;

import core.productor.HWWorkerBase;
import net.sf.json.JSONArray;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
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
            String[] ifNames = this.proxy.getNetInterfaceList();
            ArrayList<Map> data = new ArrayList<Map>();

            for (String name: ifNames) {
                Map<String, String> item = new HashMap<String, String>();

                NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
                long flags = ifconfig.getFlags();

                item.put("ifname", ifconfig.getName());
                item.put("Link encap", ifconfig.getType());
                if (NetFlags.NULL_HWADDR.equals(ifconfig.getHwaddr())) {
                    item.put("HWaddr", "-");
                } else {
                    item.put("HWaddr", ifconfig.getHwaddr());
                }

                item.put("inet addr", ifconfig.getAddress());

                if ((flags & NetFlags.IFF_POINTOPOINT) > 0) {
                    item.put("P-t-P", ifconfig.getDestination());
                } else {
                    item.put("P-t-P", "-");
                }
                if ((flags & NetFlags.IFF_BROADCAST) > 0) {
                    item.put("Bcast", ifconfig.getBroadcast());
                } else {
                    item.put("Bcast", "-");
                }

                item.put("flag", NetFlags.getIfFlagsString(flags));
                item.put("MTU", String.valueOf(ifconfig.getMtu()));
                item.put("Metric", String.valueOf(ifconfig.getMetric()));

                try {
                    NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);

                    item.put("RX packets", String.valueOf(ifstat.getRxPackets()));
                    item.put("RX errors", String.valueOf(ifstat.getRxErrors()));
                    item.put("RX dropped", String.valueOf(ifstat.getRxDropped()));
                    item.put("RX overruns", String.valueOf(ifstat.getRxOverruns()));
                    item.put("RX frame", String.valueOf(ifstat.getRxFrame()));

                    item.put("TX packets", String.valueOf(ifstat.getTxPackets()));
                    item.put("TX errors", String.valueOf(ifstat.getTxErrors()));
                    item.put("TX dropped", String.valueOf(ifstat.getTxDropped()));
                    item.put("TX overruns", String.valueOf(ifstat.getTxOverruns()));
                    item.put("TX carrier", String.valueOf(ifstat.getTxCarrier()));

                    item.put("collisions", String.valueOf(ifstat.getTxCollisions()));

                    item.put("RX bytes", String.valueOf(ifstat.getRxBytes()));
                    item.put("TX bytes", String.valueOf(ifstat.getTxBytes()));
                } catch (SigarException e) {
                    item.put("RX packets", "-");
                    item.put("RX errors", "-");
                    item.put("RX dropped", "-");
                    item.put("RX overruns", "-");
                    item.put("RX frame", "-");

                    item.put("TX packets", "-");
                    item.put("TX errors", "-");
                    item.put("TX dropped", "-");
                    item.put("TX overruns", "-");
                    item.put("TX carrier", "-");

                    item.put("collisions", "-");

                    item.put("RX bytes", "-");
                    item.put("TX bytes", "-");
                }
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
