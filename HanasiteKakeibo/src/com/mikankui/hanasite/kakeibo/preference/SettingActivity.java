package com.mikankui.hanasite.kakeibo.preference;import com.mikankui.hanasite.kakeibo.R;import android.content.pm.ActivityInfo;import android.os.Bundle;import android.preference.EditTextPreference;import android.preference.PreferenceActivity;public class SettingActivity extends PreferenceActivity{	private EditTextPreference name;	// Activity作成時に実行	   public void onCreate(Bundle savedInstanceState) 	   {	      super.onCreate(savedInstanceState);	      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	      addPreferencesFromResource(R.layout.setting);	 	   }}