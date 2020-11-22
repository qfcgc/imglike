package com.example.imglike.presenter;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.imglike.R;
import com.example.imglike.model.Partner;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class PartnerPresenter {
    private final View root;
    private final ImageView appIcon;
    private final TextView appName;
    private final TextView appDescription;
    private final Partner model;
    private final PackageManager pm;
    private final StartActivityCallback startActivityCallback;

    public PartnerPresenter(View root, Partner model, PackageManager pm, StartActivityCallback startActivityCallback) {
        this.root = root;
        this.appName = root.findViewById(R.id.app_name);
        this.appDescription = root.findViewById(R.id.app_description);
        this.appIcon = root.findViewById(R.id.app_icon);
        this.model = model;
        this.pm = pm;
        this.startActivityCallback = startActivityCallback;
    }

    public void fillAppData() {
        try {
            fillWithPackageManagerProvidedInfo(pm.getApplicationInfo(model.getPackageName(), 0));
        } catch (final PackageManager.NameNotFoundException e) {
            fillWithDefaultInfo();
        }
    }

    private void fillWithDefaultInfo() {
        appName.setText(model.getName());
        appDescription.setText(model.getDescription());

        root.setOnClickListener(v -> {
            Intent launchIntent = pm.getLaunchIntentForPackage(model.getPackageName());
            if (launchIntent != null) {
                startActivityCallback.startActivity(launchIntent);
            } else {
                startActivityCallback.startActivity(
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=" + model.getPackageName())
                        )
                );
            }
        });
    }

    private void fillWithPackageManagerProvidedInfo(ApplicationInfo appInfo) {
        CharSequence charSequenceAppName = pm.getApplicationLabel(appInfo);
        appName.setText(charSequenceAppName != null ? charSequenceAppName : model.getName());

        CharSequence appDesc = appInfo.loadDescription(pm);
        appDescription.setText(appDesc != null ? appDesc : model.getDescription());

        Drawable drawable = appInfo.loadIcon(pm);
        if (drawable != null) {
            appIcon.setImageDrawable(drawable);
        } else {
            appIcon.setImageResource(R.drawable.app_icon);
        }

        root.setOnClickListener(v -> {
            Intent launchIntent = pm.getLaunchIntentForPackage(model.getPackageName());
            if (launchIntent != null) {
                startActivityCallback.startActivity(launchIntent);
            } else {
                startActivityCallback.startActivity(
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=" + model.getPackageName())
                        )
                );
            }
        });
    }

    public interface StartActivityCallback {
        void startActivity(Intent intent);
    }
}
