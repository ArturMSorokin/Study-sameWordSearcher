package ru.innopolis.uni.course2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.*;

/**
 * Created by olymp on 10.11.2016.
 */
public class TextParseThread extends  Thread {
    private static Logger logger = LoggerFactory.getLogger(TextParseThread.class);
    private StringBuilder buffer;
    private Map<String, Integer> report;
    Semaphore semaphore;

    /**
     * Creates TextParseThread
     * @param buffer
     * @param report
     * @param semaphore - before parse semaphore should be set to 'true'.
     */
    public TextParseThread(StringBuilder buffer, Map<String, Integer> report, Semaphore semaphore) {
        this.buffer = buffer;
        this.report = report;
        this.semaphore = semaphore;
        logger.info(report.hashCode()+report.toString());

    }
    /**
     * Makes work, shouldn't be executed straight, instread should be invoked start() method.
     */
    public void run() {
        try {
            while (!semaphore.isResourceReady())
                Thread.sleep(5);
            String availableString = getAvailableString();
            updateReport(availableString);
        } catch (InterruptedException e) {
            logger.warn("buffer.wait() interrupted");
        }
    }

    /**
     * Takes string from buffer, which should be filled by FileReadThread.
     * @return
     */
    private String getAvailableString() {
        synchronized (buffer) {
            return buffer.toString();
        }
    }

    /**
     * Counts same word's occurance, saves result to map.
     * @param string with words, that should be counted.
     */
    private void updateReport(String string) {
        String[] words = string.split("[^\\p{IsCyrillic}]");

        ArrayList<String> cyrillicWords = null;
        cyrillicWords = getCyrillicWords(words);
        for (String s : cyrillicWords) {
            if (report.containsKey(s))
                report.put(s,report.get(s)+1);
            else
                report.put(s,1);
        }
//        logger.info(report.hashCode()+report.toString());
    }

    /**
     * Takes cyrillic words from 'words' and stores trem to List, and returns it.
     * @param words
     * @return
     */
    private ArrayList<String> getCyrillicWords(String[] words) {
        Pattern pattern = Pattern.compile("[\\p{IsCyrillic}]+");
        ArrayList<String> cyrillicWords = new ArrayList<String>();
        for (String s : words) {
            Matcher matcher = pattern.matcher(s);
            if (matcher.matches())
                cyrillicWords.add(s);
        }
        return cyrillicWords;
    }
}
