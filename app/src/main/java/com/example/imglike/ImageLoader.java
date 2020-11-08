package com.example.imglike;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import okhttp3.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class ImageLoader {
    private static final List<ImageData> HARDCODED_IMAGES = new ArrayList<>(30);

    static {
        HARDCODED_IMAGES.add(new ImageData(null, 229, 1080, 1620, "9yiDQ4TOS9U7eUbdVhWbAtIUwhhKspOkGGCsMkvgW2w"));
        HARDCODED_IMAGES.add(new ImageData(null, 880, 1080, 1620, "OQZUi9dVsOqU0UAvATS1Nqhj8V8KazN4NNIQWBWEWxQ"));
        HARDCODED_IMAGES.add(new ImageData(null, 1049, 1080, 1620, "YiRBV_gLrTmDmnZLmqnUNcfY2XMDW9Xu0xtbkHRdzkU"));
        HARDCODED_IMAGES.add(new ImageData(null, 202, 1080, 1620, "EIUOBbRgX3E4vFgc1H-GqBRynzsKmiHU4IGhXGi99B4"));
        HARDCODED_IMAGES.add(new ImageData(null, 16, 1080, 1620, "6sE-WtmGYYgTXKKfAQbNQiYYX_zKJuS28YYx249EXEg"));
        HARDCODED_IMAGES.add(new ImageData(null, 344, 1080, 1620, "K8UPPkZa346ipBYyqNJl7WOUI9icQeKr7Ct5pTN_5L8"));
        HARDCODED_IMAGES.add(new ImageData(null, 931, 1080, 1620, "xRvBwUc_bw3xrneshRztxVdIfnYOciZgZzWaWUQGHls"));
        HARDCODED_IMAGES.add(new ImageData(null, 145, 1080, 1620, "eNYSY-Qzevl1UU9m7_lM9cgoAeU38sqjXwdb2xJalOw"));
        HARDCODED_IMAGES.add(new ImageData(null, 933, 1080, 1620, "gIQzHZ2B6V2A4vM5WtEOVL_TdOelRb5PA2bIUAF32qg"));
        HARDCODED_IMAGES.add(new ImageData(null, 41, 1080, 1620, "vC48j9OBMcdVgk2vqaziNf-lMBNVZm6AVlzGf2t9hfo"));
        HARDCODED_IMAGES.add(new ImageData(null, 502, 1080, 1620, "xa90uEyoE6dBbr2oAjfbwWjSaX_mG4csWluBCDWB1fM"));
        HARDCODED_IMAGES.add(new ImageData(null, 739, 1080, 1620, "mBBqF9FP_Izvy14utJVVxl2ykbIO307JCHU1KmlKk0U"));
        HARDCODED_IMAGES.add(new ImageData(null, 433, 1080, 1620, "wcV6dSr7FdMuqkgo5DNtkVDnuDvtb8vj6SLLDcZLx2M"));
        HARDCODED_IMAGES.add(new ImageData(null, 822, 1080, 1620, "Eg_XmPghEg9UtOt-cTUhuGNTd3xXL89czxyTz5UDq1U"));
        HARDCODED_IMAGES.add(new ImageData(null, 361, 1080, 1620, "bchfqzoGnvwdMvpWCt9ZHNvT-Wvi2nO4X43MTqbXcFQ"));
        HARDCODED_IMAGES.add(new ImageData(null, 993, 1080, 1620, "GnCR-SMXiAtZbDrV7PhyXzs0aExsc-uXlAYxwEu6pFw"));
        HARDCODED_IMAGES.add(new ImageData(null, 383, 1080, 1620, "X3C5yr69uyLi5NSSI-5iSlLXsT2wArWA5yqmjUeLZpE"));
        HARDCODED_IMAGES.add(new ImageData(null, 900, 1080, 1620, "T1o28Ap_Rmf6lMVboR9Cl7Dpl2SeoAVJi3J0SRN6UdA"));
        HARDCODED_IMAGES.add(new ImageData(null, 130, 1080, 1620, "0n1BLxOi4qd-RbqO__tjtXRolaxPmQbMz34ZsDa78ak"));
        HARDCODED_IMAGES.add(new ImageData(null, 980, 1080, 1620, "yS1_s4AhNnqT5pKyiChLb-zItuow04k7NgR3nCuLc2g"));
        HARDCODED_IMAGES.add(new ImageData(null, 975, 1080, 1620, "6Sp78D6qGpsxipmNsKgxJfgCO2VmOsOpD_5KFmIBztg"));
        HARDCODED_IMAGES.add(new ImageData(null, 637, 1080, 1620, "C-n3rYi0_8wjubkvdSgrMWIiRCJix1Ku48jAEGBpGas"));
        HARDCODED_IMAGES.add(new ImageData(null, 959, 1080, 1620, "duMpDLjSPv3cX9G4S7fsIem3rs0Xvhevm8FsDHVsr68"));
        HARDCODED_IMAGES.add(new ImageData(null, 813, 1080, 1620, "j9lo6q_wtC4dn0KWYLwHugR9u1nark266nu--jkBEpU"));
        HARDCODED_IMAGES.add(new ImageData(null, 388, 1080, 1620, "YHqylo4-IOFxIzq9wmDsCLp7ZR3_vdpVn397j6Xrhv0"));
        HARDCODED_IMAGES.add(new ImageData(null, 166, 1080, 1620, "MI2AQJwxqrZxxeSgLehkwteqVBLEb6spjW1GtmoFMyc"));
        HARDCODED_IMAGES.add(new ImageData(null, 617, 1080, 1620, "n--z6BM1r1OetJVT3E5zs6zbM4mD4MRrLOKde4DEwyI"));
        HARDCODED_IMAGES.add(new ImageData(null, 325, 1080, 1620, "HRAzFYkfK_b005v7Lmy6z01ZeMXGAJ0oi24My5SNoJo"));
        HARDCODED_IMAGES.add(new ImageData(null, 266, 1080, 1620, "dS1X9SaDx4GXwjYq5l7nTHtPPzLUuaj5bUImnJenqBg"));
        HARDCODED_IMAGES.add(new ImageData(null, 405, 1080, 1620, "U0U6SN_8Oc62NavsUgDpGXLxLuK-4AB6XnI3W-AE218"));
        HARDCODED_IMAGES.add(new ImageData(null, 196, 1080, 1620, "msovSB4aQs7lYxAYifFgc3saAAuz1JciVDzeO7Icmw8"));
        HARDCODED_IMAGES.add(new ImageData(null, 66, 1080, 1620, "pOPl22SEF6KSCoO5H0LIrKqzuv0yxlpWZdFvOHqv58Y"));
        HARDCODED_IMAGES.add(new ImageData(null, 234, 1080, 1620, "_HR38aelqXxA1Y_X-JmAJE04EYZ53SLwQsr5ktpHSos"));
        HARDCODED_IMAGES.add(new ImageData(null, 1060, 1080, 1620, "MqkTioAHWoHEBJotrxfvmfyhZGl18Fo6n_hY2pRUC4Q"));
        HARDCODED_IMAGES.add(new ImageData(null, 239, 1080, 1620, "fAD5Gupx1Yupb-0DrioFRLz-xWfM4JocUMNHnJluVtw"));
        HARDCODED_IMAGES.add(new ImageData(null, 903, 1080, 1620, "bhUMraEcsOiW6Lib0UVtqnE7l4-r5rMYWoc0Ho-KU4Y"));
        HARDCODED_IMAGES.add(new ImageData(null, 701, 1080, 1620, "r_bMTU9UGl70b1uTNmGzhdT0URpuq7gJO0p7Y_Ztwug"));
        HARDCODED_IMAGES.add(new ImageData(null, 598, 1080, 1620, "VYgb_T1Jqovhln3d2dcWJLB5YE1A1DxmSS5CHynqJJw"));
        HARDCODED_IMAGES.add(new ImageData(null, 935, 1080, 1620, "IKx-G6_XnVdELC_kYtgjUl6tIq2yZm4hLT93JqK6ynI"));
        HARDCODED_IMAGES.add(new ImageData(null, 907, 1080, 1620, "o4YqBfXRTRrcLd2zJsRpbQ80hWF9Nps9aW7dfVh4OHI"));
        HARDCODED_IMAGES.add(new ImageData(null, 137, 1080, 1620, "ODOgcqEuucw1wV9HOvxDYFMO4gafXK5ibbCH9lezbaY"));
        HARDCODED_IMAGES.add(new ImageData(null, 820, 1080, 1620, "EXieP__s3jWlb-7jyH72BHI3RRsKoEBaM8myPBGb4ac"));
        HARDCODED_IMAGES.add(new ImageData(null, 231, 1080, 1620, "XZOT14pGwQVe22PZmS30Q4MUODJBThZhdrQ2lXlyBOk"));
        HARDCODED_IMAGES.add(new ImageData(null, 40, 1080, 1620, "aUu2ycziYUr1FhP5_wRvCnUkXEIlZEZs8e_lZOr7CIc"));
        HARDCODED_IMAGES.add(new ImageData(null, 528, 1080, 1620, "3g9lfmStoMgqQRKRGUufSDowZHk73hZzFhglkjQIsmw"));
    }

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
    int idx = 0;
    private Request storedRequest;

    public ImageLoader(int width) {
        this.width = width;
        this.height = (int) (1.5 * width);
        randomRequest = new Request.Builder()
                .url(String.format(RANDOM_IMAGE_URL_PATTERN, this.width, this.height))
//                        .url("https://source.unsplash.com/random")
                .get()
                .build();
    }

    public ImageData loadImageData(int id, String hmac) {
        Callable<ImageData> callable = new StoredImageLoadingCallable(client, id, width, height, hmac);
        Future<ImageData> future = Executors.newSingleThreadExecutor().submit(callable);
        try {
            ImageData imageData = future.get();
            return imageData;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ImageData> getNext5() {
        if (idx + 5 >= HARDCODED_IMAGES.size()) {
            return getRandomImages(5);
        }
        List<ImageData> dataList = HARDCODED_IMAGES.subList(idx, idx + 5);
        idx += 5;
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
