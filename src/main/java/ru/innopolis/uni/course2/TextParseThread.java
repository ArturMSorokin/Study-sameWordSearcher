package ru.innopolis.uni.course2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Created by olymp on 10.11.2016.
 */
public class TextParseThread extends  Thread {
    private static Logger logger = LoggerFactory.getLogger(TextParseThread.class);
    private StringBuilder buffer;
    private Map<String, Integer> report;

    public TextParseThread(StringBuilder buffer, ConcurrentHashMap<String, Integer> report) {
        this.buffer = buffer;
        this.report = report;
    }

    public void run() {
        try {
            buffer.wait();
        } catch (InterruptedException e) {
            logger.warn("buffer.wait() interrupted");
        }
    }

    private String getAvailableString() {
        synchronized (buffer) {
            return buffer.toString();
        }
    }
}
