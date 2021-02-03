package com.example.seriestracker.details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seriestracker.R;
import com.example.seriestracker.model.UserDataWithKey;
import com.example.seriestracker.utils.GlobalValues;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {
    private final List<UserDataWithKey> userData;
    private final Context context;

    public DetailsAdapter(List<UserDataWithKey> userData, Context context) {
        this.userData = userData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDataWithKey data = userData.get(position);
        if (data.getSeen()) {
            holder.ibSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_check_24));
        } else {
            holder.ibSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_seen));
        }

        holder.ibSeen.setOnClickListener(v -> {
            if (data.getSeen()) {
                holder.ibSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_seen));
                userData.get(position).setSeen(false);
                data.setSeen(false);
            } else {
                holder.ibSeen.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_check_24));
                userData.get(position).setSeen(true);
                data.setSeen(true);
            }
        });

        if (data.getLiked()) {
            holder.ibLiked.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart_simple_shape_silhouette));
        } else {
            holder.ibLiked.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart));
        }

        holder.ibLiked.setOnClickListener(v -> {
            if (data.getLiked()) {
                holder.ibLiked.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart));
                userData.get(position).setLiked(false);
                data.setLiked(false);
            } else {
                holder.ibLiked.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart_simple_shape_silhouette));
                userData.get(position).setLiked(true);
                data.setLiked(true);
            }
        });

        holder.tvTitle.setText(data.getName());

        if (data.getImage() != null) {
            String URL = GlobalValues.BASE_URL_IMAGE.concat(data.getImage());
            Glide.with(context).load(URL).placeholder(R.drawable.placeholder).into(holder.ivCover);
        }
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivCover;
        private final TextView tvTitle;
        private final ImageButton ibLiked;
        private final ImageButton ibSeen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCover = itemView.findViewById(R.id.imageViewCircleEpisodeCover);
            tvTitle = itemView.findViewById(R.id.textViewTitle);
            ibLiked = itemView.findViewById(R.id.imageButtonLiked);
            ibSeen = itemView.findViewById(R.id.imageButtonStatus);
        }
    }
}
