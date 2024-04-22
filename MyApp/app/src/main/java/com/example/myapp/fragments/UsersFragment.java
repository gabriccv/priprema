package com.example.myapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    private ListView usersListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.korisnici, container, false);

        // Inicijalizacija ListView-a za prikaz korisnika
        usersListView = view.findViewById(R.id.usersListView);

        // Dobijanje listu korisnika (u ovom primeru, koristimo statičke podatke)
        List<String> users = new ArrayList<>();
        users.add("Korisnik 1");
        users.add("Korisnik 2");
        // Dodajte ostale korisnike prema potrebi

        // Kreiranje adaptera i postavljanje podataka u ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, users);
        usersListView.setAdapter(adapter);

        return view;
    }
}
