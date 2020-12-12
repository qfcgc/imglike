package com.example.imglike.presenter;

import android.os.Build;
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
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ScrollPresenter {
    public static final int PAGE_SIZE = 30;
    private static final String TAG = ScrollPresenter.class.getName();
    private final ImageLoader imageLoader;
    private final ImageListAdapter imageListAdapter;
    private final AtomicInteger PAGE_INDEX = new AtomicInteger(2);
    private final AppCompatActivity scrollActivity;
    private RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ScrollPresenter(AppCompatActivity scrollActivity) {
        ScrollPresenterInitHelper.initializeServices(scrollActivity);

        this.scrollActivity = scrollActivity;
        this.imageLoader = ScrollPresenterInitHelper.initializeImageLoader();
        this.imageListAdapter = ScrollPresenterInitHelper.initializeImageListAdapter(scrollActivity, this.imageLoader);
        initializeRecyclerView(this.imageListAdapter);
    }

    public void notifyDataSetChanged() {
        imageListAdapter.notifyDataSetChanged();
        Log.i(TAG, "Images data set changed");
    }

    private void initializeRecyclerView(ImageListAdapter imageListAdapter) {
        RecyclerView recyclerView = scrollActivity.findViewById(R.id.image_scroll_view);
        recyclerView.setAdapter(imageListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(scrollActivity));
        recyclerView.addOnScrollListener(createListenerForLoadingImages());
        this.recyclerView = recyclerView;
    }

    public RecyclerView.OnScrollListener createListenerForLoadingImages() {
        return new ImagesOnScrollListener();
    }

    public class ImagesOnScrollListener extends RecyclerView.OnScrollListener {
        private final List<ImageData> loaded = Collections.synchronizedList(new ArrayList<>());
        private boolean isLoading = false;

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (!loaded.isEmpty()) {
                List<ImageData> cloned = new ArrayList<>(loaded.subList(0, loaded.size()));
                loaded.removeAll(cloned);
                imageListAdapter.getImages().addAll(cloned);
                Log.i(TAG, "New images are added to images data set");
                notifyDataSetChanged();
                return;
            }
            synchronized (this) {
                if (!isLoading && shouldLoadImages()) {
                    Log.i(TAG, "Starting loading images");
                    isLoading = true;
                    Executors.newSingleThreadExecutor().submit(() -> {
                        Log.i(TAG, "Starting loading images process");
                        loaded.addAll(imageLoader.findPage(PAGE_SIZE, PAGE_INDEX.getAndIncrement()));
                        isLoading = false;
                        Log.i(TAG, "Loading images process is finished");
                    });
                }
            }
        }

        private boolean shouldLoadImages() {
            int offset = recyclerView.computeVerticalScrollOffset();
            int extent = recyclerView.computeVerticalScrollExtent();
            int range = recyclerView.computeVerticalScrollRange();

            return range - offset < extent * 3 || !recyclerView.canScrollVertically(1);
        }
    }
}
