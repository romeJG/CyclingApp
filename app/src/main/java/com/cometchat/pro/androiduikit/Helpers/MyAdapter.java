package com.cometchat.pro.androiduikit.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cometchat.pro.androiduikit.R;
import com.cometchat.pro.androiduikit.Retrofit.Scientist;
import com.cometchat.pro.androiduikit.Views.DetailActivity;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is our adapter class. It has the following roles;
 * 1. Inflate our model layout into a view then subsequently recycle that view.
 * 2. Bind data to that view for all rows, making our recyclerview.
 * 3. Show name initials in icons with random icon bg color applied.
 * 4. Listen to click events of recyclerview item and pass the clicked item to recyclerview
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context c;
    private int[] mMaterialColors;
    private List<Scientist> scientists;
    public String searchString = "";

    /**
     * Our ViewHolder class. It's responsibilities include:
     * 1. Hold all the widgets which will be recycled and reference them.
     * 2. Implement click event.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        private TextView nameTxt, mDescriptionTxt, galaxyTxt;
        private MaterialLetterIcon mIcon;
        private ItemClickListener itemClickListener;
        /**
         * We reference our widgets
         */
        ViewHolder(View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.mNameTxt);
            mDescriptionTxt = itemView.findViewById(R.id.mDescriptionTxt);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }

        void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }

    /**
     * Our MyAdapter's costructor
     */
    public MyAdapter(ArrayList<Scientist> scientists) {
        this.scientists = scientists;
    }
    /**
     * We override the onCreateViewHolder. Here is where we inflate our model.xml
     * layout into a view object and set it's background color
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.c=parent.getContext();
        mMaterialColors = c.getResources().getIntArray(R.array.colors);
        View view = LayoutInflater.from(c).inflate(R.layout.model, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    /**
     * Our onBindViewHolder method
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get current scientist
        final Scientist s = scientists.get(position);

        //bind data to widgets

        holder.nameTxt.setText(s.getGroup_name());
        holder.mDescriptionTxt.setText(s.getAdmin());


        //open detail activity when clicked
        holder.setItemClickListener(pos -> Utils.sendScientistToActivity(c, s,
                DetailActivity.class));
    }
    @Override
    public int getItemCount() {
        return scientists.size();
    }
    interface ItemClickListener {
        void onItemClick(int pos);
    }
}
//end
