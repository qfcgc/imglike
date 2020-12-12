package com.example.imglike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.imglike.R;
import com.example.imglike.presenter.ScrollPresenter;

public class MainActivity extends AppCompatActivity {
    private ScrollPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new ScrollPresenter(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean res = super.onCreateOptionsMenu(menu);
        menu.clear();
        new MenuInflater(getApplicationContext()).inflate(R.menu.right_menu, menu);
        return res;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean res = super.onOptionsItemSelected(item);

        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, PartnersActivity.class);
        startActivityForResult(intent, 10, bundle);

        return res;
    }
}
