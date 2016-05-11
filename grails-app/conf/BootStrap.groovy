import com.wordcraft.CraftLog
import com.wordcraft.CraftSettings
import com.wordcraft.WordCraftsman
import com.wordcraft.WordFrequency
import com.wordcraft.utility.EventType
import grails.util.Environment;

class BootStrap {

	def grailsApplication

	def init = { servletContext ->
		if (Environment.current == Environment.DEVELOPMENT) {
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

			def settings = new CraftSettings(craftLoad: 3, craftPace:2)
			settings.save(flush:true)

			def craftLog = new CraftLog(eventTime: new Date(), eventType: EventType.TEST,
			description: "You have taken the test on ${new Date().format('dd/MM/yyyy HH:mm')} ")

			def craftsman = new WordCraftsman(username: 'tonyvu', password: 'tonyvu16', email: 'tonyvu@wordcraft.com',
			craftSettings: settings, level: 2, estimatedSize: 2651)
			craftsman.addToCraftLogs(craftLog)
			craftsman.save(flush:true)
		}
	}

	def destroy = {
	}
}
