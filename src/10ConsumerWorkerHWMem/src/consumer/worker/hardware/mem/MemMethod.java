package consumer.worker.hardware.mem;

import Entity.memoryinfo;
import Imple.inserthardwareinfoimple;
import core.consumer.MethodIf;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

/**
 * @author phantom
 */
public class MemMethod extends MethodIf {
    private inserthardwareinfoimple helper;

    public MemMethod(Properties var1) {
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
            ArrayList<memoryinfo> memoryinfoArrayList = new ArrayList<memoryinfo>();
            for (int i=0;i<jsonArray.size();++i) {
                // todo: 缺少交换区数据
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                memoryinfo item = new memoryinfo();
                item.setActualfree(jsonObject.getString("mem actualFree"));
                item.setActualused(jsonObject.getString("mem actualUsed"));
                item.setFree(jsonObject.getString("mem free"));
                item.setTotal(jsonObject.getString("mem total"));
                item.setUsed(jsonObject.getString("mem used"));
                item.setTime(jsonObject.getString("mem datetime"));
                item.setMacadr(map.get("mac"));
                memoryinfoArrayList.add(item);


                /*
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
                 */
            }
            helper.insertMemoryInfo(memoryinfoArrayList);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
