package com.wordcraft.utility;

public enum EventType {

	TEST("Vocabulary Test"), LEARN("Learning New Words"), ADVANCE("Advance to a new level"); 
	
	private String desc;
	
	private EventType(String desc) {
		this.desc = desc;
	}
}
