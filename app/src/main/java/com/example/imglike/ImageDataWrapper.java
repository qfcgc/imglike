package com.example.imglike;

import java.io.Serializable;

public class ImageDataWrapper implements Serializable {
    private ImageData imageData;
    private SaveLikedState saveLikedState;

    public ImageDataWrapper() {

    }

    public ImageDataWrapper(ImageData imageData, SaveLikedState saveLikedState) {
        this.imageData = imageData;
        this.saveLikedState = saveLikedState;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }

    public void setSaveLikedState(SaveLikedState saveLikedState) {
        this.saveLikedState = saveLikedState;
    }

    public ImageData getImageData() {
        return imageData;
    }

    public SaveLikedState getSaveLikedState() {
        return saveLikedState;
    }
}
