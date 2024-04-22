package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private Button registration;
    private DatabaseHelper dbHelper;



    // Ključevi za čuvanje emaila i lozinke u SharedPreferences
    private static final String PREF_EMAIL = "email";
    private static final String PREF_PASSWORD = "password";
    private static final String PREF_NAME = "login_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Inicijalizacija UI elemenata
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        registration = findViewById(R.id.buttonRegistration);
        dbHelper = new DatabaseHelper(this);



        // Provera da li je korisnik već prijavljen
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString(PREF_EMAIL, "");
        String savedPassword = sharedPreferences.getString(PREF_PASSWORD, "");

        if (!TextUtils.isEmpty(savedEmail) && !TextUtils.isEmpty(savedPassword)) {
            // Ako su email i lozinka sačuvani, automatski se popunjavaju polja
            editTextEmail.setText(savedEmail);
            editTextPassword.setText(savedPassword);
        }
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        // Postavljanje onClickListener-a za dugme Login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dohvatanje unetih podataka

                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Provera da li su polja popunjena
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Molimo unesite email i lozinku", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Provera korisničkih podataka u bazi
                boolean isValid = dbHelper.checkUser(email, password);
                if (isValid) {
                    // Prijavljivanje uspešno
                    Toast.makeText(LoginActivity.this, "Uspešno ste se prijavili", Toast.LENGTH_SHORT).show();

                    // Čuvanje emaila i lozinke u SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(PREF_EMAIL, email);
                    editor.putString(PREF_PASSWORD, password);
                    editor.apply();

                    // Prebacivanje na sledeću aktivnost (ili fragment)
                    Intent intent = new Intent(LoginActivity.this, Choose.class);
                    startActivity(intent);
                } else {
                    // Prijavljivanje neuspešno
                    Toast.makeText(LoginActivity.this, "Pogrešan email ili lozinka", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
