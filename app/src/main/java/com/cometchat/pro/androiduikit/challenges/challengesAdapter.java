package com.cometchat.pro.androiduikit.challenges;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cometchat.pro.androiduikit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class challengesAdapter extends RecyclerView.Adapter<challengesAdapter.challengesViewholder> implements Filterable {

    String data1[], data2[];
    int images[];
    Context context;
    List<String> titleList;


    public challengesAdapter(Context ct, String s1[], String s2[], int img[]){
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;
        titleList = new ArrayList<>(Arrays.asList(s1));
    }

    @NonNull
    @Override
    public challengesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.challenges_item, parent, false);
        return new challengesViewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull challengesViewholder holder, int position) {
        holder.title.setText(data1[position]);
        holder.description.setText(data2[position]);
        holder.img.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }



    Filter filter = new Filter() {

        //runs on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<String> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()){
                filteredList.addAll(titleList);
                Log.d("logDDD: ", titleList.toString());
            }else{
                for (String i: titleList){
                    if (i.toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(i);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;


            return filterResults;
        }
        //runs on ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            titleList.clear();
            titleList.addAll((Collection<? extends String>) filterResults.values);
            Log.d("logDDD 2: ", titleList.toString());
            notifyDataSetChanged();
        }
    };
    @Override
    public Filter getFilter() {

        return filter;
    }

    public class challengesViewholder extends RecyclerView.ViewHolder {

        TextView title, description;
        ImageView img;

        public challengesViewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_id);
            description = itemView.findViewById(R.id.description_id);
            img = itemView.findViewById(R.id.challenge_image);

        }
    }


}
