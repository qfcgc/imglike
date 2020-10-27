package com.example.imglike;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private final LinkedList<ImageDataWrapper> imagesList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private ImageListAdapter mAdapter;
    private ImageLoader imageLoader;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Получаем ViewPager и устанавливаем в него адаптер
//        ViewPager viewPager = findViewById(R.id.viewpager);
//        viewPager.setAdapter(
//                new SampleFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this));
//
//        // Передаём ViewPager в TabLayout
//        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                System.out.println();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getApplicationContext().getDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        imageLoader = new ImageLoader(width);

        // Put initial data into the word list.
        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//        sharedPreferences.edit().clear().commit();

//        imagesList.addAll(wrapImageData(imageLoader.getRandomImages(10)));
        imagesList.addAll(wrapImageData(imageLoader.getNext5()));

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.image_scroll_view);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new ImageListAdapter(this, imagesList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<ImageDataWrapper> wrapImageData(List<ImageData> imageDataList) {
        return imageDataList.stream().map(imageData -> new ImageDataWrapper(imageData,
                new SaveLikedState(sharedPreferences, imageData.getImageId(), imageData.getHmac()))).collect(Collectors.toList());
    }
}
