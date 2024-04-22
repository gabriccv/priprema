package com.example.myapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.R;
import com.example.myapp.model.Book;
import com.example.myapp.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;

public class AddRecsActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextComment;
    private Spinner spinnerBooks;
    private Button buttonAdd;

    private List<Book> bookList; // Lista knjiga iz baze
    private ArrayAdapter<String> spinnerAdapter; // Adapter za spinner

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recs);

        // Inicijalizacija view elemenata
        editTextName = findViewById(R.id.editTextName);
        editTextComment = findViewById(R.id.editTextComment);
        spinnerBooks = findViewById(R.id.spinnerBooks);
        buttonAdd = findViewById(R.id.addRecsButton);

        // Inicijalizacija liste knjiga
        bookList = loadBooksFromDatabase();

        // Kreiranje adaptera za spinner
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getBookTitles());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Postavljanje adaptera na spinner
        spinnerBooks.setAdapter(spinnerAdapter);

        // Dodavanje onItemClick listenera na spinner
        spinnerBooks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Pozivamo se funkciju koja će biti pozvana kada je selektovana stavka iz spinner-a
                onBookSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Implementacija po potrebi
            }
        });

        // Dodavanje onClickListener-a za dugme "Add"
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecs();
            }
        });
    }

    // Metoda koja se poziva kada je selektovana stavka iz spinner-a
    private void onBookSelected(int position) {
        // Implementiraj logiku po potrebi
        // Ova metoda se može koristiti za prikaz dodatnih informacija o selektovanoj knjizi
    }

    // Metoda za učitavanje svih knjiga iz baze
    private List<Book> loadBooksFromDatabase() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        return dbHelper.getAllBooks();
    }

    // Metoda za dobijanje naslova knjiga iz liste knjiga
    private List<String> getBookTitles() {
        List<String> titles = new ArrayList<>();
        for (Book book : bookList) {
            titles.add(book.getTitle());
        }
        return titles;
    }

    private void addRecs() {
        // Dobijanje unesenih podataka
        String name = editTextName.getText().toString().trim();
        String comment = editTextComment.getText().toString().trim();

        // Provera da li je selektovan element null
        if (spinnerBooks.getSelectedItem() == null) {
            Toast.makeText(this, "Niste odabrali knjigu", Toast.LENGTH_SHORT).show();
            return;
        }

        String selectedBook = spinnerBooks.getSelectedItem().toString();

        // Validacija unetih podataka
        if (name.isEmpty() || comment.isEmpty()) {
            Toast.makeText(this, "Molimo unesite ime i komentar", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        boolean commentAdded = dbHelper.addComment(name, comment, selectedBook);

        if (commentAdded) {
            // Obaveštenje o uspešnom dodavanju
            Toast.makeText(this, "Komentar dodat: " + comment, Toast.LENGTH_SHORT).show();
        } else {
            // Obaveštenje o neuspehu dodavanja
            Toast.makeText(this, "Greška prilikom dodavanja komentara", Toast.LENGTH_SHORT).show();
        }

        // Zatvaranje aktivnosti nakon dodavanja komentara
        finish();
    }

}
