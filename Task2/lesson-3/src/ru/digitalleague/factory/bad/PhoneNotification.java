package ru.digitalleague.factory.bad;


import ru.digitalleague.factory.ok.User;

public class PhoneNotification implements Notification {

    private String body;
    private User user;

    public PhoneNotification(String body, User user) {
        this.body = body;
        this.user = user;
    }

    public String getText() {
        return String.format(
                "Phone #%s\n%s",
                user.getPhone(),
                body
        );
    }
}
