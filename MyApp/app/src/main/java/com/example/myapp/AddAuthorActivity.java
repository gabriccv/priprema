package com.example.myapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.model.Author;

public class AddAuthorActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextBirthdate;
    private EditText editTextBirthplace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_author);

        // Pronađi sve EditText polja
        editTextName = findViewById(R.id.edit_text_author_name);
        editTextBirthdate = findViewById(R.id.edit_text_author_birthdate);
        editTextBirthplace = findViewById(R.id.edit_text_author_birthplace);

        // Pronađi dugme za dodavanje autora
        Button buttonAddAuthor = findViewById(R.id.button_add_author);
        buttonAddAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAuthorToDatabase();
                finish();
            }
        });
    }

    private void addAuthorToDatabase() {
        String name = editTextName.getText().toString();
        String birthdate = editTextBirthdate.getText().toString();
        String birthplace = editTextBirthplace.getText().toString();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        boolean success = databaseHelper.addAuthor(name, birthdate, birthplace);

        if (success) {
            Toast.makeText(this, "Author added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add author", Toast.LENGTH_SHORT).show();
        }

    }
}
