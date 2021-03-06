package com.example.arkadiuszwochniak.domowe.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arkadiuszwochniak.domowe.MyApplication;
import com.example.arkadiuszwochniak.domowe.R;
import com.example.arkadiuszwochniak.domowe.adapter.RecyclerViewAdapter;
import com.example.arkadiuszwochniak.domowe.adapter.RecyclerViewAdapterFav;
import com.example.arkadiuszwochniak.domowe.di.component.ApplicationComponent;
import com.example.arkadiuszwochniak.domowe.di.component.DaggerFavouriteActivityComponent;
import com.example.arkadiuszwochniak.domowe.di.component.DaggerMainActivityComponent;
import com.example.arkadiuszwochniak.domowe.di.component.FavouriteActivityComponent;
import com.example.arkadiuszwochniak.domowe.di.module.DatabaseHelper;
import com.example.arkadiuszwochniak.domowe.di.module.FavouriteActivityContextModule;
import com.example.arkadiuszwochniak.domowe.di.module.MainActivityContextModule;
import com.example.arkadiuszwochniak.domowe.di.qualifier.ActivityContext;
import com.example.arkadiuszwochniak.domowe.di.qualifier.ApplicationContext;
import com.example.arkadiuszwochniak.domowe.objects.Photos;
import com.example.arkadiuszwochniak.domowe.retrofit.APIInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteActivity extends AppCompatActivity implements RecyclerViewAdapterFav.ClickListener {

    TextView textViewFavInfo;
    Button button,delAll;
    Cursor res;
    DatabaseHelper myDb;
    RecyclerView recyclerView;
    FavouriteActivityComponent favouriteActivityComponent;
    List<Photos> photogs;

    @Inject
    public RecyclerViewAdapterFav recyclerViewAdapterFav;

    @Inject
    public APIInterface apiInterface;

    @Inject
    @ApplicationContext
    public Context mcontext;

    @Inject
    @ActivityContext
    public Context activityContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        myDb = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerViewFav);
        recyclerView.setLayoutManager(new LinearLayoutManager(FavouriteActivity.this));

        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        favouriteActivityComponent = DaggerFavouriteActivityComponent.builder()
                .favouriteActivityContextModule(new FavouriteActivityContextModule(this))
                .applicationComponent(applicationComponent)
                .build();

        favouriteActivityComponent.injectFavouriteActivity(this);
        recyclerView.setAdapter(recyclerViewAdapterFav);

        textViewFavInfo = findViewById(R.id.textViewInfoFav);


        res = myDb.getAllData();
        photogs = new ArrayList<>();

        while(res.moveToNext()){
            Photos photo = new Photos(res.getString(1),res.getString(2),res.getString(3));
            photogs.add(photo);
        }

        if(photogs.size()!=0){
            textViewFavInfo.setVisibility(View.GONE);
        }

        populateRecyclerView(photogs);

        delAll = findViewById(R.id.buttonDelAll);

        delAll.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        myDb.dropTable();
        recyclerView.setVisibility(View.GONE);
        textViewFavInfo.setVisibility(View.VISIBLE);
        Toast.makeText(getBaseContext(), "Usunięto wszystkie ulubione cytaty :(", Toast.LENGTH_LONG).show();
        }

    });

    button = findViewById(R.id.butto);

    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    });
    }

    private void populateRecyclerView(List<Photos> response) {
        recyclerViewAdapterFav.setData(photogs);
    }


    @Override
    public void launchIntent(String title, String thumbnailUrl, String url) {
        startActivity(new Intent(activityContext, DetailActivity.class).
                putExtra("title", title).
                putExtra("thumbnailUrl", thumbnailUrl).
                putExtra("url", url));
    }
}




