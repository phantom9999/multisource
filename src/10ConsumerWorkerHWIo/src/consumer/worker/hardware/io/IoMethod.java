package consumer.worker.hardware.io;

import Entity.ioinfo;
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
public class IoMethod extends MethodIf {
    private inserthardwareinfoimple helper;
    public IoMethod(Properties var1) {
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
            Map<String, String> map = transformsKey(key);
            JSONArray jsonArray = JSONArray.fromObject(strJson);
            ArrayList<ioinfo> ioinfoArrayList = new ArrayList<ioinfo>();

            for (int i=0;i<jsonArray.size();++i) {
                ioinfo item = new ioinfo();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                item.setFilesystem(jsonObject.getString("Filesystem"));
                item.setMountedon(jsonObject.getString("Mounted on"));
                item.setReads(jsonObject.getString("Reads"));
                item.setWrites(jsonObject.getString("Writes"));
                item.setRbytes(jsonObject.getString("R-bytes"));
                item.setWbytes(jsonObject.getString("W-bytes"));
                item.setQueue(jsonObject.getString("Queue"));
                item.setSvctm(jsonObject.getString("Svctm"));
                item.setTime(jsonObject.getString("datetime"));
                item.setMacadr(map.get("mac"));
                ioinfoArrayList.add(item);
            }
            helper.insertIoInfo(ioinfoArrayList);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
