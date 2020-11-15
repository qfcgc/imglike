package com.example.imglike.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.imglike.*;
import com.example.imglike.activity.adapter.ImageListAdapter;
import com.example.imglike.service.ImageLikedStatusUtilService;
import com.example.imglike.service.loader.FlickrImageLoaderImpl;
import com.example.imglike.service.loader.ImageLoader;
import com.example.imglike.model.ImageData;
import com.example.imglike.repository.SharedPreferencesImageLikedRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    public static final int PAGE_SIZE = 30;
    private final List<ImageData> imagesList = new ArrayList<>();
    private ImageListAdapter mAdapter;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageLoader = new FlickrImageLoaderImpl();

        ImageLikedStatusUtilService.setRepository(
                new SharedPreferencesImageLikedRepository(
                        getSharedPreferences("ImageLikedRepository", Context.MODE_PRIVATE)
                )
        );

        imagesList.addAll(imageLoader.findPage(PAGE_SIZE, 1));

        // Get a handle to the RecyclerView.
        RecyclerView mRecyclerView = findViewById(R.id.image_scroll_view);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new ImageListAdapter(this, imagesList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final AtomicInteger pageIndex = new AtomicInteger(2);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    imagesList.addAll(imageLoader.findPage(PAGE_SIZE, pageIndex.getAndIncrement()));
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean res = super.onCreateOptionsMenu(menu);
        menu.clear();
        new MenuInflater(getApplicationContext()).inflate(R.menu.right_menu, menu);
        return res;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean res = super.onOptionsItemSelected(item);

        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, PartnersActivity.class);
        startActivityForResult(intent, 10, bundle);

        return res;
    }
}
