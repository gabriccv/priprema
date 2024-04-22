package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapp.model.Author;
import com.example.myapp.model.Book;
import com.example.myapp.model.Rec;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bookssss.db";

    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "books";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_PAGES = "pages";
    public static final String COLUMN_BINDING = "binding";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_AUTHOR = "author";

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";

    public static final String TABLE_LOGED= "loged";
    public static final String COLUMN_EMAIL = "logedEmail";
    public static final String COLUMN_PASSWORD = "LogedPassword";
    public static final String TABLE_AUTHORS = "authors";
    public static final String COLUMN_AUTHOR_NAME = "author_name";
    public static final String COLUMN_AUTHOR_BIRTHDATE = "birthdate";
    public static final String COLUMN_AUTHOR_BIRTHPLACE = "birthplace";

    public static final String TABLE_RECS = "recs";
    public static final String COLUMN_RECS_ID = "id";
    public static final String COLUMN_RECS_NAME = "name";
    public static final String COLUMN_RECS_COMMENT = "comment";
    public static final String COLUMN_RECS_BOOK_TITLE = "book_title";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_TITLE + " TEXT," +
                COLUMN_PAGES + " INTEGER," +
                COLUMN_BINDING + " TEXT," +
                COLUMN_GENRE + " TEXT," +
                COLUMN_AUTHOR + " TEXT" + ")";
        db.execSQL(createTable);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_AUTHORS + " (" +
                COLUMN_AUTHOR_NAME + " TEXT," +
                COLUMN_AUTHOR_BIRTHDATE + " TEXT," +
                COLUMN_AUTHOR_BIRTHPLACE + " TEXT" + ")");
        String createRecsTable = "CREATE TABLE IF NOT EXISTS " + TABLE_RECS + " (" +
                COLUMN_RECS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_RECS_NAME + " TEXT," +
                COLUMN_RECS_COMMENT + " TEXT," +
                COLUMN_RECS_BOOK_TITLE + " TEXT" + ")";
        db.execSQL(createRecsTable);
        String createUserTable = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
                COLUMN_USER_EMAIL + " TEXT UNIQUE," +
                COLUMN_USER_PASSWORD + " TEXT" + ")";
        db.execSQL(createUserTable);
        String createLogedTable = "CREATE TABLE IF NOT EXISTS " + TABLE_LOGED+ " (" +
                COLUMN_EMAIL + " TEXT UNIQUE," +
                COLUMN_PASSWORD + " TEXT" + ")";
        db.execSQL(createLogedTable);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGED);




        onCreate(db);
    }

    public boolean addBook(String title, int pages, String binding, String genre, String author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_PAGES, pages);
        contentValues.put(COLUMN_BINDING, binding);
        contentValues.put(COLUMN_GENRE, genre);
        contentValues.put(COLUMN_AUTHOR, author);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
    public boolean addAuthor(String name, String birthday, String birthplace) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_AUTHOR_NAME, name);
        contentValues.put(COLUMN_AUTHOR_BIRTHDATE, birthday);
        contentValues.put(COLUMN_AUTHOR_BIRTHPLACE, birthplace);
        long result = db.insert("authors", null, contentValues);
        return result != -1;
    }

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                int pages = cursor.getInt(cursor.getColumnIndex(COLUMN_PAGES));
                String binding = cursor.getString(cursor.getColumnIndex(COLUMN_BINDING));
                String genre = cursor.getString(cursor.getColumnIndex(COLUMN_GENRE));
                String author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR));
                Book book = new Book(title, pages, binding, genre, author);
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bookList;
    }
    public boolean deleteBook(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_TITLE + " LIKE ?";
        String[] selectionArgs = { title };
        int deletedRows = db.delete(TABLE_NAME, selection, selectionArgs);
        return deletedRows > 0;
    }
    public List<Book> getBooksByAuthor(String author) {
        List<Book> authorBooks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_TITLE, COLUMN_PAGES, COLUMN_BINDING, COLUMN_GENRE};
        String selection = COLUMN_AUTHOR + "=?";
        String[] selectionArgs = {author};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                int pages = cursor.getInt(cursor.getColumnIndex(COLUMN_PAGES));
                String binding = cursor.getString(cursor.getColumnIndex(COLUMN_BINDING));
                String genre = cursor.getString(cursor.getColumnIndex(COLUMN_GENRE));
                Book book = new Book(title, pages, binding, genre, author);
                authorBooks.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return authorBooks;
    }
    public List<Author> getAllAuthors() {
        List<Author> authorList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_AUTHORS, null);
        if (cursor.moveToFirst()) {
            do {
                String authorName = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_NAME));
                Author author = new Author(authorName, "", ""); // Ovde možete dodati i rođenje i mesto rođenja ako su dostupni u tabeli
                authorList.add(author);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return authorList;
    }
    public boolean addComment(String name, String comment, String bookTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("comment", comment);
        contentValues.put("book_title", bookTitle);
        long result = db.insert(TABLE_RECS, null, contentValues);
        return result != -1;
    }

    public List<Book> searchBooksByTitle(String searchText) {
        List<Book> searchResults = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_TITLE, COLUMN_PAGES, COLUMN_BINDING, COLUMN_GENRE, COLUMN_AUTHOR};
        String selection = COLUMN_TITLE + " LIKE ?";
        String[] selectionArgs = {"%" + searchText + "%"}; // Koristimo % za pretraživanje delimičnih podudaranja
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                int pages = cursor.getInt(cursor.getColumnIndex(COLUMN_PAGES));
                String binding = cursor.getString(cursor.getColumnIndex(COLUMN_BINDING));
                String genre = cursor.getString(cursor.getColumnIndex(COLUMN_GENRE));
                String author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR));
                Book book = new Book(title, pages, binding, genre, author);
                searchResults.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return searchResults;
    }
    public List<Rec> searchCommentsByTitleAndBook(String bookTitle) {
        List<Rec> searchResults = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_RECS_NAME, COLUMN_RECS_COMMENT, COLUMN_RECS_BOOK_TITLE};
        String selection = COLUMN_RECS_BOOK_TITLE + " = ?";
        String[] selectionArgs = {bookTitle};
        Cursor cursor = db.query(TABLE_RECS, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_RECS_NAME));
                String comment = cursor.getString(cursor.getColumnIndex(COLUMN_RECS_COMMENT));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_RECS_BOOK_TITLE));
                Rec rec = new Rec(name, comment, title);
                searchResults.add(rec);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return searchResults;
    }

    public boolean addUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_EMAIL, email);
        contentValues.put(COLUMN_USER_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1;
    }

    public boolean isUserExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + "=?";
        String[] selectionArgs = { email };
        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_EMAIL};
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }




}
