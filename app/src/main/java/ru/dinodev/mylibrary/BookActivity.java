package ru.dinodev.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    public static final String BOOK_ID_KEY = "bookId";

    private TextView txtBookName, textAuthorName, txtPages, txtDescription;
    private Button btnAddToCurrentlyReading, btnAddToWantToRead, btnAddToAlreadyReadList, btnAddToFavorite;
    private ImageView imageBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

     //   String longDesc = "Quatrième de couverture\n" +
     //   "Even bad code can function. But if code isn’t clean, it can bring a development organization to its knees. Every year, countless hours and significant resources are lost because of poorly written code. But it doesn’t have to be that way.\n" +
     //           "\n" +
     //           "Noted software expert Robert C. Martin presents a revolutionary paradigm with Clean Code: A Handbook of Agile Software Craftsmanship. Martin has teamed up with his colleagues from Object Mentor to distill their best agile practice of cleaning code “on the fly” into a book that will instill within you the values of a software craftsman and make you a better programmer―but only if you work at it.\n" +
     //          "\n" +
     //           "What kind of work will you be doing? You’ll be reading code―lots of code. And you will be challenged to think about what’s right about that code, and what’s wrong with it. More importantly, you will be challenged to reassess your professional values and your commitment to your craft.\n" +
     //           "\n" +
     //           "Clean Code is divided into three parts. The first describes the principles, patterns, and practices of writing clean code. The second part consists of several case studies of increasing complexity. Each case study is an exercise in cleaning up code―of transforming a code base that has some problems into one that is sound and efficient. The third part is the payoff: a single chapter containing a list of heuristics and “smells” gathered while creating the case studies. The result is a knowledge base that describes the way we think when we write, read, and clean code.\n" +
     //           "\n" +
     //           "Readers will come away from this book understanding\n" +
     //           "How to tell the difference between good and bad code\n" +
     //           "How to write good code and how to transform bad code into good code\n" +
     //           "How to create good names, good functions, good objects, and good classes\n" +
     //           "How to format code for maximum readability\n" +
      //          "How to implement complete error handling without obscuring code logic\n" +
      //          "How to unit test and practice test-driven development\n" +
       //         "This book is a must for any developer, software engineer, project manager, team lead, or systems analyst with an interest in producing better code.\n";

        // TODO: Get the data from recycler view in here
       // Book book = new Book(4, "Clean Code", "Robert Martin", 690,
        //        "https://ebook-mania.net/wp-content/uploads/2020/05/clean_code__guida_per_diventare_bravi_artigiani_nello_sviluppo_agile_di_software_-_robert_c._martin.jpg",
        //        "Short Description", longDesc);

        Intent intent = getIntent();
        if(null != intent){
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if(bookId != -1){
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if(null != incomingBook){
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToReadBooks(incomingBook);
                    handleFavoriteBooks(incomingBook);
                    handleCurrentlyReading(incomingBook);
                }
            }
        }

    }

    private void handleCurrentlyReading(final Book book) {
        ArrayList<Book> currentlyReadingBooks = Utils.getInstance(this).getCurrentlyReadingBooks();

        boolean existInCurrentlyReadingBooks = false;

        for(Book b: currentlyReadingBooks){
            if(b.getId() == book.getId()){
                existInCurrentlyReadingBooks  = true;
            }
        }
        if(existInCurrentlyReadingBooks ){
            btnAddToCurrentlyReading.setEnabled(false);
        }else {
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance(BookActivity.this).addToCurrentlyReadingBooks(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, CurrentlyReadingActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(BookActivity.this, "Something wrong, Try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }


    private void handleFavoriteBooks(final Book book) {
        ArrayList<Book> favoriteBooks = Utils.getInstance(this).getFavoriteBooks();

        boolean existInFavoriteBooks = false;

        for(Book b: favoriteBooks){
            if(b.getId() == book.getId()){
                existInFavoriteBooks  = true;
            }
        }
        if(existInFavoriteBooks ){
            btnAddToFavorite.setEnabled(false);
        }else {
            btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance(BookActivity.this).addToFavoriteBooks(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, FavoriteActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(BookActivity.this, "Something wrong, Try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }


    private void handleWantToReadBooks( final Book book) {

        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();

        boolean existInWantToReadBooks = false;

        for(Book b: wantToReadBooks){
            if(b.getId() == book.getId()){
                existInWantToReadBooks  = true;
            }
        }
        if(existInWantToReadBooks ){
            btnAddToWantToRead.setEnabled(false);
        }else {
            btnAddToWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance(BookActivity.this).addToWantToRead(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, WantToReadActivity.class);
                        startActivity(intent);


                    }else{
                        Toast.makeText(BookActivity.this, "Something wrong, Try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    /**
     * Enable and Diseable button,
     * Add the book to Already Read Book Arralist
     * @param book
     */


    private void handleAlreadyRead(final Book book) {
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();

        boolean existInAlreadyReadBooks = false;

        for(Book b: alreadyReadBooks){
            if(b.getId() == book.getId()){
                existInAlreadyReadBooks = true;
            }
        }
        if(existInAlreadyReadBooks){
            btnAddToAlreadyReadList.setEnabled(false);
        }else {
            btnAddToAlreadyReadList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance(BookActivity.this).addToAlreadyRead(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                        startActivity(intent);


                    }else{
                        Toast.makeText(BookActivity.this, "Something wrong, Try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void setData(Book book) {
        txtBookName.setText(book.getName());
        textAuthorName.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDescription.setText(book.getLongDesc());

        Glide.with(this)
                .asBitmap()
                .load(Uri.parse(book.getImageUrl()))
                .into(imageBook);

    }

    private void initViews() {
        txtBookName = findViewById(R.id.textBookName);
        textAuthorName = findViewById(R.id.txtAuthorName);
        txtPages = findViewById(R.id.txtPages);
        txtDescription = findViewById(R.id.txtDescription);

        btnAddToAlreadyReadList = findViewById(R.id.btnAlreadyReadList);
        btnAddToCurrentlyReading = (Button) findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToWantToRead = findViewById(R.id.btnAddToWantToRead);
        btnAddToFavorite = findViewById(R.id.btnAddToFavorite);

        imageBook = findViewById(R.id.imageBook);
    }
}