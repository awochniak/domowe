package com.example.arkadiuszwochniak.domowe.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import com.example.arkadiuszwochniak.domowe.MyApplication;
import com.example.arkadiuszwochniak.domowe.R;
import com.example.arkadiuszwochniak.domowe.di.component.ApplicationComponent;
import com.example.arkadiuszwochniak.domowe.di.component.DaggerDetailActivityComponent;
import com.example.arkadiuszwochniak.domowe.di.component.DetailActivityComponent;
import com.example.arkadiuszwochniak.domowe.di.qualifier.ApplicationContext;
import com.example.arkadiuszwochniak.domowe.objects.Photos;
import com.example.arkadiuszwochniak.domowe.objects.PhotosDetails;
import com.example.arkadiuszwochniak.domowe.retrofit.APIInterface;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textView = findViewById(R.id.textView);

        String url = getIntent().getStringExtra("url");

        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        detailActivityComponent = DaggerDetailActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .build();

        detailActivityComponent.inject(this);

        apiInterface.getFilmData(url, "json").enqueue(new Callback<List<PhotosDetails>>() {
            @Override
            public void onResponse(Call<List<PhotosDetails>> call, Response<List<PhotosDetails>> response) {

                List<PhotosDetails> photographs = response.body();
                String[] titles = new String [photographs.size()];

                for  (int i = 0; i<photographs.size();i++) {

                    titles[i] = photographs.get(i).getTitleDetail();
                    textView.setText(titles[i]);

                }
            }

            @Override
            public void onFailure(Call<List<PhotosDetails>> call, Throwable t) {

            }
        });
    }
}