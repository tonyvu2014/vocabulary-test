package com.wordcraft

import grails.transaction.Transactional
import com.wordcraft.utility.Utils

@Transactional
class CraftNotificationService {

    public updateNotifications(CraftSettings settings) {
        def notification = settings.notification 
        def wordCraftsman = settings.wordCraftsman

        if (notification) {
            removeNotifications(wordCraftsman);
            addNotification(wordCraftsman, settings);
        } else {
            removeNotifications(wordCraftsman);
        }
    }

    public removeNotifications(WordCraftsman wordCraftsman) {
        CraftNotification.executeUpdate("delete CraftNotification where wordCraftsman = ?", [wordCraftsman])
    }

    public addNotification(WordCraftsman wordCraftsman, CraftSettings settings) {
        def notificationToken = settings.notificationToken
        def craftPace = settings.craftPace
        def craftHour = settings.craftHour
        def craftMinute = settings.craftMinute
        def timezone = settings.timezone

        def nextNotificationTime = Utils.getNextJobTime(timezone, hour, minute)

        def craftNotificaiton = new CraftNotification()
        craftNotification.date = nextNotification['date']
        craftNotification.hour = nextNotification['hour']
        craftNotification.minute = nextNotification['minute']
        craftNotification.wordCraftsman = wordCraftsman
        craftNotification.token = notificationToken

        craftNotification.save(flush:true, failOnError:true)

        return craftNotification
    }

}