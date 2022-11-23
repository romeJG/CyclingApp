package com.cometchat.pro.androiduikit.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cometchat.pro.androiduikit.R;

public class CyclingAdapter extends RecyclerView.Adapter<CyclingAdapter.ViewHolder> {

    CyclingDataTips[] cyclingDatumTips;
    Context context;

    public CyclingAdapter(CyclingDataTips[] cyclingDatumTips, MainActivity activity) {
        this.cyclingDatumTips = cyclingDatumTips;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CyclingDataTips cyclingDataTipsList = cyclingDatumTips[position];
        holder.textViewName.setText(cyclingDataTipsList.getMovieName());
        holder.textViewDate.setText(cyclingDataTipsList.getMovieDate());
        holder.movieImage.setImageResource(cyclingDataTipsList.getMovieImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, cyclingDataTipsList.getMovieName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cyclingDatumTips.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView movieImage;
        TextView textViewName;
        TextView textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.imageview);
            textViewName = itemView.findViewById(R.id.textName);
            textViewDate = itemView.findViewById(R.id.textdate);

        }
    }

}
