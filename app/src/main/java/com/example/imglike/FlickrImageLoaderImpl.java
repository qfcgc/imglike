package com.example.imglike;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.photos.Extras;
import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.googlecode.flickrjandroid.photos.SearchParameters;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.googlecode.flickrjandroid.photos.SearchParameters.RELEVANCE;

public class FlickrImageLoaderImpl implements ImageLoader {
    private static final String FLICKR_API_KEY = "5f4d78d239711d805048dcbfabd09ae4";
    private static final String FLICKR_API_SECRET = "9e14e6eed025747e";
    private static final String TAG = FlickrImageLoaderImpl.class.getName();
    private final Flickr flickr = new Flickr(FLICKR_API_KEY, FLICKR_API_SECRET);
    private SearchParameters searchParams;

    public FlickrImageLoaderImpl() {
        init();
    }

    protected void init() {
        searchParams = new SearchParameters();
        try {
            searchParams.setMedia("photos");
        } catch (FlickrException e) {
            Log.w(TAG, "init: exception which should never be thrown. Ignored.", e);
            throw new ImageLoadingException(e);
        }
        searchParams.setText("fun");
        searchParams.setSort(RELEVANCE);
        searchParams.setExtras(Collections.singleton(Extras.URL_L));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<ImageData> findPage(int pageSize, int pageIndex) {
        PhotoList photoList = loadPhotoList(pageSize, pageIndex);

        return mapPhotoListToListOfImageDataObjects(photoList);
    }

    private PhotoList loadPhotoList(int pageSize, int pageIndex) {
        Log.d(TAG, "Loading photo list is started");
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Callable<PhotoList> callable =
                () -> flickr.getPhotosInterface().search(searchParams, pageSize, pageIndex);
        Future<PhotoList> future = pool.submit(callable);
        PhotoList photoList;
        try {
            photoList = future.get();
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "loadPhotoList: error loading photolist", e);
            throw new ImageLoadingException(e);
        }
        Log.d(TAG, "Loading photo list is finished");
        return photoList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<ImageData> mapPhotoListToListOfImageDataObjects(PhotoList photoList) {
        ExecutorService imageLoaderPool = Executors.newFixedThreadPool(photoList.size());
        Collection<Callable<Void>> imageLoadingCollection = new ArrayList<>(photoList.size());
        PhotoToImageDataMapperFunction mapper = new PhotoToImageDataMapperFunction(imageLoadingCollection);

        List<ImageData> images = photoList.stream().map(mapper).collect(Collectors.toList());

        List<Future<Void>> futures;
        try {
            futures = imageLoaderPool.invokeAll(imageLoadingCollection);
        } catch (InterruptedException e) {
            Log.d(TAG, "mapPhotoListToListOfImageDataObjects: ", e);
            throw new ImageLoadingException(e);
        }

        futures.forEach(x -> {
            try {
                x.get();
            } catch (ExecutionException | InterruptedException e) {
                Log.d(TAG, "mapPhotoListToListOfImageDataObjects: ", e);
                throw new ImageLoadingException(e);
            }
        });

        return images;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @RequiredArgsConstructor
    private static class PhotoToImageDataMapperFunction implements Function<Photo, ImageData> {
        @NonNull
        private final Collection<Callable<Void>> imageLoadingCollection;

        @Override
        public ImageData apply(Photo photo) {
            ImageData data = new ImageData();
            ImageData.ImageMetadata metadata = new ImageData.ImageMetadata();
            metadata.setUrl(photo.getLargeUrl());
            metadata.setId(photo.getId());
            data.setMetadata(metadata);

            imageLoadingCollection.add(() -> {
                Log.d(TAG, String.format(
                        "Mapping photo with id %s to ImageData is started", data.getMetadata().getId()
                ));
                Bitmap bmp = BitmapFactory.decodeStream(new URL(photo.getLargeUrl()).openStream());
                data.setData(bmp);
                data.getMetadata().setWidth(bmp.getWidth());
                data.getMetadata().setHeight(bmp.getHeight());
                Log.d(TAG, String.format(
                        "Mapping photo with id %s to ImageData is finished", data.getMetadata().getId()
                ));
                return null;
            });
            return data;
        }
    }
}
