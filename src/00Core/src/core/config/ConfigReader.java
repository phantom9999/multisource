package core.config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Properties;

/**************************************************
 * 日志读取功能
 *
 *
 */
public class ConfigReader {
    private String filename;
    private Properties properties;


    public ConfigReader(String var1) {
        this.filename = var1;
        this.properties = null;
    }


    protected boolean configReader() throws Exception {
        this.properties = new Properties();
        File configFile = new File(this.filename);
        if (configFile.isFile() && configFile.canRead()) {
            // 读取配置文件
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(configFile);

            Element root = doc.getDocumentElement();

            if (root == null) {
                // 顶级元素为空
                String msg = "顶级元素为空";
                System.out.println(msg);
                throw new Exception(msg);
            } else {
                NodeList items = root.getChildNodes();
                if (items == null) {
                    // 配置选项为空
                    String msg = "配置选项为空";
                    System.out.println(msg);
                    throw new Exception(msg);
                } else {
                    for (int i=0;i<items.getLength();++i) {
                        Node item = items.item(i);
                        if (item != null && item.getNodeType() == Node.ELEMENT_NODE) {
                            NodeList values = item.getChildNodes();
                            if (values.getLength() < 2) {
                                // 配置信息个数不对
                                String msg = "配置信息个数部队";
                                System.out.println(msg);
                                throw new Exception(msg);
                            }

                            Node name = null;
                            Node value = null;
                            for (int j=0;j<values.getLength();++j) {
                                Node tmp = values.item(j);
                                if (tmp.getNodeName().equals("name")) {
                                    name = tmp;
                                } else if (tmp.getNodeName().equals("value")) {
                                    value = tmp;
                                }
                            }
                            if (name != null && name.getNodeType() == Node.ELEMENT_NODE && name.getChildNodes() != null
                                    && value != null && value.getNodeType() == Node.ELEMENT_NODE && value.getChildNodes() != null) {
                                // 格式正确
                                this.properties.put(name.getFirstChild().getNodeValue(), value.getFirstChild().getNodeValue());
                            } else {
                                // 格式不对
                                String msg = "格式不对";
                                System.out.println(msg);
                                throw new Exception("error in config reading");
                            }
                        }
                    }
                    return true;
                }
            }
        } else {
            String msg = "no such file " + this.filename;
            System.out.println(msg);
            throw new Exception(msg);
        }
    }


    public Properties getProperties() {
        if (this.properties == null) {
            // 读取配置文件
            try {
                this.configReader();
            } catch (Exception e) {
                //e.printStackTrace();
                return null;
            }

        }
        return this.properties;

    }









}
