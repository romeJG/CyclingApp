package com.cometchat.pro.androiduikit.Views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cometchat.pro.androiduikit.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        com.cometchat.pro.androiduikit.Views.CyclingDataTips[] cyclingDatumTips = new  com.cometchat.pro.androiduikit.Views.CyclingDataTips[]{

                new  com.cometchat.pro.androiduikit.Views.CyclingDataTips("CALORIES BURNED (WHAT TO EAT?)","1. Oats\n" +
                        "2. Apples\n" +
                        "3. Green Tea\n" +
                        "4. Eggs\n" +
                        "5. Cinnamon\n" +
                        "6. Chilli Peppers\n" +
                        "7. Blueberries\n" +
                        "8. Greek Yoghurt\n" +
                        "9. Coconut Oil\n" +
                        "10. Dark Chocolate",R.drawable.calories),
                new  com.cometchat.pro.androiduikit.Views.CyclingDataTips("WARM-UP BEFORE CYCLING","Warming up prior to cycling can increase your endurance & stamina, allowing for better results when it comes to the fitness side of the activity." +
                        "remaining essentially unchanged.",R.drawable.warmup),
                new  com.cometchat.pro.androiduikit.Views.CyclingDataTips("REFRESH YOUR SELF BY DRINKING WATER","Proper cycling hydration should include drinking small amounts every 10 to 15 minutes during your workout. " +
                        "This helps replenish the water you lose through sweat, keeps you focused, and improves stamina.",R.drawable.drink),
                new  com.cometchat.pro.androiduikit.Views.CyclingDataTips("ANGINA ","Unexplained Chest, Neck or Arm Pain When You Ride a Bicycle or Exercise. " +
                        "If you develop repeated chest, jaw, arm, or neck pain when you exercise, you could have angina, pain caused by reduced blood flow through narrowed arteries leading to your heart. " +
                        "You should check with a doctor as soon as possible.",R.drawable.angina),

                new  com.cometchat.pro.androiduikit.Views.CyclingDataTips("BE READY BEFORE RIDING","1. Pump your tyres to the right pressure.\n2. Get your saddle height and riding position just right.\n" +
                        "3. Take water and food.\n" +
                        "4. Have a saddle pack with tools, spares and cash.\n" +
                        "5. Cycling gloves, padded shorts and mitts.",R.drawable.ready),
        };

        com.cometchat.pro.androiduikit.Views.CyclingAdapter cyclingAdapter = new  com.cometchat.pro.androiduikit.Views.CyclingAdapter(cyclingDatumTips,MainActivity.this);
        recyclerView.setAdapter(cyclingAdapter);

    }
}
