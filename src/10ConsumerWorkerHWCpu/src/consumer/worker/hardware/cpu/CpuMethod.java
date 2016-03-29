package consumer.worker.hardware.cpu;

import Entity.cpuinfo;
import Imple.inserthardwareinfoimple;
import core.consumer.MethodIf;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * @author phantom
 */
public class CpuMethod extends MethodIf {
    private inserthardwareinfoimple helper;

    public CpuMethod(Properties var1) {
        super(var1);
        helper = new inserthardwareinfoimple();
    }

    @Override
    public String transforms(String strJson) {
        return null;
    }


    @Override
    public boolean store(String key, String strJson) {
        try {
            JSONArray jsonArray = JSONArray.fromObject(strJson);
            Map<String, String> map = transformsKey(key);

            List<cpuinfo> cpuinfoList = new ArrayList<cpuinfo>();

            for (int i=0;i<jsonArray.size();++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                cpuinfo item = new cpuinfo();
                item.setCombined(jsonObject.getString("combined"));
                item.setIdle(jsonObject.getString("idle"));
                item.setIrq(jsonObject.getString("irq"));
                item.setNice(jsonObject.getString("nice"));
                item.setSoftirq(jsonObject.getString("softirq"));
                item.setStolen(jsonObject.getString("stolen"));
                item.setSys(jsonObject.getString("sys"));
                item.setUser(jsonObject.getString("user"));
                item.setWaitr(jsonObject.getString("wait"));

                item.setTime(jsonObject.getString("datetime"));

                item.setMacadr(map.get("mac"));
                cpuinfoList.add(item);
            }
            helper.insertCpuInfo(cpuinfoList);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
