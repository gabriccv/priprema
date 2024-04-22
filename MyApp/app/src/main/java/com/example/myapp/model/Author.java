// Author.java
package com.example.myapp.model;

public class Author {
    private String name;
    private String birthdate;
    private String birthplace;

    public Author(String name, String birthdate, String birthplace) {
        this.name = name;
        this.birthdate = birthdate;
        this.birthplace = birthplace;
    }

    public String getName() {
        return name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getBirthplace() {
        return birthplace;
    }
}
