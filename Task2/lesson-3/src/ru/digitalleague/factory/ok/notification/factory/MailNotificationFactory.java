package ru.digitalleague.factory.ok.notification.factory;

import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.Notification;
import ru.digitalleague.factory.ok.notification.MailNotification;
import ru.digitalleague.factory.ok.notification.decorator.NotificationTemplate;

public class MailNotificationFactory implements NotificationFactory {
    public Notification makeNotification(NotificationTemplate template, User user) {
        return new MailNotification(template, user);
    }
}
