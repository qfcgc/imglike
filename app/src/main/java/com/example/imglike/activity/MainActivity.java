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
import com.example.imglike.R;
import com.example.imglike.activity.adapter.ImageListAdapter;
import com.example.imglike.repository.SharedPreferencesImageLikedRepository;
import com.example.imglike.service.ImageLikedStatusUtilService;
import com.example.imglike.service.loader.FlickrImageLoaderImpl;
import com.example.imglike.service.loader.ImageLoader;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    public static final int PAGE_SIZE = 30;
    private final AtomicInteger PAGE_INDEX = new AtomicInteger(2);
    private ImageLoader imageLoader;
    private ImageListAdapter imageListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeServices();
        this.imageLoader = initializeImageLoader();
        this.imageListAdapter = initializeImageListAdapter(this.imageLoader);
        initializeRecyclerView(this.imageListAdapter);
    }

    private void initializeServices() {
        ImageLikedStatusUtilService.setRepository(
                new SharedPreferencesImageLikedRepository(
                        getSharedPreferences("ImageLikedRepository", Context.MODE_PRIVATE)
                )
        );
    }

    private ImageLoader initializeImageLoader() {
        return new FlickrImageLoaderImpl();
    }

    private ImageListAdapter initializeImageListAdapter(ImageLoader imageLoader) {
        ImageListAdapter imageListAdapter = new ImageListAdapter(this, new ArrayList<>());
        imageListAdapter.getImages().addAll(imageLoader.findPage(PAGE_SIZE, 1));
        return imageListAdapter;
    }

    private void initializeRecyclerView(ImageListAdapter imageListAdapter) {
        RecyclerView mRecyclerView = findViewById(R.id.image_scroll_view);
        mRecyclerView.setAdapter(imageListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addOnScrollListener(new ImagesOnScrollListener());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageListAdapter.notifyDataSetChanged();
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

    private class ImagesOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (!recyclerView.canScrollVertically(1)) {
                imageListAdapter.getImages().addAll(imageLoader.findPage(PAGE_SIZE, PAGE_INDEX.getAndIncrement()));
                imageListAdapter.notifyDataSetChanged();
            }
        }
    }
}
