package ru.digitalleague.factory.ok;

import ru.digitalleague.factory.ok.notification.Notification;
import ru.digitalleague.factory.ok.notification.decorator.NotificationTemplate;
import ru.digitalleague.factory.ok.notification.decorator.TranslationNotification;
import ru.digitalleague.factory.ok.notification.factory.NotificationFactory;
import ru.digitalleague.factory.ok.notification.factory.MailNotificationFactory;
import ru.digitalleague.factory.ok.notification.factory.PhoneNotificationFactory;

public class Main {

    public static void main(String[] args) {
        User user = new User(2L, "Denis", "denis@gmail.com", "+79522668105", Language.ENGLISH);

        NotificationFactory factory = true ? new MailNotificationFactory() : new PhoneNotificationFactory();

        NotificationTemplate template = NotificationTemplate.FEEDBACK;

        Notification notification = factory.makeNotification(template, user);
        if (user.getLanguage() != Language.RUSSIAN) {
            notification = new TranslationNotification(notification, user.getLanguage(), template);
        }
        sendNotification(notification);

    }

    private static void sendNotification(Notification notification) {
        System.out.println(notification.getText());
    }
}
