package ru.digitalleague.factory.ok.notification.decorator;

import ru.digitalleague.factory.ok.Language;

import java.util.HashMap;
import java.util.Map;

// Класс-ресурс с переводами оповещений
public class Resource {
    private Map<NotificationTemplate, String> english = new HashMap<>();
    private Map<NotificationTemplate, String> french = new HashMap<>();
    private Map<NotificationTemplate, String> deutsch = new HashMap<>();

    public Resource() {
        english.put(NotificationTemplate.CONGRATULATION, "on behalf of the entire company, we sincerely " +
                "wish you happy birthday. May all wishes come true!");
        english.put(NotificationTemplate.FEEDBACK, "you have used our services, please leave a review on the " +
                "link: https://company.com/feedback");
        english.put(NotificationTemplate.ADVERTISEMENT, "reducing prices! Discounts on the services of our " +
                "company up to 70%.");

        french.put(NotificationTemplate.CONGRATULATION, "au nom de toute l'entreprise, nous vous félicitons " +
                "sincèrement pour votre anniversaire. Que tous les vœux se réalisent!");
        french.put(NotificationTemplate.FEEDBACK, "vous avez utilisé nos services, veuillez laisser un avis sur " +
                "le lien: https://company.com/feedback");
        french.put(NotificationTemplate.ADVERTISEMENT, "baisse des prix! Remises sur les services de notre " +
                "entreprise jusqu'à 70%.");

        deutsch.put(NotificationTemplate.CONGRATULATION, "im Namen des gesamten Unternehmens gratulieren wir " +
                "Ihnen ganz herzlich zu Ihrem Geburtstag. Mögen alle Wünsche in Erfüllung gehen!");
        deutsch.put(NotificationTemplate.FEEDBACK, "sie haben unsere Dienste genutzt, bitte hinterlassen Sie " +
                "eine Bewertung auf dem Link: https://company.com/feedback");
        deutsch.put(NotificationTemplate.ADVERTISEMENT, "preise senken! Rabatte auf die Dienstleistungen unseres " +
                "Unternehmens bis zu 70%.");
    }

    public String getTranslation(NotificationTemplate template, Language language) {
        String translation;
        switch (language) {
            case ENGLISH:
                translation = english.get(template);
                break;
            case FRENCH:
                translation = french.get(template);
                break;
            case DEUTSCH:
                translation = deutsch.get(template);
                break;
            default:
                translation = "";
        }
        return translation;
    }
}
