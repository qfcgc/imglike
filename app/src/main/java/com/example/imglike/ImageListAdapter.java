package com.example.imglike;

import android.content.Context;
import android.util.Log;
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

    public ImageListAdapter(Context context,
                            LinkedList<ImageDataWrapper> imageList) {
        mInflater = LayoutInflater.from(context);
        this.mImageList = imageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.image_layout,
                parent, false);
        return new ImageViewHolder(mItemView, this);
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

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageItemView;
        public final ImageView likeView;
        final ImageListAdapter mAdapter;
        private ImageDataWrapper imageDataWrapper;
        public List<ImageDataWrapper> images;
        private boolean likedCachedValue;

        public void foo() {
            if (imageDataWrapper != null && imageDataWrapper.getImageData() != null) {
                System.out.println(imageDataWrapper.getImageData().toString());
            }
        }

        public ImageViewHolder(View itemView, ImageListAdapter adapter) {
            super(itemView);
            this.imageItemView = itemView.findViewById(R.id.image);
            this.likeView = itemView.findViewById(R.id.image_like);
            this.mAdapter = adapter;
            this.images = Collections.emptyList();

            this.likeView.setOnClickListener(v -> {
                if (!imageDataWrapper.getSaveLikedState().getLiked()) {
                    imageDataWrapper.getSaveLikedState().setLiked(true);
                    likeView.setImageResource(R.drawable.ic_like_enable);
                    likedCachedValue = true;
                } else {
                    imageDataWrapper.getSaveLikedState().setLiked(false);
                    likeView.setImageResource(R.drawable.ic_like_disable);
                    likedCachedValue = false;
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
                likedCachedValue = true;
            } else {
                imageDataWrapper.getSaveLikedState().setLiked(false);
                likeView.setImageResource(R.drawable.ic_like_disable);
                likedCachedValue = false;
            }
        }
    }
}
