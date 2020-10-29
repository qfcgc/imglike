package com.example.imglike.deprecated;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.imglike.*;

import java.util.*;
import java.util.stream.Collectors;

@Deprecated
public class StoredImagesFragment extends Fragment {
    /*private final LinkedList<ImageDataWrapper> imagesList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private ImageListAdapter mAdapter;
    private ImageLoader imageLoader;
    private SharedPreferences sharedPreferences;

    public static final String ARG_TYPE = "ARG_TYPE";
    private static StoredImagesFragment STORED_IMAGES_INSTANCE;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static StoredImagesFragment getStoredImages() {
        StoredImagesFragment fragment = STORED_IMAGES_INSTANCE;
        if (fragment == null) {
            Bundle args = new Bundle();
            args.putString(ARG_TYPE, "LIKED_IMAGES_PAGE");
            fragment = new StoredImagesFragment();
            fragment.setArguments(args);
            STORED_IMAGES_INSTANCE = fragment;
        }
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_images, container, false);

        return createRecyclerView((RecyclerView) view);
//        recyclerView.setText("Fragment #" + mPage);
    }

    private View createRecyclerView(RecyclerView view) {
//        super.onCreate(savedInstanceState);

        Display display = view.getContext().getDisplay();;
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        imageLoader = new ImageLoader(width);

        // Put initial data into the word list.
        Context context = view.getContext();
        sharedPreferences = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//        sharedPreferences.edit().clear().commit();
        Set<String> stored = sharedPreferences.getStringSet("stored_pairs", Collections.emptySet());
        List<ImageData> storedImages = stored.stream().map(s -> {
            String id = s.substring(0, s.indexOf(':'));
            String hmac = s.substring(s.indexOf(':') + 1);
            return new ImageData(null, Integer.parseInt(id), size.x, (int) (1.5 * width), hmac);
        }).collect(Collectors.toList());

        imagesList.addAll(wrapImageData(imageLoader.getStoredImages(storedImages)));

        // Get a handle to the RecyclerView.
        mRecyclerView = view.findViewById(R.id.image_scroll_view);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new ImageListAdapter(context, imagesList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        return view;
    }

    private List<ImageDataWrapper> wrapImageData(List<ImageData> imageDataList) {
        return imageDataList.stream().map(imageData -> new ImageDataWrapper(imageData,
                new SaveLikedState(sharedPreferences, imageData.getImageId(), imageData.getHmac())))
                .collect(Collectors.toList());
    }*/
}
