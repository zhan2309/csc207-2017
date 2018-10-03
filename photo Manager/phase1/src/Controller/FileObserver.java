package Controller;

import java.util.Observable;

/**
 * The FileObserver is created for the future extensions like adding different functionality Observers.
 */
public abstract class FileObserver implements java.util.Observer {
    DataBaseUpdater dataBaseUpdater;

    /**
     * The Constructor of the FileObserver.
     */
    FileObserver() {
        dataBaseUpdater = new DataBaseUpdater();
    }


    /**
     * The abstract update method for each individual Observer.
     *
     * @param o   Observable The Observable that we want to observe
     * @param arg Object The argument collected by the observer
     */
    @Override
    public abstract void update(Observable o, Object arg);
}
