package ru.digitalleague.factory.ok;

public class User {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Language language;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Language getLanguage() {
        return language;
    }

    public User(Long id, String name, String email, String phone, Language language) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.language = language;
    }
}
