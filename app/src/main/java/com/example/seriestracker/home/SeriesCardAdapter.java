package com.example.seriestracker.home;

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
import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.utils.GlobalValues;

import java.util.List;

public class SeriesCardAdapter extends RecyclerView.Adapter<SeriesCardAdapter.ViewHolder> {
    private final List<TvShow> tvShows;
    private final Context context;
    private OnItemClickListener listener;

    public SeriesCardAdapter(List<TvShow> tvShows, Context context) {
        this.tvShows = tvShows;
        this.context = context;
    }

    @NonNull
    @Override
    public SeriesCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_series, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesCardAdapter.ViewHolder holder, int position) {
        TvShow show = tvShows.get(position);

        if (!show.getImage().isEmpty()) {
            String URL = GlobalValues.BASE_URL_IMAGE.concat(show.getImage());
            Glide.with(context).load(URL).into(holder.ivCover);
        } else {
            holder.ivCover.setImageResource(R.drawable.placeholder);
        }

        holder.tvTitle.setText(show.getName());
        holder.tvStatus.setText("Under work");
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivCover;
        private final TextView tvTitle;
        private final TextView tvStatus;
        private final ImageButton ibDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.imageViewCover);
            tvTitle = itemView.findViewById(R.id.textViewTitle);
            tvStatus = itemView.findViewById(R.id.textViewStatus);
            ibDetails = itemView.findViewById(R.id.imageButtonDetails);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
