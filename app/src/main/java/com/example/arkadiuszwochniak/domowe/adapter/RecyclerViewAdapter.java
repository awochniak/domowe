package com.example.arkadiuszwochniak.domowe.adapter;


import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arkadiuszwochniak.domowe.R;
import com.example.arkadiuszwochniak.domowe.di.module.DatabaseHelper;
import com.example.arkadiuszwochniak.domowe.di.qualifier.ActivityContext;
import com.example.arkadiuszwochniak.domowe.di.qualifier.ApplicationContext;
import com.example.arkadiuszwochniak.domowe.objects.Photos;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Photos> data;
    private RecyclerViewAdapter.ClickListener clickListener;
    private Boolean flag = true;
    private DatabaseHelper myDb;
    private static final String IMAGE_RESOURCE = "image-resource";

    @Inject
    @ApplicationContext
    public Context mContext;

    @Inject
    @ActivityContext
    public Context activityContext;

    @Inject
    public RecyclerViewAdapter(ClickListener clickListener) {

        this.clickListener = clickListener;
        data = new ArrayList<>();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_row, parent, false));

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        final Integer pos = position;
        final Integer photo;
        final String title = data.get(position).title;
        final String url = data.get(position).thumbnailUrl;

        holder.txtName.setText(title);
        Picasso.get().load(url).into(holder.imgView);

        photo = checkFav(title,holder.imgViewFav.getContext());
        holder.imgViewFav.setImageResource(photo);


        holder.imgViewFav.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {

                Context context = v.getContext();
                myDb = new DatabaseHelper(context);
                Cursor res = myDb.getOneRow(title);

                if (res.getCount() == 0) {
                    myDb.insertData(title, url, url);
                    holder.imgViewFav.setImageResource(android.R.drawable.btn_star_big_on);

                    Picasso.get().load(url).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            saveToInternalStorage(bitmap, v.getContext(), title);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });

                } else {
                    myDb.deleteOneRow(title);
                    holder.imgViewFav.setImageResource(android.R.drawable.btn_star_big_off);
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return data.size();

    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private ImageView imgView;
        private ImageView imgViewFav;
        private ConstraintLayout constraintLayoutContainer;

        ViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            imgView = itemView.findViewById(R.id.imageView);
            imgViewFav = itemView.findViewById(R.id.imageViewFav);

            constraintLayoutContainer = itemView.findViewById(R.id.constraintLayout);

            constraintLayoutContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickListener.launchIntent(
                            data.get(getAdapterPosition()).title,
                            data.get(getAdapterPosition()).url);

                }
            });
        }
    }

    public interface ClickListener {

        void launchIntent(String title, String url);

    }

    public void setData(List<Photos> data) {

        this.data.addAll(data);
        notifyDataSetChanged();

    }

    public int checkFav(String title, Context c){

        myDb = new DatabaseHelper(c);
        Cursor res = myDb.getOneRow(title);
        if (res.getCount() == 0) {
            int photo = android.R.drawable.btn_star_big_off;
            return photo;
        } else
        {
            int photo = android.R.drawable.btn_star_big_on;
            return photo;
        }

    }

    public String saveToInternalStorage(Bitmap bitmapImage, Context c, String fileName){

        ContextWrapper cw = new ContextWrapper(c);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, fileName+".jpg");

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  directory.getAbsolutePath();
    }

}

