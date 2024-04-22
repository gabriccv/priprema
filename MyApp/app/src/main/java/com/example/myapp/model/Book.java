package com.example.myapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String title;
    private int pages;
    private String binding;
    private String genre;
    private String author;

    public Book(String title, int pages, String binding, String genre, String author) {
        this.title = title;
        this.pages = pages;
        this.binding = binding;
        this.genre = genre;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public int getPages() {
        return pages;
    }

    public String getBinding() {
        return binding;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }
    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        pages = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeInt(pages);
    }
}
