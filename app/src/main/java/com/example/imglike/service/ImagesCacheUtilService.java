package com.example.imglike.service;

import com.example.imglike.model.ImageData;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class ImagesCacheUtilService {
    private final Map<Integer, List<ImageData>> cachedImages = Collections.synchronizedMap(new HashMap<>());

    public List<ImageData> getCachedImagesForPage(int page) {
        return cachedImages.get(page);
    }

    public void cacheImages(int page, List<ImageData> images) {
        cachedImages.put(page, images);
    }
}
