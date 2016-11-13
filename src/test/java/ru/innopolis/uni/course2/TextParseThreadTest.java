package ru.innopolis.uni.course2;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertTrue;

/**
 * Created by olymp on 13.11.2016.
 */

public class TextParseThreadTest {
    private static Logger log = LoggerFactory.getLogger(TextParseThreadTest.class);

    private  StringBuilder buffer ;
    private  ConcurrentHashMap<String,Integer> report ;
    private  ConcurrentHashMap<String,Integer> reportEtalon ;
    private  TextParseThread textParseThread ;
    private  String testString = "один! два  три 5 два; .пять";
    @BeforeClass
    public static void beforeTest(){
        log.info("This is @BeforeClass method");

    }

    @Before
    public  void before() {
        buffer = new StringBuilder();
        report = new ConcurrentHashMap<>();
        reportEtalon = new ConcurrentHashMap<>();
        buffer.append(testString);
        textParseThread = new TextParseThread(report);
        reportEtalon.put("один",1)  ;
        reportEtalon.put("два",2)  ;
        reportEtalon.put("три",1)  ;
        reportEtalon.put("пять",1)  ;
    }
    @Test
    public  void Test() {
        textParseThread.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (buffer) {
            buffer.notify();
        }
        try {
            if (textParseThread.isAlive())
                textParseThread.join();
            log.info("report:"+report.toString());
            log.info("etalon:"+reportEtalon.toString());
            assertTrue("result is incorrect", report.equals(reportEtalon));
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
    @AfterClass
    public static void afterTest(){
        log.info("This is @AfterClass method");
    }
}