package com.company.api.entity;

public class Chat {
    private int id;
    private String type;    //private, group, supergroup, channel;
    private String title;
    private String username;
    private String first_name;
    private String last_name;

    public Chat() {
    }

    public Chat(int id) {
        this.id = id;
    }

    public Chat(String username) {
        this.username = username;
    }

    public Chat(Object id) {
        if (id instanceof Integer) {
            this.id = (int) id;
        } else if (id instanceof String) {
            if (((String) id).startsWith("@")) {
                this.username = String.valueOf(id);
            } else {
                this.username = "@" + String.valueOf(id);
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
