package core.config;

import junit.framework.TestCase;

import java.util.Properties;

import static org.junit.Assert.*;


public class ConfigReaderTest  extends TestCase{
    public void testReader() throws Exception {
        String strwp = System.getProperty("user.dir");
        ConfigReader reader = new ConfigReader(strwp + "/../../etc/producer.xml");
        Properties result = reader.getProperties();
        Properties except = new Properties();
        except.put("bootstrap.servers", "kafka:9092");
        except.put("acks", "all");
        except.put("retries", "0");
        except.put("batch.size", "16384");
        except.put("linger.ms", "1");
        except.put("buffer.memory", "33554432");
        except.put("key.serializer", "org.error.kafka.common.serialization.StringSerializer");
        except.put("value.serializer", "org.error.kafka.common.serialization.StringSerializer");

        assertEquals(result.getProperty("bootstrap.servers"), except.getProperty("bootstrap.servers"));
        assertEquals(result.getProperty("acks"), except.getProperty("acks"));
        assertEquals(result.getProperty("retries"), except.getProperty("retries"));
        assertEquals(result.getProperty("batch.size"), except.getProperty("batch.size"));
        assertEquals(result.getProperty("linger.ms"), except.getProperty("linger.ms"));
        assertEquals(result.getProperty("buffer.memory"), except.getProperty("buffer.memory"));
        assertEquals(result.getProperty("key.serializer"), except.getProperty("key.serializer"));
        assertEquals(result.getProperty("value.serializer"), except.getProperty("value.serializer"));

    }







}