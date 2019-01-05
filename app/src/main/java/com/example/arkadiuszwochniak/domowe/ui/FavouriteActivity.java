package com.example.arkadiuszwochniak.domowe.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.arkadiuszwochniak.domowe.R;
import com.example.arkadiuszwochniak.domowe.di.qualifier.ApplicationContext;

import javax.inject.Inject;

public class FavouriteActivity extends AppCompatActivity {

    Button button;

    @Inject
    @ApplicationContext
    public Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

    button = findViewById(R.id.butto);

    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    }
}
