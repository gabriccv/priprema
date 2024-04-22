package com.example.myapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapp.DatabaseHelper;
import com.example.myapp.R;
import com.example.myapp.model.Book;
import com.example.myapp.model.Rec;


import java.util.List;

public class RecFragment extends Fragment {

    private EditText editTextSearch;
    private Button buttonSearch;
    private TextView textViewSearchResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflating the layout for this fragment
        View view = inflater.inflate(R.layout.rec_fragment, container, false);

        // Initialize views
        editTextSearch = view.findViewById(R.id.editTextSearch);
        buttonSearch = view.findViewById(R.id.buttonSearch);
        textViewSearchResult = view.findViewById(R.id.textViewSearchResult);

        // Set onClickListener for search button
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to perform search
                performSearch();
            }
        });

        return view;
    }

    private void performSearch() {


        // Get book title from editTextBookTitle
        String bookTitle = editTextSearch.getText().toString().trim();

        // Check if search query or book title is empty
        if (bookTitle.isEmpty()) {
            // If search query or book title is empty, display a message
            textViewSearchResult.setText("Please enter both search query and book title");
            return;
        }

        // Perform search for comments based on search query and book title
        searchComments(bookTitle);
    }


    // Method to search comments by book title in the database
    private void searchComments(String selectedBook) {
        // Get database helper instance
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

        // Perform search for comments in the database based on search query and selected book
        // Replace this with your actual implementation
        List<Rec> searchResults = dbHelper.searchCommentsByTitleAndBook(selectedBook);

        // Update UI with search results
        updateSearchResults(searchResults);
    }

    // Method to update UI with search results
    private void updateSearchResults(List<Rec> searchResults) {
        // Check if search results are empty
        if (searchResults.isEmpty()) {
            // If no results found, display a message
            textViewSearchResult.setText("No results found");
            return;
        }

        // If results found, prepare a StringBuilder to display search results
        StringBuilder resultText = new StringBuilder("Search results:\n");

        // Append each search result to the StringBuilder
        for (Rec rec : searchResults) {
            resultText.append("-    title: ").append(rec.getBookTitle()).append("\n");
            resultText.append("     comment: ").append(rec.getComment()).append("\n");
            resultText.append("     name: ").append(rec.getName()).append("\n");


        }

        // Display search results in textViewSearchResult
        textViewSearchResult.setText(resultText.toString());
    }
}