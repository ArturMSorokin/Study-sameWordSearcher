package ru.innopolis.uni.course2;

/**
 * Created by olymp on 13.11.2016.
 */
public class DataContainer {
    StringBuilder stringBuilder = new StringBuilder();
    boolean complete=false;

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
