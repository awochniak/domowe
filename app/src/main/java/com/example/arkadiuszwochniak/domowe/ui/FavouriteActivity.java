package com.example.arkadiuszwochniak.domowe.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arkadiuszwochniak.domowe.R;
import com.example.arkadiuszwochniak.domowe.di.module.DatabaseHelper;
import com.example.arkadiuszwochniak.domowe.di.qualifier.ApplicationContext;

import javax.inject.Inject;

public class FavouriteActivity extends AppCompatActivity {

    Button button,delAll;
    TextView textView;
    Cursor res;
    DatabaseHelper myDb;
    RecyclerView recyclerView;


    @Inject
    @ApplicationContext
    public Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        myDb = new DatabaseHelper(this);

        Cursor res = myDb.getAllData();
        if(res.getCount() == 0){
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            buffer.append("Id :"+ res.getString(0)+"\n");
            buffer.append("Title :"+ res.getString(1)+"\n");
            buffer.append("Thumbnail url :"+ res.getString(2)+"\n");
            buffer.append("Url :"+ res.getString(3)+"\n");
        }

    textView = findViewById(R.id.textView3);
    textView.setText(buffer);

    delAll = findViewById(R.id.buttonDelAll);

    delAll.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        myDb.dropTable();
        textView.setText("Nie masz ulubionych pozycji!");
        Toast.makeText(getBaseContext(),"Usunięto wszystkie ulubione wpisy", Toast.LENGTH_LONG);
        }
    });

    button = findViewById(R.id.butto);

    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            navigateUpTo(new Intent(getBaseContext(), MainActivity.class));
        }
    });
    }


}




