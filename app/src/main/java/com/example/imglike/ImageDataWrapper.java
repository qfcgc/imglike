package com.example.imglike;

public class ImageDataWrapper {
    private final ImageData imageData;
    private final SaveLikedState saveLikedState;

    public ImageDataWrapper(ImageData imageData, SaveLikedState saveLikedState) {
        this.imageData = imageData;
        this.saveLikedState = saveLikedState;
    }

    public ImageData getImageData() {
        return imageData;
    }

    public SaveLikedState getSaveLikedState() {
        return saveLikedState;
    }
}
