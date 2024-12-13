package com.oom.as01;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SocialApp implements Observable{
	
	private int userCounter = 0;

    private final List<Observer> observerList = new ArrayList<>();
    private final LinkedList<String> postsList = new LinkedList<>();

    @Override
    public void addObserver(Observer observer) {
    	this.observerList.add(observer);
        // Send all the posts that the user has missed
        for (String post : postsList) {
            observer.update(post);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
    	this.observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
    	String latestPost = this.postsList.peek();
        for (Observer observer : this.observerList) {
            observer.update(latestPost);
        }
    }
    
    public List<Observer> getObserverList() {
        return this.observerList;
    }

    public void addPost(String post) {
        this.postsList.addFirst(post);
        this.notifyObservers();
    }

    public int getAndIncrementUserCounter() {
        this.userCounter++;
        return this.userCounter;
    }
}
