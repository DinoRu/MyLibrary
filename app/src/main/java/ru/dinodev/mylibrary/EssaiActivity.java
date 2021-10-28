package ru.dinodev.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class EssaiActivity extends AppCompatActivity {

    private TextView txtBookName, txtAuthor, txtPages, txtLongDesc;
    private Button btnCurrentlyRead, btnAlreadyRead, btnWantToRead, btnAddToFavorite;
    private ImageView bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essai);

        initViews();

        //TODO: GET RECYCLER VIEW ITEM
        Book book = new Book(2, "L\'idee de justice", "Amartya Sen", 432,
                "https://images.epagine.fr/373/9782081392373_1_75.jpg", "Short Description", "Long Description");
        setBook(book);

    }

    private void setBook(Book book) {
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtLongDesc.setText(book.getLongDesc());

        Glide.with(this)
                .asBitmap().load(book.getImageUrl())
                .into(bookImage);

    }

    private void initViews() {

        txtBookName = findViewById(R.id.textView2);
        txtAuthor = findViewById(R.id.textView4);
        txtPages = findViewById(R.id.textView6);
        txtLongDesc = findViewById(R.id.textView8);

        btnCurrentlyRead = findViewById(R.id.button1);
        btnWantToRead = findViewById(R.id.button2);
        btnAlreadyRead = findViewById(R.id.button3);
        btnAddToFavorite = findViewById(R.id.button4);

        bookImage = findViewById(R.id.imgView);
    }
}