package ru.digitalleague.factory.ok.notification.decorator;

import ru.digitalleague.factory.ok.Language;
import ru.digitalleague.factory.ok.notification.Notification;

// Класс-декоратор для перевода оповещений
public class TranslationNotification extends NotificationDecorator {
    private Resource resource = new Resource();
    private Language language;

    public TranslationNotification(Notification notification, Language language) {
        super(notification);
        this.language = language;
    }

    @Override
    public String getText() {
        return super.getText() + getTranslation();
    }

    public String getTranslation() {
        return resource.getTranslation(getTemplate(), language);
    }
}
