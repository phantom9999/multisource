package core.consumer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class MethodIf {
    protected Properties properties;

    public MethodIf(Properties var1) {
        properties = var1;
    }
    public abstract String transforms(String strJson);
    public abstract boolean store(String key, String strJson);

    public Map<String, String> transformsKey(String strKey) {
        Map<String, String> data = new HashMap<String, String>();
        String[] keys = strKey.split("==");
        if (keys.length != 3) {
            // 无效键
            data.put("mac", "::1");
            data.put("name", "null");
            data.put("site", "null");
        } else {
            data.put("mac", keys[0]);
            data.put("name", keys[1]);
            data.put("site", keys[2]);
        }

        return data;
    }
}
