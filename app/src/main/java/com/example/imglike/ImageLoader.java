package com.example.imglike;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import okhttp3.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class ImageLoader {
    private final String RANDOM_IMAGE_URL_PATTERN = "https://picsum.photos/%d/%d";
    private final int width;
    private final int height;
    private final OkHttpClient client = new OkHttpClient.Builder()
            .callTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build();
    private final Request randomRequest;
    private Request storedRequest;
//1080; 1620
    public ImageLoader(int width) {
        this.width = width;
        this.height = (int) (1.5 * width);
        randomRequest = new Request.Builder()
                .url(String.format(RANDOM_IMAGE_URL_PATTERN, this.width, this.height))
//                        .url("https://source.unsplash.com/random")
                .get()
                .build();
    }

    private static List<ImageData> HARDCODED_IMAGES = new ArrayList<>(30);
    static {
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 880, 1080, 1620, "OQZUi9dVsOqU0UAvATS1Nqhj8V8KazN4NNIQWBWEWxQ"));
        HARDCODED_IMAGES.add(new ImageData(null, 1049, 1080, 1620, "YiRBV_gLrTmDmnZLmqnUNcfY2XMDW9Xu0xtbkHRdzkU"));
        HARDCODED_IMAGES.add(new ImageData(null, 202, 1080, 1620, "EIUOBbRgX3E4vFgc1H-GqBRynzsKmiHU4IGhXGi99B4"));
        HARDCODED_IMAGES.add(new ImageData(null, 16, 1080, 1620, "6sE-WtmGYYgTXKKfAQbNQiYYX_zKJuS28YYx249EXEg"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
    }

    int idx = 0;

    public List<ImageData> getNext5() {
        List<ImageData> dataList = HARDCODED_IMAGES.subList(idx, idx + 5);
        idx+=5;
        List<ImageData> images = Collections.emptyList();
        if (!dataList.isEmpty()) {
            ExecutorService pool = Executors.newFixedThreadPool(dataList.size());
            List<Callable<ImageData>> callables = new ArrayList<>(dataList.size());
            for (int i = 0; i < dataList.size(); i++) {
                callables.add(new StoredImageLoadingCallable(client,
                        dataList.get(i).getImageId(), width, height, dataList.get(i).getHmac()));
            }
            List<Future<ImageData>> futures = new ArrayList<>(dataList.size());
            for (Callable<ImageData> callable : callables) {
                futures.add(pool.submit(callable));
            }
            images = new ArrayList<>(dataList.size());
            for (Future<ImageData> future : futures) {
                try {
                    images.add(future.get());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return images;
    }

    public List<ImageData> getStoredImages(List<ImageData> dataList) {
        List<ImageData> images = Collections.emptyList();
        if (!dataList.isEmpty()) {
            ExecutorService pool = Executors.newFixedThreadPool(dataList.size());
            Set<Callable<ImageData>> callables = new HashSet<>(dataList.size());
            for (int i = 0; i < dataList.size(); i++) {
                callables.add(new StoredImageLoadingCallable(client,
                        dataList.get(i).getImageId(), width, height, dataList.get(i).getHmac()));
            }
            Set<Future<ImageData>> futures = new HashSet<>(dataList.size());
            for (Callable<ImageData> callable : callables) {
                futures.add(pool.submit(callable));
            }
            images = new ArrayList<>(dataList.size());
            for (Future<ImageData> future : futures) {
                try {
                    images.add(future.get());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return images;
    }

    public List<ImageData> getRandomImages(int count) {
        ExecutorService pool = Executors.newFixedThreadPool(count);
        Set<Callable<ImageData>> callables = new HashSet<>(count);
        for (int i = 0; i < count; i++) {
            callables.add(new RandomImageLoadingCallable(client, randomRequest, width, height));
        }
        Set<Future<ImageData>> futures = new HashSet<>(count);
        for (Callable<ImageData> callable : callables) {
            futures.add(pool.submit(callable));
        }
        List<ImageData> images = new ArrayList<>(count);
        for (Future<ImageData> future : futures) {
            try {
                images.add(future.get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        return images;
    }

    public ImageData getRandomImage() {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Callable<ImageData> callable = new RandomImageLoadingCallable(client, randomRequest, width, height);
        Future<ImageData> future = pool.submit(callable);
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class RandomImageLoadingCallable implements Callable<ImageData> {
        private final OkHttpClient client;
        private final Request request;
        private final int width;
        private final int height;

        public RandomImageLoadingCallable(OkHttpClient client, Request request, int width, int height) {
            this.client = client;
            this.request = request;
            this.width = width;
            this.height = height;
        }

        @Override
        public ImageData call() throws Exception {
            Call call = client.newCall(request);
            try {
                Response response = call.execute();
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    Bitmap bmp = BitmapFactory.decodeStream(body.byteStream());
                    String prefix = "https://i.picsum.photos/id/";
                    String url = response.request().url().toString();
                    int idStartPos = prefix.length();
                    String id = url.substring(idStartPos, url.indexOf('/', idStartPos));
                    String hmac = url.substring(url.indexOf("hmac=") + "hmac=".length());

                    return new ImageData(bmp, Integer.parseInt(id), width, height, hmac);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class StoredImageLoadingCallable implements Callable<ImageData> {
        private final String STORED_IMAGE_URL_PATTERN = "https://i.picsum.photos/id/%d/%d/%d.jpg?hmac=%s";
        private final OkHttpClient client;
        private final int id;
        private final int width;
        private final int height;
        private final String hmac;

        public StoredImageLoadingCallable(OkHttpClient client, int id, int width, int height, String hmac) {
            this.client = client;
            this.id = id;
            this.width = width;
            this.height = height;
            this.hmac = hmac;
        }

        @Override
        public ImageData call() throws Exception {
            Request storedRequest = new Request.Builder()
                    .url(String.format(STORED_IMAGE_URL_PATTERN,
                            id,
                            width,
                            height,
                            hmac))
                    .get()
                    .build();
            Call call = client.newCall(storedRequest);
            try {
                Response response = call.execute();
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    Bitmap bmp = BitmapFactory.decodeStream(body.byteStream());
                    String prefix = "https://i.picsum.photos/id/";
                    String url = response.request().url().toString();
                    int idStartPos = prefix.length();
                    String id = url.substring(idStartPos, url.indexOf('/', idStartPos));
                    String hmac = url.substring(url.indexOf("hmac=") + "hmac=".length());

                    return new ImageData(bmp, Integer.parseInt(id), width, height, hmac);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
