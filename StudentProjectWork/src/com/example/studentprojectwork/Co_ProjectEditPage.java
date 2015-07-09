package com.example.studentprojectwork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Co_ProjectEditPage extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

//		setContentView(R.layout.ui_projecteditpage);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		//set content view AFTER ABOVE sequence (to avoid crash)
		this.setContentView(R.layout.ui_projecteditpage);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Co_ProjectViewPage.pos=0;
	}
}
