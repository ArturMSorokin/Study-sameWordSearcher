package ru.innopolis.uni.course2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by olymp on 11.11.2016.
 */
public class HelloWorld {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    public int summ(int arg1, int arg2){
        return arg1+arg2+1;
    }

    public void doSome(int arg) throws Exception{
        if(arg == 5)
            throw new RuntimeException();
    }

}
