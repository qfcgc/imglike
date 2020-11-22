package com.example.imglike.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.imglike.R;
import com.example.imglike.model.ImageData;
import com.example.imglike.service.BitmapIntentHelperUtil;
import com.example.imglike.presenter.ImageDataPresenter;

public class ImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);
        new ImageDataPresenter(getWindow().getDecorView().findViewById(android.R.id.content), loadModel()).draw();
    }

    private ImageData loadModel() {
        String id = getIntent().getStringExtra("id");
        Bitmap bitmap = BitmapIntentHelperUtil.restoreBitmap(id);

        return new ImageData(bitmap, new ImageData.ImageMetadata(id));
    }
}
