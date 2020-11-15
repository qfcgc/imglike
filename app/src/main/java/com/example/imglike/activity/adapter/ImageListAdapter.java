package com.example.imglike.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.imglike.R;
import com.example.imglike.activity.ImageActivity;
import com.example.imglike.activity.MainActivity;
import com.example.imglike.model.ImageData;
import com.example.imglike.service.BitmapIntentHelperUtil;
import com.example.imglike.service.ImageLikedStatusUtilService;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {
    private final List<ImageData> mImageList;
    private final LayoutInflater mInflater;
    private final MainActivity mainActivity;

    public ImageListAdapter(MainActivity context,
                            List<ImageData> imageList) {
        this.mainActivity = context;
        mInflater = LayoutInflater.from(context);
        this.mImageList = imageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.image_layout, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(mItemView, mainActivity);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageData imageData = mImageList.get(position);
        holder.imageItemView.setImageBitmap(imageData.getData());
        holder.setImages(mImageList);
        holder.setImage(imageData);
        holder.reloadLikedState();
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageItemView;
        public final ImageView likeView;
        private final MainActivity mainActivity;
        public List<ImageData> images;
        private ImageData image;

        public ImageViewHolder(View itemView, MainActivity mainActivity) {
            super(itemView);
            this.imageItemView = itemView.findViewById(R.id.image);
            this.imageItemView.setOnClickListener(this::imageClickListener);
            this.likeView = itemView.findViewById(R.id.image_like);
            this.images = Collections.emptyList();
            this.mainActivity = mainActivity;

            this.likeView.setOnClickListener(v -> {
                if (!ImageLikedStatusUtilService.isLiked(image.getMetadata().getId())) {
                    ImageLikedStatusUtilService.refreshLiked(image.getMetadata().getId(), true);
                    likeView.setImageResource(R.drawable.ic_like_enable);
                } else {
                    ImageLikedStatusUtilService.refreshLiked(image.getMetadata().getId(), false);
                    likeView.setImageResource(R.drawable.ic_like_disable);
                }
            });
        }

        public void setImages(List<ImageData> images) {
            this.images = images;
        }

        public void setImage(ImageData image) {
            this.image = image;
        }

        public void reloadLikedState() {
            if (ImageLikedStatusUtilService.isLiked(image.getMetadata().getId())) {
                ImageLikedStatusUtilService.refreshLiked(image.getMetadata().getId(), true);
                likeView.setImageResource(R.drawable.ic_like_enable);
            } else {
                ImageLikedStatusUtilService.refreshLiked(image.getMetadata().getId(), false);
                likeView.setImageResource(R.drawable.ic_like_disable);
            }
        }

        private void imageClickListener(View v) {
            Bundle bundle = new Bundle();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.getData().compress(Bitmap.CompressFormat.PNG, 100, stream);

            Intent intent = new Intent(mainActivity, ImageActivity.class);
            intent.putExtra("id", image.getMetadata().getId());
            BitmapIntentHelperUtil.storeBitmap(image.getMetadata().getId(), image.getData());
            mainActivity.startActivityForResult(intent, 10, bundle);
        }
    }
}
