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

    public String getText() {
        return getHeader() + template.getTemplate();
    }

    @Override
    public String getHeader() {
        return String.format("%s\n%s, ",
                user.getPhone(),
                user.getName()
        );
    }
}
