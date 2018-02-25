package com.wordcraft.utility

import java.security.MessageDigest
import com.wordcraft.utility.Constants

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
			bytes2Hex(java.security.MessageDigest.getInstance("SHA-256")
			  .digest(text.getBytes("UTF-8")));
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
	
	public static String bytes2Hex(byte[] data) {
		return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data));
	}
	
	/**
	 * Given a level, return a "random" level in which the closer to the  
	 * given level, it has higher probability of being selected
	 * 
	 * @param level - input level
	 * @return the level which is computed based on distributed weights 
	 */
	public static int getDistributedLevel(int level) {
		Random random = new Random()
		int r = random.nextInt(12) + 1
		
		if (r == 1) {
			if (level <= 2) {
				return 1;
			} else {
				return (random.nextInt(level-2) + 1);
			}
		} else if (r >= 2 && r <= 3) {
			if (level == 1) {
				return 1;
			} else {
				return level - 1;
			}
		} else if (r >= 4 && r <= 6) {
			return level;
		} else if (r >= 7 && r <= 11) {
			if (level == Constants.MAX_LEVEL) {
				return level;
			} else {
				return level + 1; 
			}
		} else {
			if (level >= Constants.MAX_LEVEL-1) {
				return Constants.MAX_LEVEL;
			} else {
				return (random.nextInt(Constants.MAX_LEVEL-level-1) + level + 2);
			}
		}
	}
	
}
