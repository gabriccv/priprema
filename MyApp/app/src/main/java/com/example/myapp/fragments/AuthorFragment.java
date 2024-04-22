package com.example.myapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapp.AuthorBooksActivity;
import com.example.myapp.DatabaseHelper;
import com.example.myapp.MainActivity;
import com.example.myapp.R;
import com.example.myapp.model.Book;
import com.example.myapp.model.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorFragment extends Fragment {

    private static final String ARG_AUTHOR_NAME = "authorName";
    private static final String ARG_AUTHOR_BIRTHDATE = "authorBirthdate";
    private static final String ARG_AUTHOR_BIRTHPLACE = "authorBirthplace";

    private String authorName;
    private String authorBirthdate;
    private String authorBirthplace;
    private LinearLayout titleContainer;

    public AuthorFragment() {
        // Required empty public constructor
    }

//    public static AuthorFragment newInstance(String authorName, String authorBirthdate, String authorBirthplace) {
//        AuthorFragment fragment = new AuthorFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_AUTHOR_NAME, authorName);
//        args.putString(ARG_AUTHOR_BIRTHDATE, authorBirthdate);
//        args.putString(ARG_AUTHOR_BIRTHPLACE, authorBirthplace);
//        fragment.setArguments(args);
//        return fragment;
//    }
    public static AuthorFragment newInstance(List<Author> authors) {
        AuthorFragment fragment = new AuthorFragment();
        Bundle args = new Bundle();
        // Proslijeđivanje niza imena autora u fragment
        ArrayList<String> authorNames = new ArrayList<>();
        for (Author author : authors) {
            authorNames.add(author.getName());
        }
        args.putStringArrayList("author_names", authorNames);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

        View view = inflater.inflate(R.layout.author_fragment, container, false);

        titleContainer = view.findViewById(R.id.cont);
        titleContainer.removeAllViews();

        // Uzimanje imena autora iz argumenata fragmenta
        ArrayList<String> authorNames = getArguments().getStringArrayList("author_names");
        if (authorNames != null) {
            for (String authorName : authorNames) {
                TextView textView = new TextView(requireContext());
                textView.setText(authorName);
                textView.setTextSize(10); // Postavljanje veličine teksta prema potrebi

                // Dodavanje novog TextView-a u LinearLayout
                titleContainer.addView(textView);

                // Postavljanje OnClickListener-a za otvaranje prozora sa spiskom knjiga autora
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dohvati ime autora iz TextView-a
                        String author = textView.getText().toString();

                        textView.setTextColor(getResources().getColor(R.color.white));

                        // Dohvati sve knjige tog autora iz baze podataka
                        List<Book> authorBooks = dbHelper.getBooksByAuthor(author);

                        // Kreiraj novi intent za otvaranje AuthorBooksActivity
                        Intent intent = new Intent(requireActivity(), AuthorBooksActivity.class);
                        // Proslijedi ime autora u intent
                        intent.putExtra("author_name", author);
                        // Proslijedi listu knjiga autora u intent
                        intent.putParcelableArrayListExtra("author_books", new ArrayList<>(authorBooks));
                        // Pokreni aktivnost
                        startActivity(intent);
                    }
                });
            }
        }


        TextView nameTextView = view.findViewById(R.id.text_author_name);
        TextView birthdateTextView = view.findViewById(R.id.text_author_birthdate);
        TextView birthplaceTextView = view.findViewById(R.id.text_author_birthplace);

        nameTextView.setText(authorName);
        birthdateTextView.setText(authorBirthdate);
        birthplaceTextView.setText(authorBirthplace);


        // Postavljanje OnClickListener-a za otvaranje prozora sa spiskom knjiga autora



        // Promena boje teksta imena i prezimena u plavu
        nameTextView.setTextColor(getResources().getColor(R.color.purple_200));

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            authorName = getArguments().getString(ARG_AUTHOR_NAME);
            authorBirthdate = getArguments().getString(ARG_AUTHOR_BIRTHDATE);
            authorBirthplace = getArguments().getString(ARG_AUTHOR_BIRTHPLACE);
        }
    }




    private void openBooksByAuthor(String authorName) {
        // Dohvati sve knjige tog autora iz baze podataka
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        List<Book> authorBooks = dbHelper.getBooksByAuthor(authorName);

        // Pripremi listu naslova knjiga
        StringBuilder authorBookTitles = new StringBuilder();
        for (Book book : authorBooks) {
            authorBookTitles.append(book.getTitle()).append("\n");
        }

        // Prikazi prozor sa spiskom knjiga autora
        // Ovo može biti novi fragment ili aktivnost, u zavisnosti od vašeg dizajna aplikacije
        // Ovde samo prikazujem spisak u Toast poruci
        Context context = requireContext();
        Toast.makeText(context, authorBookTitles.toString(), Toast.LENGTH_SHORT).show();
    }

    public void setAuthorList(List<Author> authorList) {
        // Prvo provjeravamo da li je layout kontejner dostupan
        View rootView = getView(); // Dohvati root view fragmenta
        if (rootView == null) {
            // Ako root view nije dostupan, prekidamo izvršavanje metode
            return;
        }

        // Pronađi kontejner unutar layout-a fragmenta
        LinearLayout layout = rootView.findViewById(R.id.containerr);
        if (layout == null) {
            // Ako layout kontejner nije dostupan, prekidamo izvršavanje metode
            return;
        }

        // Uklonimo sve prethodne TextView elemente iz layout kontejnera
        layout.removeAllViews();

        // Zatim prolazimo kroz listu autora i dodajemo ih u layout
        for (Author author : authorList) {
            // Kreiramo novi TextView za svakog autora
            TextView textView = new TextView(requireContext());
            textView.setText(author.getName()); // Postavljamo ime autora u TextView
            textView.setTextSize(20); // Postavljamo veličinu teksta

            // Dodajemo TextView u layout kontejner
            layout.addView(textView);

        }
    }



}
