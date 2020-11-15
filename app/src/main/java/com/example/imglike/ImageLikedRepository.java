package com.example.imglike;

public interface ImageLikedRepository {
    boolean isLiked(String id);

    void refreshLiked(String id, boolean liked);
}
