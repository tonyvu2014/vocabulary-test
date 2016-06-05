package com.wordcraft.utility

import java.security.MessageDigest

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
	
	
	public static String generateUUID() {
		return UUID.randomUUID().toString()	
	}
	
	public static String encryptData(String data) {
		def sha256Hash = { text ->
			java.security.MessageDigest.getInstance("SHA-256")
			  .digest(text.getBytes("UTF-8")).encodeBase64().toString()
		}
		return sha256Hash(data)
	}
	
	public static String getPasswordHash(String value) {
		byte[] bytesOfPassword = value.getBytes("UTF-8");
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(bytesOfPassword);
		byte[] bytesOfEncryptedPassword = md.digest();
		return new String(bytesOfEncryptedPassword);
	}
	
}
