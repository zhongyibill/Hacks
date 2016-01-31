package com.zyhust.hack04;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
        Preference ratePref = findPreference("pref_rate");
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW,uri);
        ratePref.setIntent(goToMarket);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册Preference变化通知监听器
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注销Preference变化通知监听器
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("pref_username")){
            updateUserText();
        }
    }

    public void updateUserText(){
        EditTextPreference pref;
        pref = (EditTextPreference) findPreference("pref_username");
        String user=pref.getText();
        if (user== null){
            user="?";
        }
        pref.setSummary(String.format("Username:%s",user));
    }
}
