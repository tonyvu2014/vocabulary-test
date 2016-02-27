package com.wordcraft.utility;

public enum EventType {

	TEST("Vocabulary Test"), LEARN("Learning New Words"),  ACQUIRE("Word Acquisition"); 
	
	private String desc;
	
	private EventType(String desc) {
		this.desc = desc;
	}
}
