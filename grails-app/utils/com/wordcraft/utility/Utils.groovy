package com.wordcraft.utility

import java.security.MessageDigest
import com.wordcraft.utility.Constants
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.TimeZone
import java.util.Date
import java.text.DateFormat
import java.text.SimpleDateFormat

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

    /**
	* Given user timezone, the hour and the minute compte the server next time to run
	* a background job
    *	
	* @param timezone - the user's timezone
	* @param hour -  the hour as a number from 0 to 23 at which the job should run
	* @param minute - the minute as a number from 0 to 59 at which the job should run
	*
	* @return a list of 3 elements which indicates the next server time to run: 
	* first element is the date expressed as string in 'YYYY-mm-dd' format, 
	* the 2nd element is the hour from 0-23
	* the 3rd element is the minute from 0-59 
	*/
	def static getFirstJobTime(String timezone, int hour, int minute) {
		def localTime = convertToLocalTime(timezone, hour, minute, 0)

		def localHour = localTime['hour']
		def localMinute = localTime['minute']
		def localTimeValue = 60 * localHour + localMinute

		Date currentDate = new Date()
		Calendar currentCal = Calendar.getInstance()
        currentCal.setTime(currentDate)
        
		def currentHour = currentCal.get(Calendar.HOUR_OF_DAY)
		def currentMinute = currentCal.get(Calendar.MINUTE)
		def currentTimeValue = 60 * currentHour + currentMinute

		DateFormat df = new SimpleDateFormat(Constants.NOTIFICATION_DATE_FORMAT);
		def d = ''
		if (localTimeValue > currentTimeValue) {// not past yet, schedule for today
			d = df.format(currentDate)
		} else {// past already, schedule for tomorrow
			currentCal.add(Calendar.DATE, 1)
			Date nextDate = currentCal.getTime()
			d = df.format(nextDate)
		}

		return ['date': d, 'hour': localHour, 'minute': localMinute]
	}


	def static convertToLocalTime(String timezone, int hour, int minute, int second) {
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone(timezone));
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);

		Calendar localCal = new GregorianCalendar();
		localCal.setTimeInMillis(cal.getTimeInMillis());
		int h = localCal.get(Calendar.HOUR_OF_DAY);
		int m = localCal.get(Calendar.MINUTE);
		int s = localCal.get(Calendar.SECOND);

		return ['hour': h, 'minute': m, 'second': s]
	} 
	
}
