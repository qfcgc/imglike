package com.example.imglike;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

public class ImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        ImageView imageView = findViewById(R.id.image);
        Bitmap bitmap = loadBitmap();
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

    private Bitmap loadBitmap() {
        Bitmap bmp = null;
        String filename = getIntent().getStringExtra("image");
        try {
            FileInputStream is = this.openFileInput(filename);
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }
}
