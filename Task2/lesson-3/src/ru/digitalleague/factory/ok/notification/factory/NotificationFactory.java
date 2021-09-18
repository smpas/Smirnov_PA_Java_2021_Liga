package ru.digitalleague.factory.ok.notification.factory;

import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.Notification;
import ru.digitalleague.factory.ok.notification.decorator.NotificationTemplate;

public interface NotificationFactory {
    Notification makeNotification(NotificationTemplate template, User user);
}
