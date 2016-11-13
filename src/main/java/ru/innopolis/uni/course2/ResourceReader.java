package ru.innopolis.uni.course2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by olymp on 12.11.2016.
 */
public class ResourceReader {
    private static Logger logger = LoggerFactory.getLogger(ResourceReader.class);
    private FileReadThread fileReadThread;
    private TextParseInterface textParseThread;
    public ResourceReader(String resource, ConcurrentHashMap<String, Integer> report) {
        textParseThread = new TextParseThread(report);
        fileReadThread = new FileReadThread(resource,textParseThread);
        logger.info(report.hashCode()+report.toString());
    }

    public void start() {
        fileReadThread.start();
        textParseThread.start();

    }

    public boolean isComplete() {
        return !(fileReadThread.isAlive() || textParseThread.isAlive());
    }
}
