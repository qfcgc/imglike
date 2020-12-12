package com.example.imglike.presenter;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.imglike.R;
import com.example.imglike.activity.adapter.ImageListAdapter;
import com.example.imglike.repository.SharedPreferencesImageLikedRepository;
import com.example.imglike.service.ImageLikedStatusUtilService;
import com.example.imglike.service.loader.FlickrImageLoaderImpl;
import com.example.imglike.service.loader.ImageLoader;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;

import static com.example.imglike.presenter.ScrollPresenter.PAGE_SIZE;

@UtilityClass
public class ScrollPresenterInitHelper {
    public void initializeServices(AppCompatActivity scrollActivity) {
        ImageLikedStatusUtilService.setRepository(
                new SharedPreferencesImageLikedRepository(
                        scrollActivity.getSharedPreferences("ImageLikedRepository", Context.MODE_PRIVATE)
                )
        );
    }

    public ImageLoader initializeImageLoader() {
        return new FlickrImageLoaderImpl();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ImageListAdapter initializeImageListAdapter(AppCompatActivity activity, ImageLoader imageLoader) {
        ImageListAdapter imageListAdapter = new ImageListAdapter(activity, new ArrayList<>());
        imageListAdapter.getImages().addAll(imageLoader.findPage(PAGE_SIZE, 1));
        return imageListAdapter;
    }
}
