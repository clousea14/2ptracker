package com.example.studentprojectwork;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Co_PrefsActivity extends PreferenceActivity{

@SuppressWarnings("deprecation")
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
addPreferencesFromResource(R.xml.preference);
}
}