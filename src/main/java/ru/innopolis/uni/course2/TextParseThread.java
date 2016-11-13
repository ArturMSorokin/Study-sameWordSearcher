package ru.innopolis.uni.course2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.regex.*;

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
            String availableString = getAvailableString();
            updateReport(availableString);
        } catch (InterruptedException e) {
            logger.warn("buffer.wait() interrupted");
        }
    }

    private String getAvailableString() {
        synchronized (buffer) {
            return buffer.toString();
        }
    }

    private void updateReport(String string) {
        String[] words = string.split("[ ]");//??
        ArrayList<String> cyrillicWords = getCyrillicWords(words);
        for (String s : cyrillicWords) {
            if (report.containsKey(s))
                report.put(s,report.get(s)+1);
            else
                report.put(s,1);
        }
    }

    private ArrayList<String> getCyrillicWords(String[] words) {
        Pattern pattern = Pattern.compile("[а-яА-Я]");
        ArrayList<String> cyrillicWords = new ArrayList<String>();
        for (String s : words) {
            Matcher matcher = pattern.matcher(s);
            if (matcher.matches())
                cyrillicWords.add(s);
        }
        return cyrillicWords;
    }
}
