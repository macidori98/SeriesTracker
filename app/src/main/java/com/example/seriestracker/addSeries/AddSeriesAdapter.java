package com.example.seriestracker.addSeries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seriestracker.R;
import com.example.seriestracker.model.SearchSeries;
import com.example.seriestracker.utils.GlobalValues;

import java.util.List;

public class AddSeriesAdapter extends RecyclerView.Adapter<AddSeriesAdapter.ViewHolder> {

    private final List<SearchSeries> seriesList;
    private Context context;

    public AddSeriesAdapter(List<SearchSeries> seriesList, Context context) {
        this.seriesList = seriesList;
        this.context = context;
    }

    @NonNull
    @Override
    public AddSeriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_add_series, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddSeriesAdapter.ViewHolder holder, int position) {
        SearchSeries series = seriesList.get(position);
        if (series.getImage()!=null) {
            String URL = GlobalValues.BASE_URL_IMAGE.concat(series.getImage());
            Glide.with(context).load(URL).into(holder.ivCover);
        }
        holder.tvTitle.setText(series.getName());
    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivCover;
        private TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.imageViewCover);
            tvTitle = itemView.findViewById(R.id.textViewTitle);
        }
    }
}
