package com.example.seriestracker.home;

import android.app.Activity;
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
import com.example.seriestracker.model.UserDataWithKey;
import com.example.seriestracker.utils.ActivityManager;
import com.example.seriestracker.utils.GlobalValues;

import java.util.ArrayList;
import java.util.List;

public class SeriesCardAdapter extends RecyclerView.Adapter<SeriesCardAdapter.ViewHolder> {
    private final List<TvShow> tvShows;
    private final List<UserDataWithKey> userData;
    private final Context context;
    private final Activity activity;
    private OnItemClickListener listener;

    public SeriesCardAdapter(List<TvShow> tvShows, List<UserDataWithKey> userData, Activity activity, Context context) {
        this.tvShows = tvShows;
        this.context = context;
        this.userData = userData;
        this.activity = activity;
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
        List<UserDataWithKey> data = getFilteredList(show.getDbId());

        if (!show.getImage().isEmpty()) {
            String URL = GlobalValues.BASE_URL_IMAGE.concat(show.getImage());
            Glide.with(context).load(URL).into(holder.ivCover);
        } else {
            holder.ivCover.setImageResource(R.drawable.placeholder);
        }

        for (UserDataWithKey ud : data) {
            if (!ud.getSeen()) {
                String status = context.getResources().getString(R.string.season_init)
                        .concat(String.valueOf(ud.getSeasonNumber()))
                        .concat(context.getResources().getString(R.string.space))
                        .concat(context.getResources().getString(R.string.episode_init))
                        .concat(String.valueOf(ud.getEpisodeNumber()));
                holder.tvStatus.setText(status);
                holder.tvContinue.setVisibility(View.VISIBLE);
                break;
            }
            if (ud.getKey().equals(data.get(data.size() - 1).getKey())) {
                holder.tvStatus.setText(R.string.waiting_for_new_season);
                holder.tvContinue.setVisibility(View.INVISIBLE);
            }
        }

        holder.tvTitle.setText(show.getName());

        holder.ibDetails.setOnClickListener(v -> ActivityManager.startDetailsActivity(activity, show, userData));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private List<UserDataWithKey> getFilteredList(int id) {
        List<UserDataWithKey> data = new ArrayList<>();

        for (UserDataWithKey ud : userData) {
            if (ud.getDbId() == id) {
                data.add(ud);
            }
        }

        data.sort((o1, o2) -> {
            Integer x1 = o1.getSeasonNumber();
            Integer x2 = o2.getSeasonNumber();

            return x1.compareTo(x2);
        });

        return data;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivCover;
        private final TextView tvTitle;
        private final TextView tvStatus;
        private final TextView tvContinue;
        private final ImageButton ibDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.imageViewCover);
            tvTitle = itemView.findViewById(R.id.textViewTitle);
            tvStatus = itemView.findViewById(R.id.textViewStatus);
            tvContinue = itemView.findViewById(R.id.textViewContinueWatching);
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
