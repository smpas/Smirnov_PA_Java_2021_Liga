package ru.digitalleague.factory.ok.notification;

import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.decorator.NotificationTemplate;

public class PhoneNotification implements Notification {

    private NotificationTemplate template;
    private User user;

    public PhoneNotification(NotificationTemplate template, User user) {
        this.template = template;
        this.user = user;
    }

    @Override
    public String getText() {
        return String.format("%s\n%s\n%s\n",
                user.getPhone(),
                user.getName(),
                getTemplate()
        );
    }

    @Override
    public NotificationTemplate getTemplate() {
        return template;
    }
}
