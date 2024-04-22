package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.model.Book;

import java.util.List;

public class AddBookActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextAuthor, editTextPages, editTextGenre;
    private Spinner spinnerBinding;
    private Button buttonAdd;
    private DatabaseHelper dbHelper; // Deklaracija dbHelper kao globalne varijable



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);

        // Inicijalizacija UI elemenata
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        editTextPages = findViewById(R.id.editTextPages);
        editTextGenre = findViewById(R.id.editTextGenre);
        spinnerBinding = findViewById(R.id.spinnerBinding);
        buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonDelete = findViewById(R.id.buttonDelete);



        // Definisanje opcija za Spinner
        String[] bindingOptions = {"Hardcover", "Paperback"};

        // Kreiranje adaptera za Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bindingOptions);

        // Postavljanje izgleda opcija u Spinner-u
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Postavljanje adaptera na Spinner
        spinnerBinding.setAdapter(adapter);
        dbHelper = new DatabaseHelper(this);


        // Postavljanje akcije kada se izabere opcija u Spinner-u
        spinnerBinding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Poziv metode koja se izvršava kada se izabere opcija
                // Možete izvršiti dodatne akcije ovde ako je potrebno
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Metoda koja se izvršava kada nije izabrana nijedna opcija
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            editTextTitle.setText(intent.getStringExtra("book_title"));
            editTextAuthor.setText(intent.getStringExtra("book_author"));
            editTextPages.setText(String.valueOf(intent.getIntExtra("book_pages", 0)));
            editTextGenre.setText(intent.getStringExtra("book_genre"));

            String binding = intent.getStringExtra("book_binding");
            if (binding != null) {
                int position = adapter.getPosition(binding);
                if (position != -1) {
                    spinnerBinding.setSelection(position);
                }
            }

            boolean isEditable = intent.getBooleanExtra("is_editable", false);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteBookFromDatabase();

                    Toast.makeText(AddBookActivity.this, "Book deleted successfully", Toast.LENGTH_SHORT).show();
                    // Opciono, možete zatvoriti trenutnu aktivnost nakon brisanja knjige
                    finish();
                }
            });
            EditText editTextAuthor = findViewById(R.id.editTextAuthor);
            editTextAuthor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Dohvati autora iz EditText-a
                    String author = editTextAuthor.getText().toString();

                    // Dohvati sve knjige tog autora iz baze podataka
                    List<Book> authorBooks = dbHelper.getBooksByAuthor(author);

                    // Prikazi nazive svih knjiga tog autora
                    StringBuilder authorBookTitles = new StringBuilder();
                    for (Book book : authorBooks) {
                        authorBookTitles.append(book.getTitle()).append("\n");
                    }

                    // Prikazi rezultat u authorBooksContainer
                    TextView textView = new TextView(AddBookActivity.this);
                    textView.setText(authorBookTitles.toString());
                    LinearLayout authorBooksContainer = findViewById(R.id.authorBooksContainer);
                    authorBooksContainer.removeAllViews(); // Obrisi prethodne rezultate
                    authorBooksContainer.addView(textView);
                }
            });
            // Ovde možete dodati logiku za onemogućavanje polja ako je isEditable postavljeno na false
        }

        // Postavljanje akcije kada se klikne na dugme "Add Book"
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookToDatabase();

                Toast.makeText(AddBookActivity.this, "Book added successfully", Toast.LENGTH_SHORT).show();
                // Opciono, možete zatvoriti trenutnu aktivnost nakon dodavanja knjige
                finish();
            }
        });
    }

    private void addBookToDatabase() {
        String title = editTextTitle.getText().toString();
        String author = editTextAuthor.getText().toString();
        int pages = Integer.parseInt(editTextPages.getText().toString());
        String genre = editTextGenre.getText().toString();
        String binding = spinnerBinding.getSelectedItem().toString();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        boolean success = databaseHelper.addBook(title, pages, binding, genre, author);

        if (success) {
            Toast.makeText(this, "Book added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add book", Toast.LENGTH_SHORT).show();
        }

    }
    private void deleteBookFromDatabase() {
        // Dohvatanje naslova knjige iz EditText-a
        String title = editTextTitle.getText().toString();

        // Brisanje knjige iz baze podataka
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        boolean success = databaseHelper.deleteBook(title);

        if (!success) {
            Toast.makeText(this, "Failed to delete book", Toast.LENGTH_SHORT).show();
        }
    }
}

