package com.example.imglike;

import java.util.List;

public interface ImageLoader {
    List<ImageData> findPage(int pageSize, int pageIndex);
}
