package ru.innopolis.uni.course2;

/**
 * Created by olymp on 09.11.2016.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    static public void main(String[] ar) {


        System.out.println("Hello world");
        logger.error("someError");
    }
}
