package com.example.imglike.service.loader;

import com.example.imglike.model.ImageData;

import java.util.List;

public interface ImageLoader {
    List<ImageData> findPage(int pageSize, int pageIndex);
}
