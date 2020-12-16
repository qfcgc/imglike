package com.example.imglike.presenter;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.imglike.R;
import com.example.imglike.activity.adapter.ImageListAdapter;
import com.example.imglike.model.ImageData;
import com.example.imglike.service.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class ScrollPresenter {
    public static final int PAGE_SIZE = 10;
    private static final String TAG = ScrollPresenter.class.getName();
    private final ImageLoader imageLoader;
    private final ImageListAdapter imageListAdapter;
    private final AtomicInteger PAGE_INDEX = new AtomicInteger(1);
    private final AppCompatActivity scrollActivity;
    private ImagesOnScrollListener listener;
    private RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ScrollPresenter(AppCompatActivity scrollActivity) {
        ScrollPresenterInitHelper.initializeServices(scrollActivity);

        this.scrollActivity = scrollActivity;
        this.imageLoader = ScrollPresenterInitHelper.initializeImageLoader(scrollActivity.getApplicationContext());
        this.imageListAdapter = ScrollPresenterInitHelper.initializeImageListAdapter(scrollActivity, this.imageLoader);
        initializeRecyclerView(this.imageListAdapter);
    }

    public void notifyDataSetChanged() {
        imageListAdapter.notifyDataSetChanged();
        Log.i(TAG, "Images data set changed");
    }

    private void initializeRecyclerView(ImageListAdapter imageListAdapter) {
        this.recyclerView = scrollActivity.findViewById(R.id.image_scroll_view);
        this.recyclerView.setAdapter(imageListAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(scrollActivity));
        this.listener = new ImagesOnScrollListener();
        this.recyclerView.addOnScrollListener(this.listener);
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("PAGES_COUNT", PAGE_INDEX.intValue());
        outState.putInt("SCROLL_OFFSET", recyclerView.computeVerticalScrollOffset());
    }

    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        //restores PAGE_INDEX value
        PAGE_INDEX.set(savedInstanceState.getInt("PAGES_COUNT"));
        //loads image pages
        loadPages(PAGE_INDEX.intValue());
        //scrolls to previous position
        recyclerView.scrollToPosition(savedInstanceState.getInt("SCROLL_OFFSET"));
    }

    private void loadPages(int count) {
        Log.i(TAG, "Starting loading images");
        List<ImageData> loaded = Collections.synchronizedList(new ArrayList<>());
        Future<?> result = Executors.newSingleThreadExecutor().submit(() -> {
            Log.i(TAG, "Starting loading image pages process");
            for (int i = 2; i <= count; i++) {
                loaded.addAll(imageLoader.findPage(PAGE_SIZE, i));
                Log.d(TAG, String.format("Images loaded, size: %d", loaded.size()));
            }
            Log.i(TAG, "Loading image pages process is finished");
            return null;
        });
        try {
            result.get();
            this.listener.putImagesToLoaded(loaded);
            this.listener.showAddedItems();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class ImagesOnScrollListener extends RecyclerView.OnScrollListener {
        private final List<ImageData> loaded = Collections.synchronizedList(new ArrayList<>());
        private boolean isLoading = false;

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (!loaded.isEmpty()) {
                showAddedItems();
                return;
            }
            synchronized (this) {
                if (!isLoading && shouldLoadImages()) {
                    Log.i(TAG, "Starting loading images");
                    isLoading = true;
                    Executors.newSingleThreadExecutor().submit(() -> {
                        Log.i(TAG, "Starting loading images process");
                        loaded.addAll(imageLoader.findPage(PAGE_SIZE, PAGE_INDEX.incrementAndGet()));
                        isLoading = false;
                        Log.i(TAG, "Loading images process is finished");
                    });
                }
            }
        }

        public void showAddedItems() {
            List<ImageData> cloned = new ArrayList<>(loaded.subList(0, loaded.size()));
            loaded.removeAll(cloned);
            imageListAdapter.getImages().addAll(cloned);
            Log.i(TAG, "New images are added to images data set");
            notifyDataSetChanged();
        }

        private boolean shouldLoadImages() {
            int offset = recyclerView.computeVerticalScrollOffset();
            int extent = recyclerView.computeVerticalScrollExtent();
            int range = recyclerView.computeVerticalScrollRange();

            return range - offset < extent * 3 || !recyclerView.canScrollVertically(1);
        }

        public void putImagesToLoaded(List<ImageData> newImages) {
            loaded.addAll(newImages);
        }
    }
}
