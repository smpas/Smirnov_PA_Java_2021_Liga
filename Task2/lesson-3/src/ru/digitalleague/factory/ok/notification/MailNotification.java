package ru.digitalleague.factory.ok.notification;


import ru.digitalleague.factory.ok.User;
import ru.digitalleague.factory.ok.notification.decorator.NotificationTemplate;

public class MailNotification implements Notification {

    private NotificationTemplate template;
    private User user;

    public MailNotification(NotificationTemplate template, User user) {
        this.template = template;
        this.user = user;
    }

    @Override
    public String getText() {
        return getHeader() + template.getTemplate();
    }

    @Override
    public String getHeader() {
        return String.format("%s\n%s, ",
                user.getEmail(),
                user.getName()
        );
    }
}
