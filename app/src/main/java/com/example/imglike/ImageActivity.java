package com.example.imglike;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "com.example.imglike.extra.REPLY";
    private boolean likeFlipped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);

        View likeView = findViewById(R.id.image_like);
        likeView.setOnClickListener(this::returnReply);
//
//        Intent intent = getIntent();
//        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
//        TextView textView = findViewById(R.id.text_message);
//        textView.setText(message);
    }

    public void returnReply(View view) {
        if (likeFlipped) {
            likeFlipped = false;
            Intent replyIntent = new Intent();
            replyIntent.putExtra("LIKED_STATE_CHANGED", true);
            setResult(RESULT_OK, replyIntent);
        }
//        String reply = mReply.getText().toString();
//        Intent replyIntent = new Intent();
//        replyIntent.putExtra(EXTRA_REPLY, reply);
//        setResult(RESULT_OK,replyIntent);
//        finish();
    }
}
