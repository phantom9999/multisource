package core.master;


import core.config.ConfigReader;
import org.apache.commons.io.input.ReaderInputStream;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class MasterBase {


    protected String target;
    protected Properties config;


    public MasterBase(String strTarget) {
        target = strTarget;
        // 读取环境变量
        init();
        // 读取配置文件
    }

    public Properties readEnv() {
        Properties properties = new Properties();
        properties.put("classpath", System.getProperty("java.class.path"));
        properties.put("workspace", System.getProperty("user.dir") + "/");
        properties.put("core.config.dir", properties.getProperty("workspace") + "etc/");
        // properties.put("method.config.dir", properties.getProperty("core.config.dir") + "group/");
        properties.put("kafka.config", properties.getProperty("core.config.dir") + target + ".xml");
        properties.put("worker.config.dir", properties.getProperty("core.config.dir") + target + "/");
        return properties;
    }

    public Properties readProp(String path) {
        try {
            return new ConfigReader(path).getProperties();
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public boolean checkClass(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public boolean checkClassList(ArrayList<String> classList) {
        for (Iterator iterator = classList.iterator(); iterator.hasNext(); ) {
            String className = (String) iterator.next();
            if (!checkClass(className)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkFileList(ArrayList<String> pathList) {
        for (String strPath : pathList) {
            if (!checkFile(strPath)) {
                System.out.println(strPath + "不存在");
                return false;
            }
        }
        return true;
    }

    private boolean checkFile(String strPath) {
        File item = new File(strPath);
        if (item.exists() && item.canRead()) {
            return true;
        }
        return false;
    }

    public boolean checkWorkerProp(String strDir, String strTarget) {
        // 读取
        ArrayList<String> configList = getWorkerConfigList(strDir);
        ArrayList<Properties> propertiesArrayList = new ArrayList<Properties>();
        for (String configFile : configList) {
            Properties tmp = readProp(configFile);
            if (tmp != null) {
                propertiesArrayList.add(tmp);
            }
        }
        // 判断是否存在问题
        if (configList.size() != propertiesArrayList.size()) {
            System.out.println("配置文件数与实例数量不符");
            return false;
        }

        // 判断配置中的类是否存在
        ArrayList<String> classList = new ArrayList<String>();
        for (Properties propertiesItem : propertiesArrayList) {
            String className = propertiesItem.getProperty(strTarget + ".class");
            classList.add(className);
        }

        if (!checkClassList(classList)) {
            System.out.println("存在类不存在");
            return false;
        }

        // 判断目录文件是否存在
        ArrayList<String> fileList = new ArrayList<String>();
        for (Properties propertiesItem : propertiesArrayList) {
            if (propertiesItem.containsKey(strTarget + ".dir")) {
                String strPath = propertiesItem.getProperty(strTarget + ".dir");
                String strFile = strPath + propertiesItem.getProperty(strTarget + ".filename");
                fileList.add(strPath);
                fileList.add(strFile);
            }
        }

        if (!checkFileList(fileList)) {
            System.out.println("存在文件不存在");
            return false;
        }

        return true;
    }

    public ArrayList<String> getWorkerConfigList(String strPath) {
        File dir = new File(strPath);
        ArrayList<String> stringArrayList = new ArrayList<String>();
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".xml")) {
                    stringArrayList.add(file.getAbsolutePath());
                }
            }
        }
        return stringArrayList;
    }

    public boolean checkKafkaConfig(String strPath) {
        if (!checkFile(strPath)) {
            return false;
        }

        Properties properties = readProp(strPath);
        if (properties == null) {
            return false;
        }

        if (!properties.containsKey("bootstrap.servers")) {
            return false;
        }

        if (target.equals("producer")) {
            if (! properties.containsKey("key.serializer") || !checkClass(properties.getProperty("key.serializer"))) {
                return false;
            }
            if (! properties.containsKey("value.serializer") || !checkClass(properties.getProperty("value.serializer"))) {
                return false;
            }
        } else if (target.equals("consumer")) {
            if (! properties.containsKey("key.deserializer") || !checkClass(properties.getProperty("key.deserializer"))) {
                return false;
            }
            if (! properties.containsKey("value.deserializer") || !checkClass(properties.getProperty("value.deserializer"))) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    public boolean checkCoreEnv() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(config.getProperty("workspace"));
        arrayList.add(config.getProperty("core.config.dir"));
        // arrayList.add(config.getProperty("group.config.dir"));
        arrayList.add(config.getProperty("kafka.config"));
        arrayList.add(config.getProperty("worker.config.dir"));
        if (!checkFileList(arrayList)) {
            return false;
        } else {
            return true;
        }
    }

    public String getMac() {
        String mac = null;
        try {
            mac = new Sigar().getNetInterfaceConfig(null).getHwaddr();
        } catch (SigarException e) {
            // e.printStackTrace();
            mac = "::1";
        }
        return mac;
    }

    public boolean check() {
        // 检查核心变量
        if (!checkCoreEnv()) {
            System.out.println("核心变量错误");
            return false;
        }
        // 检测worker配置
        if (!checkWorkerProp(config.getProperty("worker.config.dir"), target)) {
            System.out.println("worker配置问题");
            return false;
        }
        // 检测kafka配置
        if (!checkKafkaConfig(config.getProperty("kafka.config"))) {
            System.out.println("kafka问题");
            return false;
        }
        return true;
    }

    public void init() {
        // 读取环境
        config = readEnv();
        // 获得mac地址
        config.put("mac", getMac());
    }

    public void run() {
        // 获得需要的配置文件
        ArrayList<String> fileList = getWorkerConfigList(config.getProperty("worker.config.dir"));
        // 启动进程
        ArrayList<Process> processArrayList = new ArrayList<Process>();
        ArrayList<ProcessBuilder> processBuilderArrayList = new ArrayList<ProcessBuilder>();
        try {
            for (Iterator iterator = fileList.iterator(); iterator.hasNext(); ) {

                String file = (String) iterator.next();
                Properties item = readProp(file);

                System.out.println("启动");
                System.out.println(item.getProperty(target + ".class"));

                ProcessBuilder processBuilder = new ProcessBuilder(
                        "java",
                        "-classpath",
                        config.getProperty("classpath"),
                        item.getProperty(target + ".class")
                );
                processBuilder.directory(new File(config.getProperty("workspace")));
                Map<String, String> env = processBuilder.environment();
                env.put("kafka.config", config.getProperty("kafka.config"));
                env.put("worker.config", file);
                env.put("mac", config.getProperty("mac"));
                env.put("target", target);
                // env.put("worker.name", item.getProperty(target + ".name"));
                processBuilderArrayList.add(processBuilder);
                processArrayList.add(processBuilder.start());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Todo:心跳检测

        ArrayList<WatchThread> watchThreads = new ArrayList<WatchThread>();
        for (int i=0;i<processArrayList.size();++i) {

            Process process = processArrayList.get(i);
            ProcessBuilder processBuilder = processBuilderArrayList.get(i);
            WatchThread watchThread = new WatchThread(processBuilder, process);
            watchThread.start();
            watchThreads.add(watchThread);
        }


        try {
            for (Iterator iterator = watchThreads.iterator(); iterator.hasNext(); ) {
                WatchThread watchThread = (WatchThread) iterator.next();
                watchThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}



