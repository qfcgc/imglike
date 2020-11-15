package com.example.imglike.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.imglike.service.BitmapIntentHelperUtil;
import com.example.imglike.service.ImageLikedStatusUtilService;
import com.example.imglike.R;

import java.io.FileInputStream;

public class ImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        ImageView imageView = findViewById(R.id.image);
        Bitmap bitmap = BitmapIntentHelperUtil.restoreBitmap(id);
        imageView.setImageBitmap(bitmap);

        ImageView likeView = findViewById(R.id.image_like);
        if (ImageLikedStatusUtilService.isLiked(id)) {
            likeView.setImageResource(R.drawable.ic_like_enable);
        }
        likeView.setOnClickListener(v -> {
            if (!ImageLikedStatusUtilService.isLiked(id)) {
                ImageLikedStatusUtilService.refreshLiked(id, true);
                likeView.setImageResource(R.drawable.ic_like_enable);
            } else {
                ImageLikedStatusUtilService.refreshLiked(id, false);
                likeView.setImageResource(R.drawable.ic_like_disable);
            }
        });
    }
}
