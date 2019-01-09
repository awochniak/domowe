package com.example.arkadiuszwochniak.domowe.ui;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.arkadiuszwochniak.domowe.MyApplication;
import com.example.arkadiuszwochniak.domowe.R;
import com.example.arkadiuszwochniak.domowe.di.component.ApplicationComponent;
import com.example.arkadiuszwochniak.domowe.di.component.DaggerDetailActivityComponent;
import com.example.arkadiuszwochniak.domowe.di.component.DetailActivityComponent;
import com.example.arkadiuszwochniak.domowe.di.module.DatabaseHelper;
import com.example.arkadiuszwochniak.domowe.di.module.ImageHelper;
import com.example.arkadiuszwochniak.domowe.di.qualifier.ApplicationContext;
import com.example.arkadiuszwochniak.domowe.objects.Photos;
import com.example.arkadiuszwochniak.domowe.retrofit.APIInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.arkadiuszwochniak.domowe.di.module.ImageHelper.checkPhotoExist;

public class DetailActivity extends AppCompatActivity {

    DetailActivityComponent detailActivityComponent;
    DatabaseHelper myDb;

    @Inject
    public APIInterface apiInterface;

    @Inject
    @ApplicationContext
    public Context mContext;

    TextView textView;
    ImageView imgView;
    Button buttonReturn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        myDb = new DatabaseHelper(this);

        textView = findViewById(R.id.textView);
        imgView = findViewById(R.id.imageView2);
        buttonReturn = findViewById(R.id.buttonReturn);

        final String title = getIntent().getStringExtra("title");
        final String url = getIntent().getStringExtra("url");
        final String path;
      //  Picasso.get().load(url).fetch();

        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        detailActivityComponent = DaggerDetailActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .build();

        detailActivityComponent.inject(this);

                path = checkPhotoExist(title, url, myDb,getBaseContext());
                Picasso.get().load(path).into(imgView);
                textView.setText(title);


        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}