package consumer.worker.hardware.ifnet;

import Entity.networkcardinfo;
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
public class IfMethod extends MethodIf {
    private inserthardwareinfoimple helper;

    public IfMethod(Properties var1) {
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
            ArrayList<networkcardinfo> networkcardinfoArrayList = new ArrayList<networkcardinfo>();
            JSONArray jsonArray = JSONArray.fromObject(strJson);
            for (int i=0;i<jsonArray.size();++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                networkcardinfo item = new networkcardinfo();
                item.setIfname(jsonObject.getString("ifname"));
                item.setLinkencap(jsonObject.getString("Link encap"));
                item.setHwaddr(jsonObject.getString("HWaddr"));
                item.setInetaddr(jsonObject.getString("inet addr"));
                item.setPtp(jsonObject.getString("P-t-P"));
                item.setBcast(jsonObject.getString("Bcast"));
                item.setFlag(jsonObject.getString("flag"));
                item.setMtu(jsonObject.getString("MTU"));
                item.setMetric(jsonObject.getString("Metric"));
                item.setRxpackets(jsonObject.getString("RX packets"));
                item.setRxerrors(jsonObject.getString("RX errors"));
                item.setRxdropped(jsonObject.getString("RX dropped"));
                item.setRxoverruns(jsonObject.getString("RX overruns"));
                item.setRxframe(jsonObject.getString("RX frame"));
                item.setTxpackets(jsonObject.getString("TX packets"));
                item.setTxerrors(jsonObject.getString("TX errors"));
                item.setTxdropped(jsonObject.getString("TX dropped"));
                item.setTxoverruns(jsonObject.getString("TX overruns"));
                item.setTxcarrier(jsonObject.getString("TX carrier"));
                item.setCollections(jsonObject.getString("collisions"));
                item.setRxbytes(jsonObject.getString("RX bytes"));
                item.setTxbytes(jsonObject.getString("TX bytes"));
                item.setTime(jsonObject.getString("datetime"));
                item.setMacadr(map.get("mac"));
                networkcardinfoArrayList.add(item);
            }
            helper.insertNetworkCardInfo(networkcardinfoArrayList);
            return true;
        } catch (Exception e) {
            return true;
        }

    }
}
