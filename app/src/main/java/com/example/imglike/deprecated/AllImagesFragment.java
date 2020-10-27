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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.imglike.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Deprecated
public class AllImagesFragment extends Fragment {

    private final LinkedList<ImageDataWrapper> imagesList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private ImageListAdapter mAdapter;
    private ImageLoader imageLoader;
    private SharedPreferences sharedPreferences;

    public static final String ARG_TYPE = "ARG_TYPE";
    private static AllImagesFragment NEW_IMAGES_INSTANCE;
//    private static AllImagesFragment STORED_IMAGES_INSTANCE;

    private int mPage;

//    public static AllImagesFragment newInstance(int page) {
//        Bundle args = new Bundle();
//        args.putInt(ARG_PAGE, page);
//        AllImagesFragment fragment = new AllImagesFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    public static AllImagesFragment getNewImages() {
        AllImagesFragment fragment = NEW_IMAGES_INSTANCE;
        if (fragment == null) {
            Bundle args = new Bundle();
            args.putString(ARG_TYPE, "RANDOM_IMAGES_PAGE");
            fragment = new AllImagesFragment();
            fragment.setArguments(args);
            NEW_IMAGES_INSTANCE = fragment;
        }
        return fragment;
    }

//    public static AllImagesFragment getStoredImages() {
//        AllImagesFragment fragment = STORED_IMAGES_INSTANCE;
//        if (fragment == null) {
//            Bundle args = new Bundle();
//            args.putString(ARG_TYPE, "LIKED_IMAGES_PAGE");
//            fragment = new AllImagesFragment();
//            fragment.setArguments(args);
//            STORED_IMAGES_INSTANCE = fragment;
//        }
//        return fragment;
//    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_TYPE);
        }
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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

        imagesList.addAll(wrapImageData(imageLoader.getRandomImages(10)));

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
                new SaveLikedState(sharedPreferences, imageData.getImageId(), imageData.getHmac()))).collect(Collectors.toList());
    }
}
