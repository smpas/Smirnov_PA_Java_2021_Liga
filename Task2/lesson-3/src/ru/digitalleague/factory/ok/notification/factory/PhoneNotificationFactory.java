package ru.digitalleague.factory.ok.notification.factory;

import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.Notification;
import ru.digitalleague.factory.ok.notification.PhoneNotification;
import ru.digitalleague.factory.ok.notification.decorator.NotificationTemplate;

public class PhoneNotificationFactory implements NotificationFactory {
    public Notification makeNotification(NotificationTemplate template, User user) {
        return new PhoneNotification(template, user);
    }
}
