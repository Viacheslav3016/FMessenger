package com.example.messegeme.model;

public class User {
    private String id;
    private String name;
    private String surname;
    private int age;
    private boolean online;

    public User(String id, String name, String surname, int age, Boolean online) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.online = online;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public boolean getOnline() {
        return online;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", Surname='" + surname + '\'' +
                ", age=" + age +
                ", isOnline=" + online +
                '}';
    }
}
