import com.wordcraft.WordFrequency

import grails.util.Environment;

class BootStrap {

	def grailsApplication

	def init = { servletContext ->
		if (Environment.current == Environment.DEVELOPMENT || Environment.current == Environment.PRODUCTION) {
			if (!WordFrequency.count()) {
				def filePath = "resources/word_frequency.txt"
				def wordFrequencyText = grailsApplication.getParentContext().getResource("classpath:$filePath").inputStream.text
				print "Loading data..."
				//			int r = 1
				wordFrequencyText.eachLine { line ->
					try {
						String[] fields = line.split("\\s+")
						def wordFrequency = new WordFrequency()
						def w = fields[1]
						//					def record = WordFrequency.findByWord(w)
						//					if (record) {
						//						return
						//					}
						//			    	wordFrequency.rank = r
						wordFrequency.rank = fields[0].toInteger()
						wordFrequency.word = w
						wordFrequency.pos = fields[2]
						wordFrequency.frequency = fields[3].toInteger()
						wordFrequency.dispersion = fields[4].toFloat()
						wordFrequency.save(flush: true, failOnError:true)
						//					r++
					} catch (Exception ex) {
						ex.printStackTrace()
					}
				}
				print "Data loading finished"
				print "Word Loaded: " + WordFrequency.count()
			}
		}
	}

	def destroy = {
	}
}
