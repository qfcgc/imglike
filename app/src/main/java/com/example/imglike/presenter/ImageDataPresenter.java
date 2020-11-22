package com.example.imglike.presenter;

import android.view.View;
import android.widget.ImageView;
import com.example.imglike.R;
import com.example.imglike.model.ImageData;
import com.example.imglike.service.ImageLikedStatusUtilService;
import lombok.Getter;

@Getter
public class ImageDataPresenter {
    private final ImageView imageView;
    private final ImageView likeView;
    private final ImageData imageData;

    public ImageDataPresenter(View root, ImageData imageData) {
        this.imageView = root.findViewById(R.id.image);
        this.likeView = root.findViewById(R.id.image_like);
        this.imageData = imageData;

        this.likeView.setOnClickListener(view -> flipLikedState());
    }

    public void draw() {
        loadImage();
        refreshLikedIcon();
    }

    public void loadImage() {
        imageView.setImageBitmap(imageData.getData());
    }

    public void refreshLikedIcon() {
        if (ImageLikedStatusUtilService.isLiked(imageData.getMetadata().getId())) {
            likeView.setImageResource(R.drawable.ic_like_enable);
        } else {
            likeView.setImageResource(R.drawable.ic_like_disable);
        }
    }

    public void flipLikedState() {
        if (!ImageLikedStatusUtilService.isLiked(imageData.getMetadata().getId())) {
            ImageLikedStatusUtilService.refreshLiked(imageData.getMetadata().getId(), true);
            likeView.setImageResource(R.drawable.ic_like_enable);
        } else {
            ImageLikedStatusUtilService.refreshLiked(imageData.getMetadata().getId(), false);
            likeView.setImageResource(R.drawable.ic_like_disable);
        }
    }
}
