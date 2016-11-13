package ru.innopolis.uni.course2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.*;

/**
 * Created by olymp on 10.11.2016.
 *
 */

/**
 * Class waits buffer setting by method parseBuffer, then it parses it and saves results to report,
 * or setting complete flag,  in this case it finises.
 */
public class TextParseThread extends  Thread implements TextParseInterface {
    private static Logger logger = LoggerFactory.getLogger(TextParseThread.class);
    private Queue<DataContainer> buffer=new ConcurrentLinkedQueue<>();
    private Map<String, Integer> report;

    /**
     * Creates TextParseThread
     * @param report
     */
    public TextParseThread( Map<String, Integer> report) {
        this.report = report;
        logger.info(report.hashCode()+report.toString());
    }
    /**
     * Makes work, shouldn't be executed straight, instread should be invoked start() method.
     */
    public void run() {
        while (!interrupted()) {
                if (buffer.isEmpty()) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        logger.warn("buffer.wait() interrupted");
                    }
                } else {
                    DataContainer dataContainer = buffer.poll();
                    updateReport(getAvailableString(dataContainer));
                    if (dataContainer.isComplete())
                        return;
                }
        }
    }


    public void parseBuffer(DataContainer buffer) {
        this.buffer.add(buffer);
    }

    /**
     * Takes string from buffer, which should be filled by FileReadThread.
     * @return
     */
    private String getAvailableString(DataContainer dataContainer) {
            return dataContainer.getStringBuilder().toString();
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
        logger.info(report.hashCode()+report.toString());
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
