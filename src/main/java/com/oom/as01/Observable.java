package com.oom.as01;

public interface Observable {
	void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}
