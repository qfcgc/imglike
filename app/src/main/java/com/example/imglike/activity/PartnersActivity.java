package com.example.imglike.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.imglike.R;

public class PartnersActivity extends AppCompatActivity {
    private PackageManager pm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partners_layout);
        pm = getApplicationContext().getPackageManager();

        ScrollView scrollView = findViewById(R.id.partners);

        LinearLayout onlyChild = new LinearLayout(getApplicationContext());
        onlyChild.setOrientation(LinearLayout.VERTICAL);

        onlyChild.addView(addViewToList("com.android.chrome", "Chrome", "Good Browser"));
        onlyChild.addView(addViewToList("com.alibaba.aliexpresshd", "AliExpress", "Good Market"));
        onlyChild.addView(addViewToList("com.citymobil", "Citymobil", "Good Taxi"));
        onlyChild.addView(addViewToList("com.facebook.katana", "Facebook", "Facebook app"));
        onlyChild.addView(addViewToList("com.test", "Test App", "Test description"));

        scrollView.addView(onlyChild);
    }

    private View addViewToList(String packageName, String appNameDefault, String appDescriptionDefault) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.partner_layout, null);


        ApplicationInfo appInfo;
        try {
            appInfo = pm.getApplicationInfo(packageName, 0);
        } catch (final PackageManager.NameNotFoundException e) {
            appInfo = null;
        }

        fillData(root, appInfo, packageName, appNameDefault, appDescriptionDefault);

        return root;
    }

    private void fillData(View root, ApplicationInfo appInfo, String packageName,
                          String appNameDefault, String appDescriptionDefault) {
        if (appInfo == null) {
            fillData(root, packageName, appNameDefault, appDescriptionDefault);
            return;
        }

        CharSequence charSequenceAppName = pm.getApplicationLabel(appInfo);
        TextView appNameView = root.findViewById(R.id.app_name);
        appNameView.setText(charSequenceAppName != null ? charSequenceAppName : appNameDefault);

        TextView appDescView = root.findViewById(R.id.app_description);
        CharSequence appDesc = appInfo.loadDescription(pm);
        appDescView.setText(appDesc != null ? appDesc : appDescriptionDefault);

        Drawable drawable = appInfo.loadIcon(pm);
        ImageView appIconView = root.findViewById(R.id.app_icon);
        if (drawable != null) {
            appIconView.setImageDrawable(drawable);
        } else {
            appIconView.setImageResource(R.drawable.app_icon);
        }

        root.setOnClickListener(v -> {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
            if (launchIntent != null) {
                startActivity(launchIntent);
            } else {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
            }
        });
    }

    private void fillData(View root, String packageName,
                          String appNameDefault, String appDescriptionDefault) {
        TextView appNameView = root.findViewById(R.id.app_name);
        appNameView.setText(appNameDefault);

        TextView appDescView = root.findViewById(R.id.app_description);
        appDescView.setText(appDescriptionDefault);

        root.setOnClickListener(v -> {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
            if (launchIntent != null) {
                startActivity(launchIntent);
            } else {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
            }
        });
    }
}
