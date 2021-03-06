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
import android.widget.Toast;

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
    private DatabaseHelper myDb;

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
        final String title = data.get(position).title;
        final String url = data.get(position).url;
        final String thumbnailUrl = data.get(position).thumbnailUrl;


        holder.txtName.setText(title);
        Picasso.get().load(thumbnailUrl).into(holder.imgView);
        holder.imgViewFav.setImageResource(android.R.drawable.ic_delete);

        holder.imgViewFav.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View v) {
                    Context context = v.getContext();
                    myDb = new DatabaseHelper(context);
                    myDb.deleteOneRow(title);
                    data.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(v.getContext(), "Usunięto z ulubionych", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {

        return data.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtFavInfo;
        private ImageView imgView;
        private ImageView imgViewFav;
        private ConstraintLayout constraintLayoutContainer;

        ViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtFavInfo = itemView.findViewById(R.id.textViewInfoFav);
            imgView = itemView.findViewById(R.id.imageView);
            imgViewFav = itemView.findViewById(R.id.imageViewFav);

            constraintLayoutContainer = itemView.findViewById(R.id.constraintLayout);
            constraintLayoutContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickListener.launchIntent(
                            data.get(getAdapterPosition()).title,
                            data.get(getAdapterPosition()).thumbnailUrl,
                            data.get(getAdapterPosition()).url);

                }
            });
        }
    }

    public interface ClickListener {

        void launchIntent(String title, String thumbnailUrl, String url);

    }

    public void setData(List<Photos> data) {

        this.data.addAll(data);
        notifyDataSetChanged();

    }



}
