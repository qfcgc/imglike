package com.example.imglike.service;

import android.graphics.Bitmap;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class BitmapIntentHelperUtil {
    private final Map<String, Bitmap> idBitmap = new HashMap<>(1);

    public void storeBitmap(String id, Bitmap bmp) {
        idBitmap.put(id, bmp);
    }

    public Bitmap restoreBitmap(String id) {
        Bitmap bitmap = idBitmap.get(id);
        idBitmap.remove(id);
        return bitmap;
    }
}
