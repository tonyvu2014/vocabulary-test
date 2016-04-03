package com.wordcraft.utility;

public enum EventType {

	TEST("Vocabulary Test"), LEARN("Learning New Words"); 
	
	private String desc;
	
	private EventType(String desc) {
		this.desc = desc;
	}
}
