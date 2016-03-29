package consumer.worker.error.apache;

import core.consumer.ErrorItem;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import junit.framework.TestCase;
import net.sf.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * @author phantom
 */
public class ApacheMethodTest extends TestCase {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testTransforms() throws Exception {
        String testLine = "[Thu Feb 25 11:22:27.103636 2016] [autoindex:error] [pid:3822] AH00170: caught SIGWI";
        /*Pattern pattern = Pattern.compile("\\]");

        // Matcher matcher = pattern.matcher(testLine);
        // System.out.println(matcher.group());
        for (String str:pattern.split(testLine)) {
            System.out.println(str);
        }

        ErrorItem item = new ErrorItem();
        System.out.println(JSONObject.fromObject(item).toString());*/
        ApacheMethod apacheMethod = new ApacheMethod(null);
        String str = apacheMethod.transforms("{\"msg\":\"" + testLine + "\"}");
        System.out.println(str);

    }
}