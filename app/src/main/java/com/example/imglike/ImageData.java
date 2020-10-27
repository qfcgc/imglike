package com.example.imglike;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;

public class ImageData {
    private final int imageId;
    private final String hmac;
    private final Bitmap data;
    private final int width;
    private final int height;

    public ImageData(Bitmap data, int imageId, int width, int height, String hmac) {
        this.data = data;
        this.imageId = imageId;
        this.width = width;
        this.height = height;
        this.hmac = hmac;
    }

    public int getImageId() {
        return imageId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getHmac() {
        return hmac;
    }

    public Bitmap getData() {
        return data;
    }

    @Override
    public String toString() {
        return String.format("{image_url=https://i.picsum.photos/id/%d/%d/%d.jpg?hmac=%s}",
                imageId,
                width,
                height,
                hmac);
    }
}
