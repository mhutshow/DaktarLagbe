package com.mhutshow.daktarlagbe.model.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mhutshow.daktarlagbe.controller.ConsultationFragmentPage;
import com.mhutshow.daktarlagbe.controller.Hospitalisation;

public class ConsultationFragmentAdapter extends FragmentPagerAdapter {
    // 1 - Array of colors that will be passed to PageFragment
    private int[] colors;

    // 2 - Default Constructor
    public ConsultationFragmentAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public int getCount() {
        return(2); // 3 - Number of page to show
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: //Page number 1
                return ConsultationFragmentPage.newInstance();
            case 1: //Page number 2
                return Hospitalisation.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: //Page number 1
                return "Consultation";
            case 1: //Page number 2
                return "Hospitalisation";
            default:
                return null;
        }
    }
}
