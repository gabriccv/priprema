package com.example.myapp.model;

public class Rec {
    private String name;
    private String comment;
    private String bookTitle;

    public Rec( String name, String comment, String bookTitle) {
        this.name = name;
        this.comment = comment;
        this.bookTitle = bookTitle;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}
