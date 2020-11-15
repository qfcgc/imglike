package com.example.imglike;

import lombok.Setter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageLikedStatusUtilService {
    @Setter
    private ImageLikedRepository repository;

    public boolean isLiked(String id) {
        return repository.isLiked(id);
    }

    public void refreshLiked(String id, boolean liked) {
        repository.refreshLiked(id, liked);
    }
}
