package com.example.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.myapp.fragments.AuthorFragment;



import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.databinding.ActivityMainBinding;
import com.example.myapp.fragments.FragmentTransition;
import com.example.myapp.fragments.RecFragment;
import com.example.myapp.model.Author;
import com.example.myapp.model.Book;

import java.util.List;

public class MainActivity extends AppCompatActivity {
//    private Toolbar toolbar;
//    private ActionBar actionBar;
//    private ActionBarDrawerToggle actionBarDrawerToggle;
    private LinearLayout titleContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleContainer = findViewById(R.id.containerr);
        Button addBookButton = findViewById(R.id.addBook);
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);

                // Pokretanje AddBookActivity
                startActivity(intent);
            }
        });
        Button addAuthorButton = findViewById(R.id.addAuthor);
        addAuthorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAuthorActivity.class);

                // Pokretanje AddBookActivity
                startActivity(intent);
            }
        });
        Button addRecsButton = findViewById(R.id.addRecButton);
        addRecsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Otvaranje nove aktivnosti ili dijaloga za unos komentara
                openAddRecsForm();
            }
        });
    }

    private void openAddRecsForm() {
        // Kreiranje novog Intent-a za otvaranje AddRecsActivity (primer koda za otvaranje nove aktivnosti)
        Intent intent = new Intent(MainActivity.this, AddRecsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_manu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.book:
                displayAllBooks();
                return true;
            case R.id.author:
                displayAllAuthors();
            return true;
            case R.id.recs:
                openRecsFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void openRecsFragment() {
        // Kreiranje instance RecsFragmenta
        RecFragment recFragment = new RecFragment();

        // Koristite FragmentTransition klasu da postavite RecsFragment unutar titleContainer layout-a
        FragmentTransition.to(recFragment, this, true, R.id.containerr);
    }
    private void displayAllBooks() {
        // Dohvati sve knjige iz baze podataka
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Book> books = dbHelper.getAllBooks();
        titleContainer.removeAllViews();

        // Prikazi sve knjige unutar kontejnera
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            TextView textView = new TextView(this);
            textView.setText(book.getTitle());
            textView.setTextSize(20); // Postavljanje veličine teksta prema potrebi

            // Dodavanje novog TextView-a u LinearLayout
            titleContainer.addView(textView);

            // Postavljanje OnClickListener-a za otvaranje detalja knjige
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openAddBookActivityWithBookData(book);
                }
            });
        }

        // Postavi tekst u titleContainer
//        titleContainer.setText(sb.toString());
    }
    private void openAddBookActivityWithBookData(Book book) {
        Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
        intent.putExtra("book_title", book.getTitle());
        intent.putExtra("book_author", book.getAuthor());
        intent.putExtra("book_pages", book.getPages());
        intent.putExtra("book_genre", book.getGenre());
        intent.putExtra("book_binding", book.getBinding());
        intent.putExtra("is_editable", false); // Da označimo da se polja ne mogu uređivati
        startActivity(intent);
    }
//    private void displayAllAuthors() {
//        // Dohvati sve autore iz baze podataka
//        DatabaseHelper dbHelper = new DatabaseHelper(this);
//        List<Author> authors = dbHelper.getAllAuthors();
//        titleContainer.removeAllViews();
//
//        // Prikazi sve autore unutar kontejnera
//        for (Author author : authors) {
//            TextView textView = new TextView(this);
//            textView.setText(author.getName());
//            textView.setTextSize(20); // Postavljanje veličine teksta prema potrebi
//
//            // Dodavanje novog TextView-a u LinearLayout
//            titleContainer.addView(textView);;
//        }
//    }
    private void displayAllAuthors() {
        // Dohvati sve autore iz baze podataka
        titleContainer.removeAllViews();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Author> authors = dbHelper.getAllAuthors();

        // Kreiraj novi instance AuthorFragment-a i proslijedi listu autora kao argument
        AuthorFragment authorFragment = AuthorFragment.newInstance(authors);

        // Koristi FragmentTransition klasu da postaviš AuthorFragment unutar titleContainer layout-a
        FragmentTransition.to(authorFragment, this, true, R.id.containerr);


    }

}