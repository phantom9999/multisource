package producer.worker.access.apache;


import core.productor.Pattern;

import java.util.HashMap;
import java.util.Map;

/*******************************************************
 * access文件解析部分
 * 初始化时将匹配模式传入.
 * 通过调用treansform将日志转换成map类型, 并进行返回
 *******************************************************/
public class AccessPattern implements Pattern {

    protected String[] keys;


    /**
     * 构造函数, 参数为需要匹配的模式
     * @param var1
     */
    public AccessPattern(String var1) {
        keys = var1.split("\\\\t");
    }

    /**
     * 转换函数
     * @param var1 一行日志
     * @return map类型的数据
     * @throws Exception
     */
    public synchronized Map<String, String> transform(String var1) {
        String[] values = var1.split("\\t");
        if (values.length != keys.length) {
            // 长度不同
            System.out.println(keys.length);
            System.out.println(values.length);

            String msg = "日志模型与实际不符";
            // throw new Exception(msg);
            return null;
        } else {
            Map<String, String> data = new HashMap<String, String>();
            for (int i=0;i<keys.length;++i) {
                data.put(keys[i], values[i]);
            }
            return data;
        }
    }
}
