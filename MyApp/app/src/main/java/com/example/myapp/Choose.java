package com.example.myapp;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Choose extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private Button buttonCamera, buttonChangeColor;
    private String loggedInUser;
    private TextView name;
    private TextView cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);

        // Dobijanje imena ulogovanog korisnika iz SharedPreferences
        loggedInUser = getSharedPreferences("login_prefs", MODE_PRIVATE).getString("email", "");
        name = findViewById(R.id.name);
        cont = findViewById(R.id.conta);

        // Inicijalizacija UI elemenata
        buttonCamera = findViewById(R.id.buttonCamera);
        buttonChangeColor = findViewById(R.id.buttonChangeColor);

        // Postavljanje OnClickListener-a za dugme "Kamera"
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Provera da li je dozvola za kameru već odobrena
                if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Ako nije, zatraži dozvolu od korisnika
                    requestPermissions(new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    // Ako jeste, otvori kameru
                    openCamera();
                }
            }
        });

        // Postavljanje teksta imena ulogovanog korisnika

        // Postavljanje OnClickListener-a za dugme "Promeni boju pozadine"
        buttonChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Promena boje pozadine na crvenu
                getWindow().getDecorView().setBackgroundColor(Color.RED);
                name.setText("Ulogovani korisnik: " + loggedInUser);

            }
        });
    }

    // Metoda koja se poziva kada korisnik odgovori na zahtev za dozvolu
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Dozvola za kameru je odobrena
                openCamera();
            } else {
                // Dozvola za kameru nije odobrena
                Toast.makeText(this, "Nije dozvoljena kamera!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Metoda za otvaranje kamere
    private void openCamera() {
        // Ovde možete implementirati logiku za otvaranje kamere
        // Na primer, možete pokrenuti novu aktivnost koja koristi kameru
    }
}
