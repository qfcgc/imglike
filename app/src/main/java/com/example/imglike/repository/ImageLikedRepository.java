package com.example.imglike.repository;

public interface ImageLikedRepository {
    boolean isLiked(String id);

    void refreshLiked(String id, boolean liked);
}
