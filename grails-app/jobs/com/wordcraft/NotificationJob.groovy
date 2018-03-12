package com.wordcraft

import com.wordcraft.utility.Constants

import java.text.DateFormat
import java.text.SimpleDateFormat

class NotificationJob {

    def CraftNotificationService craftNotificationService
    def description = "Push Notification Job"

    static triggers = {
        cron name: 'pushNotification', cronExpression: "0 0/5 * * * ?" //execute every 5 minute
    }

    def execute() {
        DateFormat df = new SimpleDateFormat(Constants.NOTIFICATION_DATE_FORMAT)
        Date currentDate = new Date()
        log.info("Sending push notifications at ${currentDate.toString()}")

        String today = df.format(currentDate)
        Calendar cal = Calendar.getInstance()
        cal.setTime(currentDate)
        int currentHour = cal.get(Calendar.HOUR_OF_DAY)
        int currentMinute = cal.get(Calendar.MINUTE)

        int currentTimeValue = 60 * currentHour + currentMinute
        int lowerBoundValue = currentTimeValue - Constants.LOWER_BOUNDARY_VALUE
        int upperBoundValue = currentTimeValue + Constants.UPPER_BOUNDARY_VALUE

        def notifications = CraftNotification.executeQuery("select n from CraftNotification n " +
                "where n.date = :d " +
                "and 60*n.hour+n.minute >= :lowerBound and 60*n.hour+n.minute <= :upperBound",
                [d: today, lowerBound: lowerBoundValue, upperBound: upperBoundValue])

        notifications.each { notification ->
            sendAndSetup(notification)
        }
    }

    def private sendAndSetup(CraftNotification notification) {
        craftNotificationService.sendNotification(notification)

        // Add next notification for this user and delete the current notification
        def wordCraftsman = notification.wordCraftsman
        def settings = wordCraftsman.craftSettings
        craftNotificationService.addNextNotification(notification, settings)
        notification.delete()
    }
}
