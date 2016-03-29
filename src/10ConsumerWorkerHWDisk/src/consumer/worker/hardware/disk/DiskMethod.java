package consumer.worker.hardware.disk;

import Entity.diskinfo;
import Imple.inserthardwareinfoimple;
import core.consumer.MethodIf;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author phantom
 */
public class DiskMethod extends MethodIf {
    private inserthardwareinfoimple helper;
    public DiskMethod(Properties var1) {
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
            ArrayList<diskinfo> diskinfoArrayList = new ArrayList<diskinfo>();

            for (int i=0;i<jsonArray.size();++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                diskinfo item = new diskinfo();
                item.setAvail(jsonObject.getString("avail"));
                item.setDevname(jsonObject.getString("devname"));
                item.setDirname(jsonObject.getString("dirname"));
                item.setMounts(jsonObject.getString("mounts"));
                item.setTotal(jsonObject.getString("total"));
                item.setUsed(jsonObject.getString("used"));
                item.setUsepct(jsonObject.getString("usePct"));

                item.setMacadr(map.get("mac"));
                item.setTime(jsonObject.getString("datetime"));
                diskinfoArrayList.add(item);

            }
            helper.intsertDiskInfo(diskinfoArrayList);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
