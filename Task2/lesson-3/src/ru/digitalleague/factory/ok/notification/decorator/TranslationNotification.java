package ru.digitalleague.factory.ok.notification.decorator;

import ru.digitalleague.factory.ok.Language;
import ru.digitalleague.factory.ok.notification.Notification;

// Класс-декоратор для перевода оповещений
public class TranslationNotification extends NotificationDecorator {
    private Resource resource = new Resource();
    private Language language;
    private NotificationTemplate template;

    public TranslationNotification(Notification notification, Language language, NotificationTemplate template) {
        super(notification);
        this.language = language;
        this.template = template;
    }

    @Override
    public String getText() {
        return notification.getHeader() + resource.getTranslation(template, language);
    }

    @Override
    public String getHeader() {
        return notification.getHeader();
    }
}
