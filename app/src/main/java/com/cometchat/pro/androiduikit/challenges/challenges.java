package com.cometchat.pro.androiduikit.challenges;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cometchat.pro.androiduikit.Helpers.MyAdapter;
import com.cometchat.pro.androiduikit.R;

public class challenges extends AppCompatActivity {

    RecyclerView recyclerView;
    challengesAdapter challengesAdapter;

    String s1[], s2[];
    int images[] = {R.drawable.makatimandaluyongedsa, R.drawable.mandaluyongcircle10km};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        recyclerView = findViewById(R.id.challenges_rv);
        setTitle("Challenges");
        s1 = getResources().getStringArray(R.array.challenges);
        s2 = getResources().getStringArray(R.array.challenges_description);

        challengesAdapter = new challengesAdapter(this, s1, s2, images);
        recyclerView.setAdapter(challengesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.challengess_search, menu);
        MenuItem item = menu.findItem(R.id.search_challenge);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                challengesAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}