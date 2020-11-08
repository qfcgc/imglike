package com.example.imglike;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "com.example.imglike.extra.REPLY";
    private boolean likeFlipped;
    private ImageDataWrapper imageDataWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);


        Intent intent = getIntent();
        int imageId = intent.getIntExtra("imageId", 0);
        int width = intent.getIntExtra("width", 1080);
        String hmac = intent.getStringExtra("hmac");
        boolean liked = intent.getBooleanExtra("liked", false);

        ImageLoader loader = new ImageLoader(width);
        ImageView image = findViewById(R.id.image);
        ImageData imageData = loader.loadImageData(imageId, hmac);
        image.setImageBitmap(imageData.getData());

        imageDataWrapper = new ImageDataWrapper(
                imageData, new SaveLikedState(

                getApplicationContext().getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE),
                imageId, hmac)
        );


        ImageView likeView = findViewById(R.id.image_like);
        if (liked) {
            likeView.setImageResource(R.drawable.ic_like_enable);
        }
        likeView.setOnClickListener(v -> {
            if (!imageDataWrapper.getSaveLikedState().getLiked()) {
                imageDataWrapper.getSaveLikedState().setLiked(true);
                likeView.setImageResource(R.drawable.ic_like_enable);
            } else {
                imageDataWrapper.getSaveLikedState().setLiked(false);
                likeView.setImageResource(R.drawable.ic_like_disable);
            }
        });

    }
}
