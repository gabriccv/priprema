package com.example.myapp;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.model.Book;

import java.util.ArrayList;
import java.util.List;

public class AuthorBooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_book);

        // Dohvati ime autora iz intenta
        String authorName = getIntent().getStringExtra("author_name");

        // Dohvati listu knjiga autora iz intenta
        List<Book> authorBooks = getIntent().getParcelableArrayListExtra("author_books");

        // Prikazi ime autora na vrhu layout-a
        TextView authorNameTextView = findViewById(R.id.text_author_name);
        authorNameTextView.setText(authorName);

        // Prikazi listu knjiga autora
        LinearLayout authorBooksContainer = findViewById(R.id.authorBooksContainer);
        for (Book book : authorBooks) {
            TextView textView = new TextView(this);
            textView.setText(book.getTitle());
            authorBooksContainer.addView(textView);
        }
    }
}
