package com.example.tennisanalysis;

import android.net.Uri;
import android.os.Bundle;
import android.R.anim;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button btnCreate;
	private Button btnWeb;
	private Button btnExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initControl();
		btnCreate.setOnClickListener(btnCreateOnClick);
		btnWeb.setOnClickListener(btnWebOnClick);
		btnExit.setOnClickListener(btnExitOnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void initControl() {
		btnCreate = (Button) findViewById(R.id.btnCreate);
		btnWeb = (Button) findViewById(R.id.btnWeb);
		btnExit = (Button) findViewById(R.id.btnExit);
	}

	private Button.OnClickListener btnCreateOnClick = new Button.OnClickListener() {
		public void onClick(View v) {
			Intent Main_Scoring = new Intent();
			Main_Scoring.setClass(MainActivity.this, Scoring.class);
			startActivity(Main_Scoring);
			finish();
		}
	};

	private Button.OnClickListener btnWebOnClick = new Button.OnClickListener() {
		public void onClick(View v) {
			String urlString = "http://140.115.204.146/index.html";
			Intent go_to_web = new Intent(Intent.ACTION_VIEW,
					Uri.parse(urlString));
			startActivity(go_to_web);
			finish();
		}
	};

	private Button.OnClickListener btnExitOnClick = new Button.OnClickListener() {
		public void onClick(View v) {
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	};
}
