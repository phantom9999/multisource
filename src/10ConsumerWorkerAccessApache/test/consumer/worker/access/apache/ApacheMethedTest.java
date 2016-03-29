package consumer.worker.access.apache;

import com.log.store.HBase;
import core.consumer.AccessItem;
import core.consumer.LogItem;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runners.model.TestClass;

import java.io.IOException;
import java.util.Properties;

/**
 * @author phantom
 */
public class ApacheMethedTest extends TestCase{
    @Test
    public void testStore() throws IOException {
        String[] lines = new String[4];
        lines[0] = "line 1";
        lines[1] = "line 2";
        lines[2] = "line 3";
        lines[3] = "line 4";
        String strMac = "as:as:as:as:as";
        String strType = "test";
        // new HBase().put(strMac, strType, strType, lines);
        // HBase.get(strMac);
    }

    @Test
    public void testTrans() {
        // %h %l %u %t %r %>s %b %{Referer}i %{User-Agent}i %T
        String testLine = "{" +
                "\"%h\":\"a\", " +
                "\"%l\":\"b\", " +
                "\"%u\":\"c\", " +
                "\"%t\":\"d\", " +
                "\"%r\":\"e\", " +
                "\"%>s\":\"f\", " +
                "\"%b\":\"g\", " +
                "\"%{Referer}i\":\"h\", " +
                "\"%{User-Agent}i\":\"i\", " +
                "\"%T\":\"j\"" +
                "}";
        System.out.println(testLine);
        Properties properties = new Properties();
        properties.put("consumer.format", "%h\t%l\t%u\t%t\t%r\t%>s\t%b\t%{Referer}i\t%{User-Agent}i\t%T");
        properties.put("%h", "remoteHost");
        properties.put("%t", "datetime");
        properties.put("%r", "url");
        properties.put("%>s", "status");
        properties.put("%b", "length");
        properties.put("%{Referer}i", "referer");
        properties.put("%{User-Agent}i", "userAgent");
        properties.put("%T", "timeTaken");
        ApacheMethed apacheMethed = new ApacheMethed(properties);

        /*AccessItem logItem = apacheMethed.transformValue(testLine);
        assertEquals("a", logItem.getRemoteHost());
        assertEquals("d", logItem.getDatetime());
        assertEquals("e", logItem.getUrl());
        assertEquals("f", logItem.getStatus());
        assertEquals("g", logItem.getLength());
        assertEquals("h", logItem.getReferer());
        assertEquals("i", logItem.getUserAgent());
        assertEquals("j", logItem.getTimeTaken());*/


    }
}