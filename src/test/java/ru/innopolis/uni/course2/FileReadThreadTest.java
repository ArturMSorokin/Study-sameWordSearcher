package ru.innopolis.uni.course2;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import static org.junit.Assert.*;
/**
 * Created by olymp on 13.11.2016.
 */
public class FileReadThreadTest {
    private static Logger log = LoggerFactory.getLogger(FileReadThreadTest.class);
    private FileReadThread fileReadThread;
    private static String testString = "У лукоморья дуб зеленый;\n" +
            "Златая цепь на дубе том:\n" +
            "И днем и ночью кот ученый\n" +
            "Всё ходит по цепи кругом;\n" +
            "Идет направо — песнь заводит,\n" +
            "Налево — сказку говорит.";
    private static String target = "У лукоморья дуб зеленый; Златая цепь на дубе том: И днем и ночью кот ученый Всё ходит по цепи кругом; Идет направо — песнь заводит, Налево — сказку говорит. null ";

    private static String fileName = "file.txt";
    @BeforeClass
    public static void beforeTest(){
        log.info("This is @BeforeClass method");
        try (FileWriter fw = new FileWriter(fileName);
                         BufferedWriter bw = new BufferedWriter(fw);) {
            bw.write(testString);
        } catch (IOException e) {
            log.error(fileName+"  IOException");
        }
    }
    @Test
    public void Test() {
        StringBuilder buffer = new StringBuilder();
        Semaphore semaphore = new Semaphore();
        FileReadThread fileReadThread = new FileReadThread(buffer, fileName,semaphore);
        fileReadThread.start();
        try {
            fileReadThread.join();
            assertTrue("result is incorrect", buffer.toString().equals(target));
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
    @AfterClass
    public static void afterTest(){
        new File(fileName).delete();
        log.info("This is @AfterClass method");
    }
}
