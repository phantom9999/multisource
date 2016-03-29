package core.productor;

import core.productor.Pattern;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

public class LogWorkerThread extends Thread {
    public long beginPoint;
    public long endPoint;
    public File file;
    public Pattern pattern;



    private Sender sender;

    /**
     * 工作函数主体
     * 包括: 文件读取, 文件解析, 文件格式转换, 文件发送
     */
    public void run() {
        try {

            RandomAccessFile reader = new RandomAccessFile(file, "r");
            reader.seek(beginPoint);

            while (true) {
                if (reader.getFilePointer() > endPoint) {
                    break;
                }
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                // System.out.println(line);
                Map<String, String> data = pattern.transform(line);
                JSONObject jsonObject = JSONObject.fromObject(data);
                String strJson = jsonObject.toString();
                // 发送

                // System.out.println("发送");
                // System.out.println(strJson);
                sender.send(strJson);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // sender.close();
    }


    public long getBeginPoint() {
        return beginPoint;
    }

    public void setBeginPoint(long beginPoint) {
        this.beginPoint = beginPoint;
    }

    public long getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(long endPoint) {
        this.endPoint = endPoint;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

}
