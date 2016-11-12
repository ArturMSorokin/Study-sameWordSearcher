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
    private int lineNumber=10;
    private LineNumberReader reader;
    private String fileName;

    /**
     * Creates text file reader, running in it's own thread, and syncronously sending results to buffer.
     * @param buffer buffer.
     * @param fileName name of file.
     */
    public FileReadThread(StringBuilder buffer, String fileName) {
        this.buffer = buffer;
        this.fileName = fileName;
        buffer = new StringBuilder();
    }

    private String getFileName() {
        return fileName;
    }

    /**
     * Makes work, shouldn't be executed straight, instread should be invoked start() method.
     */
    public void run() {
        if (initReader(getFileName())) {
            while (!interrupted() && readLines()) { }
        }
    }

    private int getLineNumber() {
        return lineNumber;
    }

    /**
     * Reads set of lines and syncronously stores in buffer.
     * @return true if file still has data, else otherwise.
     */
    boolean readLines() {
        int i=lineNumber;
        String tmp;
        do {
            tmp = readLine();
            addStrings(tmp);
        } while (i-->0 && tmp!=null);
        return !(tmp==null);
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
     * @return readed string or null if stream is empty.
     */
    private String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            logger.info("File "+this.getFileName()+" reached end");
        }
        return null;
    }

    private boolean initReader(String file) {
        Reader fileReader = null;
        try {
            fileReader = new FileReader(file);
            reader = new LineNumberReader(fileReader);
            return true;
        } catch (FileNotFoundException e) {
            logger.error("File "+file+" not found");
        }
        return false;
    }
}
