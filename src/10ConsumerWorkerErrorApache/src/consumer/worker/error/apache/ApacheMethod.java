package consumer.worker.error.apache;

import com.log.store.HBase;
import core.consumer.ErrorItem;
import core.consumer.MethodIf;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * @author phantom
 */
public class ApacheMethod extends MethodIf {
    private String[] formatItems;

    public ApacheMethod(Properties var1) {
        super(var1);
        formatItems = properties.getProperty("consumer.format").split("\\t");
    }



    public String transforms(String strJson) {
        JSONObject jsonObject = JSONObject.fromObject(strJson);
        if (formatItems.length > 0) {
            String key = formatItems[0];
            if (jsonObject.has(key)) {
                String line = jsonObject.getString(key).replace("[", "");
                Pattern pattern = Pattern.compile("\\]");
                String[] fields = pattern.split(line);
                if (fields.length > 3) {
                    // 合格
                    Map<String, String> data = new HashMap<String, String>();

                    String datetime = fields[0];
                    data.put("Time", datetime);
                    String line2 = fields[1];
                    String[] line2Fields = line2.split(":");
                    if (line2Fields.length == 2) {
                        // 合格
                        data.put("Mod", line2Fields[0]);
                        data.put("Rank", line2Fields[1]);

                        String line3 = fields[2];
                        String[] line3Fields = line3.split(":");
                        if (line3Fields.length == 2) {
                            data.put("Pid", line3Fields[1]);
                            String line4 = fields[3];
                            data.put("Type", "-");
                            data.put("Message", line4);
                            return JSONObject.fromObject(data).toString();
                        } else {
                            return "";
                        }
                    } else {
                        // 不合格
                        return "";
                    }

                } else {
                    return "";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }



    @Override
    public boolean store(String key, String strJson) {
        try {
            String line = transforms(strJson);
            if (line.equals("")) {
                return false;
            }
            Map<String, String> map = transformsKey(key);
            String strMac = map.get("mac");
            String strType = map.get("name");
            String strSite = map.get("site");
            String[] lines = new String[1];
            lines[0] = line;
            new HBase().put(strMac, strType, strSite, lines);
            // System.out.println("插入生成");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
