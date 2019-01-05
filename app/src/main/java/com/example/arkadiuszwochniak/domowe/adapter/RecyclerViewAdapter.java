package com.example.arkadiuszwochniak.domowe.adapter;


import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arkadiuszwochniak.domowe.R;
import com.example.arkadiuszwochniak.domowe.objects.Photos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Photos> data;
    private RecyclerViewAdapter.ClickListener clickListener;
    private Boolean flag = true;

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
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.txtName.setText(data.get(position).title);
        Picasso.get().load(data.get(position).thumbnailUrl).into(holder.imgView);
        holder.imgViewFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == true) {
                    holder.imgViewFav.setImageResource(android.R.drawable.btn_star_big_off);
                    flag = false;
                } else {
                    holder.imgViewFav.setImageResource(android.R.drawable.btn_star_big_on);
                    flag = true;
                }
            }
        });

        }

    @Override
    public int getItemCount() {

        return data.size();

    }

    public Object getItem(int position){

        return (data.get(position));

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
