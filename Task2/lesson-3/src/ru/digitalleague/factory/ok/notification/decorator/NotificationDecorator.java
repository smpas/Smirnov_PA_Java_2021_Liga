package ru.digitalleague.factory.ok.notification.decorator;

import ru.digitalleague.factory.ok.notification.Notification;

// Абстрактный декоратор
public abstract class NotificationDecorator implements Notification {
    protected Notification notification;

    public NotificationDecorator(Notification notification) {
        this.notification = notification;
    }

    @Override
    public String getText() {
        return notification.getText();
    }

    @Override
    public NotificationTemplate getTemplate() {
        return notification.getTemplate();
    }
}
