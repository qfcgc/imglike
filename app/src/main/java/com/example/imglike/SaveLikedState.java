package com.example.imglike;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SaveLikedState {
    private final SharedPreferences sharedPreferences;
    private final String id;
    private final String hmac;

    public SaveLikedState(SharedPreferences sharedPreferences, int id, String hmac) {
        this.sharedPreferences = sharedPreferences;
        this.id = String.valueOf(id);
        this.hmac = hmac;
    }

    public boolean getLiked() {
        boolean res = sharedPreferences.getBoolean(id, false);
        Log.d("getLiked", String.format("id=%s, val=%b", id, res));
        return res;
    }

    public void setLiked(boolean liked) {
        Log.d("setLiked", String.format("id=%s, val=%b", id, liked));
        sharedPreferences.edit().putBoolean(id, liked).apply();
        Set<String> storedPairs = sharedPreferences.getStringSet("stored_pairs", Collections.emptySet());
        Set<String> newSet = new HashSet<>(storedPairs);
        String newPair = id + ":" + hmac;
        if (liked) {
            newSet.add(newPair);
        } else {
            newSet.remove(newPair);
        }
        sharedPreferences.edit().putStringSet("stored_pairs", newSet).apply();
    }
}
