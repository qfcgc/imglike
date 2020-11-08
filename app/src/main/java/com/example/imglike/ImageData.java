package com.example.imglike;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;

import java.io.Serializable;

public class ImageData implements Serializable {
    private int imageId;
    private String hmac;
    private Bitmap data;
    private int width;
    private int height;

    public ImageData() {
    }

    public ImageData(Bitmap data, int imageId, int width, int height, String hmac) {
        this.data = data;
        this.imageId = imageId;
        this.width = width;
        this.height = height;
        this.hmac = hmac;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
    }

    public void setData(Bitmap data) {
        this.data = data;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
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
