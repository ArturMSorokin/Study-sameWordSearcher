package ru.innopolis.uni.course2;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
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
            synchronized (buffer) {
                buffer.wait();
            }
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
//        String[] words = string.split("[\\s,.:%$#!?]");
        String[] words = string.split("[^\\p{IsCyrillic}]");

        ArrayList<String> cyrillicWords = null;
        cyrillicWords = getCyrillicWords(words);
        for (String s : cyrillicWords) {
            if (report.containsKey(s))
                report.put(s,report.get(s)+1);
            else
                report.put(s,1);
        }
        report.size();
    }

    private ArrayList<String> getCyrillicWords(String[] words) {
        Pattern pattern = Pattern.compile("[\\p{IsCyrillic}]+");
        String str="ыва";
        String str2="sdf";
        if (pattern.matcher(str).matches()) {
            int k = 0;
        }

        ArrayList<String> cyrillicWords = new ArrayList<String>();
        for (String s : words) {
            Matcher matcher = pattern.matcher(s);
            if (matcher.matches())
                cyrillicWords.add(s);
        }
        return cyrillicWords;
    }
}
