package com.wordcraft.utility

class Utils {
	
	public static final String ALPHANUMERIC = (('A'..'Z') + ('a'..'z') + (0..9)).join()

	public static String generateToken(int length) {
	    
		def generator = { String alphabet, int n ->
			new Random().with {
			  (1..n).collect { alphabet[ nextInt( alphabet.length() ) ] }.join()
			}
		 }
			
		 return generator(ALPHANUMERIC, length)
	}
	
}
