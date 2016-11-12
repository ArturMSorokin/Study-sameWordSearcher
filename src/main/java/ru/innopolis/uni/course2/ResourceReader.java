package ru.innopolis.uni.course2;

import java.util.Map;

/**
 * Created by olymp on 12.11.2016.
 */
public class ResourceReader {
    private FileReadThread fileReadThread;
    private TextParseThread textParseThread;
    private boolean complete = false;
    public ResourceReader(String resource, Map<String,Integer> report) {

    }

    public boolean isComplete() {
        return !(fileReadThread.isAlive() && textParseThread.isAlive());
    }
}
