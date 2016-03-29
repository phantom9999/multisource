package consumer.worker.error.nginx;

import com.log.store.HBase;
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
                String line = jsonObject.getString(key);
                Map<String, String> data = new HashMap<String, String>();
                String[] line1Fields = line.split("\\[error\\]");
                data.put("Rank", "error");
                if (line1Fields.length == 2) {
                    data.put("Time", line1Fields[0]);
                    String[] line2Fields = line1Fields[1].split("client:");
                    if (line2Fields.length == 2) {
                        data.put("Message", line2Fields[0]);
                        data.put("Mod", "-");
                        data.put("Pid", "-");
                        data.put("Type", "-");
                        return JSONObject.fromObject(data).toString();
                    } else {
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
