package com.example.imglike.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.imglike.R;
import com.example.imglike.model.Partner;
import com.example.imglike.presenter.PartnerPresenter;

import java.util.ArrayList;
import java.util.List;

public class PartnersActivity extends AppCompatActivity {
    private static final List<Partner> DEFAULT_PARTNERS = new ArrayList<>();

    static {
        DEFAULT_PARTNERS.add(new Partner("com.android.chrome", "Chrome", "Good Browser"));
        DEFAULT_PARTNERS.add(new Partner("com.alibaba.aliexpresshd", "AliExpress", "Good Market"));
        DEFAULT_PARTNERS.add(new Partner("com.citymobil", "Citymobil", "Good Taxi"));
        DEFAULT_PARTNERS.add(new Partner("com.facebook.katana", "Facebook", "Facebook app"));
        DEFAULT_PARTNERS.add(new Partner("com.test", "Test App", "Test description"));
    }

    private PackageManager pm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partners_layout);
        this.pm = getApplicationContext().getPackageManager();

        ScrollView scrollView = findViewById(R.id.partners);
        LinearLayout onlyChild = new LinearLayout(getApplicationContext());
        onlyChild.setOrientation(LinearLayout.VERTICAL);
        DEFAULT_PARTNERS.forEach(partner -> onlyChild.addView(createViewForPartner(partner, pm)));

        scrollView.addView(onlyChild);
    }

    private View createViewForPartner(Partner partner, PackageManager pm) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.partner_layout, null);
        PartnerPresenter partnerPresenter = new PartnerPresenter(root, partner, pm, this::startActivity);
        partnerPresenter.fillAppData();
        return partnerPresenter.getRoot();
    }
}
