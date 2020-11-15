package com.example.imglike;

import android.graphics.Bitmap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageData {
    private Bitmap data;
    private ImageMetadata metadata;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageMetadata {
        private String id;
        private String url;
        private int width;
        private int height;
    }
}
