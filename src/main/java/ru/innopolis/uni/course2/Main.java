package ru.innopolis.uni.course2;

/**
 * Created by olymp on 09.11.2016.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    static public void main(String[] ar) {
        ConcurrentHashMap<String,Integer> report = new ConcurrentHashMap<>();
        report.put("test",1);
        logger.info(report.hashCode()+report.toString());
        List<ResourceReader> resourceReaders = new LinkedList<>();
        for (String s : ar) {
            logger.info(s);
            ResourceReader resourceReader = new ResourceReader(s,report);
            resourceReader.start();
            resourceReaders.add(resourceReader);
        }
        while (!resourceReaders.isEmpty()) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Iterator<ResourceReader> iter = resourceReaders.iterator();
            while(iter.hasNext()) {
                if (iter.next().isComplete()) {
                    iter.remove();
                }
            }
        }
        logger.info(report.hashCode()+report.toString());
    }
}
