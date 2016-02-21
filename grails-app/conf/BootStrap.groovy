import com.wordcraft.WordFrequency;

class BootStrap {

    def grailsApplication	

    def init = { servletContext ->
		if (!WordFrequency.count()) {
			def filePath = "resources/word_frequency.txt"
			def wordFrequencyText = grailsApplication.getParentContext().getResource("classpath:$filePath").inputStream.text
			print "Loading data..."
			wordFrequencyText.eachLine { line ->
				try {
					String[] fields = line.split("\\s+")
					def wordFrequency = new WordFrequency()
			    	wordFrequency.rank = fields[0].toInteger()
					wordFrequency.word = fields[1]
					wordFrequency.pos = fields[2]
					wordFrequency.frequency = fields[3].toInteger()
					wordFrequency.dispersion = fields[4].toFloat()
					wordFrequency.save(flush: true, failOnError:true)	
				} catch (Exception ex) {
					ex.printStackTrace()
				}
			}
			print "Data loading finished"
			print "Word Loaded: " + WordFrequency.count()
		}
    }
	
    def destroy = {
    }
}
