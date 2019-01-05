package com.example.arkadiuszwochniak.domowe.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.arkadiuszwochniak.domowe.MyApplication;
import com.example.arkadiuszwochniak.domowe.R;
import com.example.arkadiuszwochniak.domowe.di.component.ApplicationComponent;
import com.example.arkadiuszwochniak.domowe.di.component.DaggerDetailActivityComponent;
import com.example.arkadiuszwochniak.domowe.di.component.DetailActivityComponent;
import com.example.arkadiuszwochniak.domowe.di.qualifier.ApplicationContext;
import com.example.arkadiuszwochniak.domowe.objects.Photos;
import com.example.arkadiuszwochniak.domowe.retrofit.APIInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    DetailActivityComponent detailActivityComponent;

    @Inject
    public APIInterface apiInterface;

    @Inject
    @ApplicationContext
    public Context mContext;

    TextView textView;
    ImageView imgView, addFavourite;
    Button buttonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textView = findViewById(R.id.textView);
        imgView = findViewById(R.id.imageView2);
        buttonReturn = findViewById(R.id.buttonReturn);
        addFavourite = findViewById(R.id.imageViewFav);

        final String title = getIntent().getStringExtra("title");
        final String url = getIntent().getStringExtra("url");

        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        detailActivityComponent = DaggerDetailActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .build();

        detailActivityComponent.inject(this);

        apiInterface.getPeople("json").enqueue(new Callback<List<Photos>>() {
            @Override
            public void onResponse(Call<List<Photos>> call, Response<List<Photos>> response) {

                textView.setText(title);
                Picasso.get().load(url).into(imgView);


            }

            @Override
            public void onFailure(Call<List<Photos>> call, Throwable t) {

            }
        });

       buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}