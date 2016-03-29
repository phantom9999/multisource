package consumer.worker.access.apache;


import com.log.store.HBase;
import core.consumer.AccessItem;
import core.consumer.LogItem;
import core.consumer.MethodIf;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ApacheMethed extends MethodIf {
    private String[] formatItems;

    public ApacheMethed(Properties var1) {
        super(var1);
        formatItems = properties.getProperty("consumer.format").split("\\\\t");
    }


    @Override
    public String transforms(String strJson) {
        JSONObject jsonObject = JSONObject.fromObject(strJson);
        StringBuilder stringBuilder = new StringBuilder();
        for (String item: formatItems) {
            if (jsonObject.has(item)) {
                stringBuilder.append(jsonObject.getString(item)).append("\t");
            } else {
                stringBuilder.append("-\t");
            }
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public AccessItem transformValues(String strJson) {
        AccessItem accessItem = new AccessItem();
        JSONObject jsonObject = JSONObject.fromObject(strJson);

        for (String item: formatItems) {
            if (jsonObject.has(item)) {
                try {
                    String fieldName = properties.getProperty(item, "-");
                    if (!fieldName.equals("-")) {
                        Field field = AccessItem.class.getField(fieldName);
                        field.set(accessItem, jsonObject.getString(item));
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return accessItem;
    }

    public Map<String, String> transformValue(String strJson) {
        if (strJson == null || strJson.equals("")) {
            System.out.println("空");
            return null;
        }
        try {
            Map<String, String> data = new HashMap<String, String>();
            JSONObject jsonObject = JSONObject.fromObject(strJson);

            for (String item: formatItems) {
                if (jsonObject.has(item)) {
                    String fieldName = properties.getProperty(item, "-");
                    data.put(fieldName, jsonObject.getString(item));
                }
            }
            return data;
        } catch (Exception e) {
            return null;
        }
    }






    @Override
    public boolean store(String key, String strJson) {

        //try {
            // String line = transforms(strJson);
            Map<String, String> map = transformsKey(key);
            String strMac = map.get("mac");
            String strType = map.get("name");
            String strSite = map.get("site");
            String[] lines = new String[1];
            Map<String, String> data = transformValue(strJson);
            if (data == null) {
                return false;
            } else {
                lines[0] = JSONObject.fromObject(data).toString();
                System.out.println(lines[0]);
                // System.out.println(formatItems.length);
                //new HBase().put(strMac, strType, strSite, lines);
            }
            // System.out.println("插入生成");
            return true;
        /*} catch (IOException e) {
            // e.printStackTrace();
            return false;
        }*/
    }
}
