package ru.innopolis.uni.course2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by olymp on 12.11.2016.
 */
public class FileReadThread extends Thread {

    private static Logger logger = LoggerFactory.getLogger(FileReadThread.class);
    private StringBuilder buffer;
    private String fileName;
    private Semaphore semaphore;
    /**
     * Creates text file reader, running in it's own thread, and syncronously sending results to buffer.
     * @param buffer buffer.
     * @param fileName name of file.
     * @param semaphore
     */
    public FileReadThread(StringBuilder buffer, String fileName, Semaphore semaphore) {
        this.buffer = buffer;
        this.fileName = fileName;
        this.semaphore = semaphore;
    }




    private String getFileName() {
        return fileName;
    }

    /**
     * Makes work, shouldn't be executed straight, instread should be invoked start() method.
     */
    public void run() {
        read(getFileName());
        synchronized (buffer) {
            buffer.notify();
        }
        semaphore.setResourceReady(true);
    }

    /**
     * Adds syncronously readed strings to buffer.
     * @param string string ot add.
     */
    private void addStrings(String string) {
        synchronized (buffer) {
            buffer.append (string);
        }
    }

    /**
     * Tries to read string from stream.
     */
    private String readLine(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            logger.info("File "+this.getFileName()+" reached end");
        }
        return null;
    }
    /**
     * Reads set of lines and syncronously stores in buffer.
     */
    private void readLines(BufferedReader reader) {
        String tmp;
        do {
            tmp = readLine(reader);
            addStrings(tmp+' ');
        } while (tmp!=null);
    }

    /**
     * Reads file.
     * @param file
     */
    private void read(String file) {
        try (Reader fileReader = new FileReader(file);
                BufferedReader reader = new LineNumberReader(fileReader)) {
            readLines(reader);
        } catch (FileNotFoundException e) {
            logger.error("File "+file+" not found");
        } catch (IOException e) {
            logger.error("File "+file+" IOException "+e.getMessage());
        }
    }
}
