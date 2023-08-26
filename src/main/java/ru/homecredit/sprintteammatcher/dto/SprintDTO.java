package ru.homecredit.sprintteammatcher.dto;

public class SprintDTO {
    private String id;
    private String name;

    // конструктор
    public SprintDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // геттеры и сеттеры
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

