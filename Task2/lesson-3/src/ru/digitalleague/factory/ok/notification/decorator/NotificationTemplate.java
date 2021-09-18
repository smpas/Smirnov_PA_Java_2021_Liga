package ru.digitalleague.factory.ok.notification.decorator;

// Шаблоны оповещений
public enum NotificationTemplate {
    CONGRATULATION("от лица всей компании искренне поздравляем вас с днем рождения. Пусть все желания сбываются!"),
    FEEDBACK("вы воспользовались нашими услугами, оставьте, пожалуйста, отзыв по ссылке: https://company.com/feedback"),
    ADVERTISEMENT("мы снизили цены! Скидки на услуги нашей компании до 70%.");

    String template;

    NotificationTemplate(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
