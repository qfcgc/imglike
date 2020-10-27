package com.example.imglike.deprecated;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

@Deprecated
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Random", "Liked" };
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override public int getCount() {
        return PAGE_COUNT;
    }

    @Override public Fragment getItem(int position) {
        if (position == 0) {
            return AllImagesFragment.getNewImages();
        } else {
            return StoredImagesFragment.getStoredImages();
        }
    }

    @Override public CharSequence getPageTitle(int position) {
        // генерируем заголовок в зависимости от позиции
        return tabTitles[position];
    }
}
