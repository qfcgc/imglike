package com.example.imglike.activity.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.imglike.R;
import com.example.imglike.activity.ImageActivity;
import com.example.imglike.activity.MainActivity;
import com.example.imglike.model.ImageData;
import com.example.imglike.service.BitmapIntentHelperUtil;
import com.example.imglike.presenter.ImageDataPresenter;
import lombok.Getter;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {
    @Getter
    private final List<ImageData> images;
    private final LayoutInflater inflater;
    private final MainActivity mainActivity;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ImageListAdapter(MainActivity context,
                            List<ImageData> imageList) {
        this.mainActivity = context;
        this.inflater = LayoutInflater.from(context);
        this.images = imageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = inflater.inflate(R.layout.image_layout, parent, false);
        return new ImageViewHolder(mItemView, mainActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageData imageData = images.get(position);
        holder.bindImageToView(imageData);
        holder.draw();
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        private final View itemView;
        private final MainActivity mainActivity;
        private ImageDataPresenter imageDataPresenter;

        public ImageViewHolder(View itemView, MainActivity mainActivity) {
            super(itemView);
            this.itemView = itemView;
            this.mainActivity = mainActivity;
        }

        public void bindImageToView(ImageData image) {
            this.imageDataPresenter = new ImageDataPresenter(this.itemView, image);
            this.imageDataPresenter.getImageView().setOnClickListener(this::imageClickListener);
        }

        public void draw() {
            if (this.imageDataPresenter != null) {
                this.imageDataPresenter.draw();
            } else {
                throw new RuntimeException("Cannot draw image because it is not bind");
            }
        }

        private void imageClickListener(View v) {
            Bundle bundle = new Bundle();
            ImageData imageData = imageDataPresenter.getImageData();
            ImageData.ImageMetadata metadata = imageData.getMetadata();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageData.getData().compress(Bitmap.CompressFormat.PNG, 100, stream);

            Intent intent = new Intent(mainActivity, ImageActivity.class);
            intent.putExtra("id", imageData.getMetadata().getId());
            BitmapIntentHelperUtil.storeBitmap(metadata.getId(), imageData.getData());
            mainActivity.startActivityForResult(intent, 10, bundle);
        }
    }
}
