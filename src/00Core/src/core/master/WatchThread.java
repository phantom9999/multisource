package core.master;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * @author phantom
 */
public class WatchThread extends Thread {


    private Process _process;



    private ProcessBuilder _processBuilder;
    private boolean _isPrint;

    public WatchThread(ProcessBuilder processBuilder, Process process, boolean isPrint) {
        _processBuilder = processBuilder;
        _process = process;
        _isPrint = isPrint;
    }

    public WatchThread(ProcessBuilder processBuilder, Process process) {
        this(processBuilder, process, true);
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                if (!_process.isAlive()) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(_process.getErrorStream()));

                    while (_isPrint) {
                        String line = br.readLine();
                        if (line == null) {
                            break;
                        } else {
                            System.out.println(line);
                        }
                    }
                    _process = _processBuilder.start();
                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(_process.getInputStream()));
                    while (_isPrint) {
                        String line = br.readLine();
                        if (line == null) {
                            break;
                        } else {
                            System.out.println(line);
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
