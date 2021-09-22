package ru.digitalleague.factory.ok.notification.decorator;

import ru.digitalleague.factory.ok.Language;

import java.util.HashMap;
import java.util.Map;

// Класс-ресурс с переводами оповещений
public class Resource {
    private Map<NotificationTemplate, Map<Language, String>> resource = new HashMap<>();

    public Resource() {
        Map<Language, String> congratulations = new HashMap<>();
        congratulations.put(Language.ENGLISH, "On behalf of the entire company, we sincerely" +
                "wish you happy birthday. May all wishes come true!\n");
        congratulations.put(Language.FRENCH, "Au nom de toute l'entreprise, nous vous félicitons " +
                "sincèrement pour votre anniversaire. Que tous les vœux se réalisent!\n");
        congratulations.put(Language.DEUTSCH, "Im Namen des gesamten Unternehmens gratulieren wir " +
                "Ihnen ganz herzlich zu Ihrem Geburtstag. Mögen alle Wünsche in Erfüllung gehen!\n");

        Map<Language, String> feedback = new HashMap<>();
        feedback.put(Language.ENGLISH, "You have used our services, please leave a review on the " +
                "link: https://company.com/feedback\n");
        feedback.put(Language.FRENCH, "Vous avez utilisé nos services, veuillez laisser un avis sur " +
                "le lien: https://company.com/feedback\n");
        feedback.put(Language.DEUTSCH, "Sie haben unsere Dienste genutzt, bitte hinterlassen Sie " +
                "eine Bewertung auf dem Link: https://company.com/feedback\n");

        Map<Language, String> advertisement = new HashMap<>();
        advertisement.put(Language.ENGLISH, "Reducing prices! Discounts on the services of our " +
                "company up to 70%.\n");
        advertisement.put(Language.FRENCH, "Baisse des prix! Remises sur les services de notre " +
                "entreprise jusqu'à 70%.\n");
        advertisement.put(Language.DEUTSCH, "Preise senken! Rabatte auf die Dienstleistungen unseres " +
                "Unternehmens bis zu 70%.\n");

        resource.put(NotificationTemplate.CONGRATULATION, congratulations);
        resource.put(NotificationTemplate.FEEDBACK, feedback);
        resource.put(NotificationTemplate.ADVERTISEMENT, advertisement);
    }

    public String getTranslation(NotificationTemplate template, Language language) {
        return resource.get(template).get(language);
    }
}
