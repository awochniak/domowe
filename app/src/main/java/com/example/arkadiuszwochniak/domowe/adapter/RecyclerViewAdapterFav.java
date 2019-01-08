package com.example.arkadiuszwochniak.domowe.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arkadiuszwochniak.domowe.R;
import com.example.arkadiuszwochniak.domowe.di.module.DatabaseHelper;
import com.example.arkadiuszwochniak.domowe.di.qualifier.ActivityContext;
import com.example.arkadiuszwochniak.domowe.di.qualifier.ApplicationContext;
import com.example.arkadiuszwochniak.domowe.objects.Photos;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RecyclerViewAdapterFav extends RecyclerView.Adapter<RecyclerViewAdapterFav.ViewHolder> {

    private List<Photos> data;
    private RecyclerViewAdapterFav.ClickListener clickListener;
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
    public RecyclerViewAdapterFav(RecyclerViewAdapterFav.ClickListener clickListener) {

        this.clickListener = clickListener;
        data = new ArrayList<>();

    }


    @Override
    public RecyclerViewAdapterFav.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new RecyclerViewAdapterFav.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_row, parent, false));

    }


    @Override
    public void onBindViewHolder(final RecyclerViewAdapterFav.ViewHolder holder, final int position) {


        final Integer pos = position;
        final Integer photo;
        final String title = data.get(position).title;
        final String url = data.get(position).thumbnailUrl;

        holder.txtName.setText(title);
        Picasso.get().load(url).into(holder.imgView);

        holder.imgViewFav.setImageResource(android.R.drawable.btn_star_big_on);


        holder.imgViewFav.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {

                Context context = v.getContext();
                myDb = new DatabaseHelper(context);
                Cursor res = myDb.getOneRow(title);

                if (res.getCount() == 0) {

                } else {
                    myDb.deleteOneRow(title);
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

}
