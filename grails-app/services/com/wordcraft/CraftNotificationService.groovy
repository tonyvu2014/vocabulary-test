package com.wordcraft

import com.wordcraft.utility.Constants
import com.wordcraft.utility.Utils
import grails.transaction.Transactional
import groovyx.net.http.Method
import org.codehaus.groovy.runtime.InvokerHelper
import groovyx.net.http.AsyncHTTPBuilder
import org.springframework.context.MessageSource

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.POST
import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class CraftNotificationService {

    def MessageSource messageSource

    def updateNotifications(CraftSettings settings) {
        def notification = settings.craftNotification
        def wordCraftsman = settings.wordCraftsman

        if (notification) {
            setUpNotification(wordCraftsman, settings)
        } else {
            removeNotifications(wordCraftsman);
        }
    }

    def setUpNotification(WordCraftsman wordCraftsman, CraftSettings settings) {
        removeNotifications(wordCraftsman);
        addNotification(wordCraftsman, settings);
    }

    def addNextNotification(CraftNotification notification, CraftSettings settings) {
        def date = notification.date
        def pace = settings.craftPace

        DateFormat df = new SimpleDateFormat(Constants.NOTIFICATION_DATE_FORMAT)
        Date currentDate = df.parse(date);
        Calendar cal = Calendar.getInstance()
        cal.setTime(currentDate)
        cal.add(Calendar.DATE, pace)
        String nextDate = df.format(cal.getTime())

        CraftNotification nextNotification = new CraftNotification()
        InvokerHelper.setProperties(nextNotification, notification.properties)
        nextNotification.setDate(nextDate)
        nextNotification.save(flush:true, failOnError: true)
    }

    def removeNotifications(WordCraftsman wordCraftsman) {
        CraftNotification.executeUpdate("delete CraftNotification where wordCraftsman = ?", [wordCraftsman])
    }

    def addNotification(WordCraftsman wordCraftsman, CraftSettings settings) {
        def notificationToken = settings.craftNotificationToken
        def craftHour = settings.craftHour
        def craftMinute = settings.craftMinute
        def timezone = settings.craftTimezone

        def nextNotificationTime = Utils.getFirstJobTime(timezone, craftHour, craftMinute)

        def craftNotification = new CraftNotification()
        craftNotification.date = nextNotificationTime['date']
        craftNotification.hour = nextNotificationTime['hour']
        craftNotification.minute = nextNotificationTime['minute']
        craftNotification.wordCraftsman = wordCraftsman
        craftNotification.token = notificationToken

        craftNotification.save(flush:true, failOnError:true)

        return craftNotification
    }

    def sendNotification(CraftNotification notification) {
        def wordCraftsman = notification.wordCraftsman
        def settings = wordCraftsman.craftSettings
        def load = settings.craftLoad
        def token = settings.craftNotificationToken

        def http =  new AsyncHTTPBuilder(poolSize: 5, uri: Constants.FIREBASE_NOTIFICATION_URL, contentType: JSON)
        def postBody = [
            to: token,
            data: [
                body: messageSource.getMessage('notification.body', [String.valueOf(load)] as Object[], Locale.US),
                title: messageSource.getMessage('notification.title', null, Locale.US)
            ]
        ]

        http.request(POST, JSON) { req ->
            uri.path = '/fcm/send'
            body = postBody
            headers.'Authorization' = "key=${Constants.ClOUD_MESSAGE_SERVER_KEY}"

            response.success = { resp, json ->
                log.info("Sending notification succeeded for user ${wordCraftsman.username}")

            }

            response.failure = { resp ->
                log.info("Sending notification failed for user ${wordCraftsman.username}. Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}")
            }
        }
    }

}
