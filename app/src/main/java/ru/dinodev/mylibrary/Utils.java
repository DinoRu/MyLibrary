package ru.dinodev.mylibrary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String ALL_BOOKS_KEY = "all_book";
    private static final String ALREADY_READ_BOOKS_KEY = "already_read_books";
    private static final String CURRENTLY_READING_BOOKS_KEY = "currently_reading_books";
    private static final String WANT_TO_READ_BOOKS_KEY = "want_to_read_books";
    private static final String FAVORITE_BOOKS_KEY = "favorite_books";

    private static Utils instance;
    private SharedPreferences sharedPreferences;

   /*
     private static ArrayList<Book> allBooks;
    private static ArrayList<Book> alreadyReadBooks;
    private static ArrayList<Book> wantToReadBooks;
    private static ArrayList<Book> currentlyReadingBooks;
     private static ArrayList<Book> favoriteBooks;
    */

    public Utils(Context context) {
        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);
        if(null == getAllBooks()){
            initData();
        }

        SharedPreferences.Editor editor= sharedPreferences.edit();
        Gson gson = new Gson();

        if (null == getAlreadyReadBooks()){
            editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(new ArrayList<>()));
            editor.commit();
        }

        if(null == getWantToReadBooks()){
            editor.putString(WANT_TO_READ_BOOKS_KEY, gson.toJson(new ArrayList<>()));
            editor.commit();
        }
        if(null == getCurrentlyReadingBooks()){
            editor.putString(CURRENTLY_READING_BOOKS_KEY, gson.toJson(new ArrayList<>()));
            editor.commit();
        }

        if(null == getFavoriteBooks()){
            editor.putString(FAVORITE_BOOKS_KEY, gson.toJson(new ArrayList<>()));
            editor.commit();
        }
    }

    private void initData() {

        ArrayList<Book> books = new ArrayList<>();
        //TODO: add initial data
        books.add(new Book(1, "12 regles pour vie", "Jordan Peterson", 512,
                "data:image/jpeg;base64,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",
                "Un antidote au chaos", "L'un des plus importants penseur actuel"));
        books.add(new Book(2, "L\'idee de justice", "Amartya Sen", 432,
                "https://images.epagine.fr/373/9782081392373_1_75.jpg", "Short Description", "Long Description"));
        books.add(new Book(3, "21 Léçons pour le 21 siecle", "Yuval Noah Harrari", 546, "https://thepierceinstitute.com/wp-content/uploads/2019/07/71t0ZvJ9GhL.jpg",
                "Un bref aperçu de l'avenir", "Longue description"));
        books.add(new Book(4, "Clean Code", "Robert Martin", 690,
                "https://ebook-mania.net/wp-content/uploads/2020/05/clean_code__guida_per_diventare_bravi_artigiani_nello_sviluppo_agile_di_software_-_robert_c._martin.jpg",
                "Short Description", "Longue Description"));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
        editor.commit();


    }


    public static Utils getInstance(Context context) {

        if (null == instance) {
            instance = new Utils(context);
        }
        return instance;
    }

    public ArrayList<Book> getAllBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getAlreadyReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS_KEY, null), type);
        return books;
    }

    public  ArrayList<Book> getWantToReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WANT_TO_READ_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getCurrentlyReadingBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS_KEY, null), type);
        return books;
    }

    public  ArrayList<Book> getFavoriteBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVORITE_BOOKS_KEY, null), type);
        return books;
    }

    public Book getBookById(int id){

        ArrayList<Book> books = getAllBooks();
        if(null != books){
            for(Book b: books){
                if(b.getId() == id){
                    return b;
                }
            }
        }

        return null;
    }

    public boolean addToAlreadyRead(Book book){
       ArrayList<Book> books = getAlreadyReadBooks();
       if(null != books){
           if(books.add(book)){
               Gson gson = new Gson();
               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.remove(ALREADY_READ_BOOKS_KEY);
               editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(books));
               editor.commit();
               return true;
           }
       }
       return false;
    }

    public boolean addToWantToRead(Book book){
        ArrayList<Book> books = getWantToReadBooks();
        if(null != books){
            if(books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(WANT_TO_READ_BOOKS_KEY);
                editor.putString(WANT_TO_READ_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToFavoriteBooks(Book book){
        ArrayList<Book> books = getFavoriteBooks();
        if(null != books){
            if(books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVORITE_BOOKS_KEY);
                editor.putString(FAVORITE_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToCurrentlyReadingBooks(Book book){
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if(null != books){
            if(books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(CURRENTLY_READING_BOOKS_KEY);
                editor.putString(CURRENTLY_READING_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean removeFromToAlreadyBook(Book book){
        ArrayList<Book> books = getAlreadyReadBooks();
        if(null != books){
            for (Book b: books){
                if (b.getId() == book.getId()){
                    if (books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALREADY_READ_BOOKS_KEY);
                        editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromWantToRead(Book book) {
        ArrayList<Book> books = getWantToReadBooks();
        if(null != books){
            for (Book b: books){
                if (b.getId() == book.getId()){
                    if (books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(WANT_TO_READ_BOOKS_KEY);
                        editor.putString(WANT_TO_READ_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromToCurrentlyReadingBooks(Book book){
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if(null != books){
            for (Book b: books){
                if (b.getId() == book.getId()){
                    if (books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(CURRENTLY_READING_BOOKS_KEY);
                        editor.putString(CURRENTLY_READING_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromToFavoriteBooks(Book book){
        ArrayList<Book> books = getFavoriteBooks();
        if(null != books){
            for (Book b: books){
                if (b.getId() == book.getId()){
                    if (books.remove(b)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVORITE_BOOKS_KEY);
                        editor.putString(FAVORITE_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
