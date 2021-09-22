package ru.digitalleague.factory.ok.notification;

import ru.digitalleague.factory.ok.notification.decorator.NotificationTemplate;

public interface Notification {
    String getText();
    NotificationTemplate getTemplate();
}
