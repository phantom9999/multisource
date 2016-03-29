package core.master;


import core.config.ConfigReader;

import java.util.Properties;

public abstract class WorkerBase {
    public Properties env;
    public Properties kafka;
    public Properties config;

    public WorkerBase() {
        env = readEnv();
        kafka = readConfig(env.getProperty("kafka.config"));
        config = readConfig(env.getProperty("worker.config"));
        String target = env.getProperty("target");
        config.put("key", env.getProperty("mac") + "==" + config.getProperty(target + ".name"));
    }

    public Properties readEnv() {
        Properties properties = new Properties();
        properties.put("mac", System.getenv("mac"));
        properties.put("target", System.getenv("target"));
        properties.put("kafka.config", System.getenv("kafka.config"));
        properties.put("worker.config", System.getenv("worker.config"));
        return properties;
    }

    public Properties readConfig(String strPath) {
        return new ConfigReader(strPath).getProperties();
    }

    public abstract void run() throws Exception;
}
