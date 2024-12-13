package com.oom.as01;

import java.util.Stack;

public class CreateUser implements Observer {
	
	private final Stack<String> postsLocal;
    private final String userID;
    
    public CreateUser(String userID) {
        this.userID = userID;
        this.postsLocal = new Stack<>();
    }

    @Override
    public void update(String message) {
    	this.postsLocal.add(message);
    }

    public Stack<String> getAllMessagesInReverseOrder() {
        return this.postsLocal;
    }
    
    public String getLastMessage() {
        return this.postsLocal.peek();
    }

    public String getUserID() {
        return userID;
    }

    public void clearLocalPosts() {
        this.postsLocal.clear();
    }

}
