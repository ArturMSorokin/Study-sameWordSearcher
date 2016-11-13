package ru.innopolis.uni.course2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.*;

/**
 * Created by olymp on 12.11.2016.
 */
public class FileReadThread extends Thread {

    private static Logger logger = LoggerFactory.getLogger(FileReadThread.class);
    private String fileName;
    private TextParseInterface parser;
    private  static int lineNumberRead=50;

    private static int getLineNumberRead() {
        return lineNumberRead;
    }

    /**
     * Creates text file reader, running in it's own thread, and syncronously sending results to buffer.
     * @param fileName name of file.
     */
    public FileReadThread(String fileName, TextParseInterface parser) {
        this.fileName = fileName;
        this.parser = parser;
    }

    private String getFileName() {
        return fileName;
    }

    /**
     * Makes work, shouldn't be executed straight, instread should be invoked start() method.
     */
    public void run() {
        read(getFileName());
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
            DataContainer dataContainer = new DataContainer();
            int linesReaded = getLineNumberRead();
            do {
                tmp = readLine(reader);
                dataContainer.getStringBuilder().append(tmp + ' ');
            } while (tmp != null && linesReaded-- > 0);
            dataContainer.setComplete(tmp == null);
            parser.parseBuffer(dataContainer);
        }while (tmp!=null);
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
