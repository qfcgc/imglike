package com.example.imglike;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {
    private final LinkedList<ImageDataWrapper> mImageList;
    private final LayoutInflater mInflater;
    private final List<ImageViewHolder> holders = new LinkedList<>();
    private final MainActivity mainActivity;

    public ImageListAdapter(MainActivity context,
                            LinkedList<ImageDataWrapper> imageList) {
        this.mainActivity = context;
        mInflater = LayoutInflater.from(context);
        this.mImageList = imageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.image_layout,
                parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(mItemView, mainActivity, this);
        holders.add(imageViewHolder);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageDataWrapper mCurrent = mImageList.get(position);
        holder.imageItemView.setImageBitmap(mCurrent.getImageData().getData());
        holder.setImages(mImageList);
        holder.setImageDataWrapper(mCurrent);
        holder.reloadLikedState();
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    @Override
    public void onViewRecycled(@NonNull ImageViewHolder holder) {
        holder.foo();
    }

    public List<ImageViewHolder> getHolders() {
        return holders;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageItemView;
        public final ImageView likeView;
        final ImageListAdapter mAdapter;
        private ImageDataWrapper imageDataWrapper;
        public List<ImageDataWrapper> images;
        private MainActivity mainActivity;

        public void foo() {
            if (imageDataWrapper != null && imageDataWrapper.getImageData() != null) {
                System.out.println(imageDataWrapper.getImageData().toString());
            }
        }

        public ImageViewHolder(View itemView, MainActivity mainActivity, ImageListAdapter adapter) {
            super(itemView);
            this.imageItemView = itemView.findViewById(R.id.image);
            this.imageItemView.setOnClickListener(this::imageClickListener);
            this.mainActivity = mainActivity;
            this.likeView = itemView.findViewById(R.id.image_like);
            this.mAdapter = adapter;
            this.images = Collections.emptyList();

            /*imageItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.launchSecondActivity(v);
                }
            });*/

            this.likeView.setOnClickListener(v -> {
                if (!imageDataWrapper.getSaveLikedState().getLiked()) {
                    imageDataWrapper.getSaveLikedState().setLiked(true);
                    likeView.setImageResource(R.drawable.ic_like_enable);
                } else {
                    imageDataWrapper.getSaveLikedState().setLiked(false);
                    likeView.setImageResource(R.drawable.ic_like_disable);
                }
            });
        }

        public void setImages(List<ImageDataWrapper> images) {
            this.images = images;
        }

        public void setImageDataWrapper(ImageDataWrapper imageDataWrapper) {
            this.imageDataWrapper = imageDataWrapper;
        }

        /**
         * @implSpec uses {@link #imageDataWrapper} so it must be set
         */
        public void reloadLikedState() {
            if (imageDataWrapper.getSaveLikedState().getLiked()) {
                imageDataWrapper.getSaveLikedState().setLiked(true);
                likeView.setImageResource(R.drawable.ic_like_enable);
//                likedCachedValue = true;
            } else {
                imageDataWrapper.getSaveLikedState().setLiked(false);
                likeView.setImageResource(R.drawable.ic_like_disable);
//                likedCachedValue = false;
            }
        }

        private void imageClickListener(View v)
        {
//            ActivityOptions options
//                    = ActivityOptions.makeSceneTransitionAnimation(mainActivity,
//                    new Pair<>(itemView.findViewById(R.id.image),
//                            mainActivity.getString(R.string.image_transition)),
//                    new Pair<>(itemView.findViewById(R.id.image_like), mainActivity.getString(R.string.image_like_transition))
//            );

            Bundle bundle = new Bundle();
            Intent intent = new Intent(mainActivity, ImageActivity.class);
            intent.putExtra("imageId", imageDataWrapper.getImageData().getImageId());
            intent.putExtra("width", imageDataWrapper.getImageData().getWidth());
            intent.putExtra("hmac", imageDataWrapper.getImageData().getHmac());
            intent.putExtra("liked", imageDataWrapper.getSaveLikedState().getLiked());
            mainActivity.startActivityForResult(intent, 10, bundle);
        }
    }
}
