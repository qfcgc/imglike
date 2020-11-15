package com.example.imglike.repository;

import android.content.SharedPreferences;
import android.util.Log;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SharedPreferencesImageLikedRepository implements ImageLikedRepository {
    private final SharedPreferences sharedPreferences;

    @Override
    public boolean isLiked(String id) {
        boolean res = sharedPreferences.getBoolean(id, false);
        Log.d("getLiked", String.format("id=%s, val=%b", id, res));
        return res;
    }

    @Override
    public void refreshLiked(String id, boolean liked) {
        Log.d("setLiked", String.format("id=%s, val=%b", id, liked));
        sharedPreferences.edit().putBoolean(id, liked).apply();
    }
}
