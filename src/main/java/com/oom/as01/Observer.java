package com.oom.as01;

public interface Observer {
	void update(String message);

    String getLastMessage();

    String getUserID();
}
