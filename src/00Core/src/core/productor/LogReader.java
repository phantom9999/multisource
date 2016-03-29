package core.productor;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LogReader {


    public long begin;
    public long end;
    private String _dirname;
    private String _filename;
    private String token;
    public File file;

    public LogReader(String dirname, String filename) {
        begin = -1;
        end = -1;
        token = null;
        _filename = filename;
        _dirname = dirname;
    }

    public void adjust4begin() throws IOException {
        // 文件位置修正
        RandomAccessFile tmpReader = new RandomAccessFile(file, "r");
        String newToken = tmpReader.readLine();
        end = tmpReader.length();
        if (newToken != null && newToken.equals(token)) {
            // 旧文件
            // 第一次读取的修正
            tmpReader.seek(begin);
            tmpReader.readLine();
            begin = tmpReader.getFilePointer();
        } else {
            // 新文件
            begin = 0;
            token = newToken;
        }
        tmpReader.close();
    }

    public void adjust() throws IOException {
        begin = end;
        RandomAccessFile tmpReader = new RandomAccessFile(file, "r");
        String newToken = tmpReader.readLine();
        if (newToken == null || !newToken.equals(token)) {
            // 不同文件
            begin = 0;
            token = newToken;
        }
        end = tmpReader.length();
        tmpReader.close();
    }

    public void checkFile() throws Exception {
        File dir = new File(_dirname);
        if (!dir.exists() && !dir.isDirectory()) {
            // 目录不存在
            throw new FileNotFoundException("目录不存在");
        }

        file = new File(_dirname + "/" + _filename);
        if (!file.exists() && !file.isFile()) {
            // 文件不存在
            throw new FileNotFoundException("文件不存在");
        }

        if (!file.canRead()) {
            // 文件不可读
            throw new Exception("文件不可读");
        }

        RandomAccessFile tmpReader = new RandomAccessFile(file, "r");
        token = tmpReader.readLine();
        begin = tmpReader.length();
        tmpReader.close();

    }
}
