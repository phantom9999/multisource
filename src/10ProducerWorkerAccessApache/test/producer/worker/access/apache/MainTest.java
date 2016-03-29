package producer.worker.access.apache;

import core.config.ConfigReader;
import org.junit.Test;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Created by phantom on 16-3-9.
 */
public class MainTest {

    @Test
    public void testMain() throws Exception {
        new WorkerProcess().run();
    }
}