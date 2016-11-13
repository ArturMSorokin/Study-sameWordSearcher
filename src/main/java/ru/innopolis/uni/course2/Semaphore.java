package ru.innopolis.uni.course2;

/**
 * Created by olymp on 13.11.2016.
 */
public class Semaphore {
    private boolean resourceReady=false;

    public void setResourceReady(boolean resourceReady) {
        this.resourceReady = resourceReady;
    }

    public boolean isResourceReady() {

        return resourceReady;
    }
}
