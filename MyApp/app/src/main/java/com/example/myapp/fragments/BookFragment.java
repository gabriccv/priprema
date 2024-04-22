package com.example.myapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapp.R;

public class BookFragment extends Fragment {

    // Fragment initialization parameters
    private static final String ARG_BOOK_TITLE = "bookTitle";
    private static final String ARG_BOOK_PAGES = "bookPages";
    private static final String ARG_BOOK_BINDING = "bookBinding";
    private static final String ARG_BOOK_GENRE = "bookGenre";
    private static final String ARG_BOOK_AUTHOR = "bookAuthor";

    // Parameters
    private String bookTitle;
    private int bookPages;
    private String bookBinding;
    private String bookGenre;
    private String bookAuthor;

    public BookFragment() {
        // Required empty public constructor
    }

    public static BookFragment newInstance(String title, int pages, String binding, String genre, String author) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_BOOK_TITLE, title);
        args.putInt(ARG_BOOK_PAGES, pages);
        args.putString(ARG_BOOK_BINDING, binding);
        args.putString(ARG_BOOK_GENRE, genre);
        args.putString(ARG_BOOK_AUTHOR, author);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookTitle = getArguments().getString(ARG_BOOK_TITLE);
            bookPages = getArguments().getInt(ARG_BOOK_PAGES);
            bookBinding = getArguments().getString(ARG_BOOK_BINDING);
            bookGenre = getArguments().getString(ARG_BOOK_GENRE);
            bookAuthor = getArguments().getString(ARG_BOOK_AUTHOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_fragment, container, false);

        TextView titleTextView = view.findViewById(R.id.text_title);
        TextView pagesTextView = view.findViewById(R.id.text_pages);
        TextView bindingTextView = view.findViewById(R.id.text_binding);
        TextView genreTextView = view.findViewById(R.id.text_genre);
        TextView authorTextView = view.findViewById(R.id.text_author);

        // Set book details to TextViews
        titleTextView.setText(bookTitle);
        pagesTextView.setText(String.valueOf(bookPages));
        bindingTextView.setText(bookBinding);
        genreTextView.setText(bookGenre);
        authorTextView.setText(bookAuthor);

        return view;
    }
}

//package com.example.myapp.fragments;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.fragment.app.Fragment;
//
//import com.example.myapp.R;
//
//public class BookFragment extends Fragment {
//
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private String mParam1;
//    private String mParam2;
//
//    public BookFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FirstFragment.
//     */
//    public static BookFragment newInstance(String param1, String param2) {
//        BookFragment fragment = new BookFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.d("MyApp", "BookFragment onCreate()");
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        Log.d("MyApp", "BookFragment onCreateView()");
//        View view = inflater.inflate(R.layout.book_fragment, container, false);
//        TextView title = view.findViewById(R.id.fragment1Title);
//        title.setText(mParam1);
//        TextView description = view.findViewById(R.id.fragment1Description);
//        description.setText(mParam2);
//        return view;
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Log.d("MyApp", "BookFragment onAttach()");
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.d("MyApp", "BookFragment onDestroyView()");
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Log.d("MyApp", "BookFragment onDetach()");
//    }
//}
//
