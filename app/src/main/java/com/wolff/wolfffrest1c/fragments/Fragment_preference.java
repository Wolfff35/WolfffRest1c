package com.wolff.wolfffrest1c.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.wolff.wolfffrest1c.R;

/**
 * Created by wolff on 28.02.2017.
 */

public class Fragment_preference extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_general);
    }
}
