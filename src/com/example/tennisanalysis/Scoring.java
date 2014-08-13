package com.example.tennisanalysis;

import java.util.ArrayList;

import android.R.anim;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Scoring extends Activity {

	private Button btnEnter;
	private EditText edtScorer;
	private EditText edtLocation;
	private String scorerString;
	private String locationString;
	private String mplaceString;

	private Spinner spinner;
	private String[] placeString = { "Clay", "Hard", "Grass", "Carpet", "Wood" };
	private ArrayAdapter<String> placeAdapter;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scoring);

		initControl_Scoring();
		btnEnter.setOnClickListener(btnEnterOnClick);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scoring, menu);
		return true;
	}

	public void initControl_Scoring() {
		btnEnter = (Button) findViewById(R.id.btnEnter);
		edtScorer = (EditText)findViewById(R.id.edtScorer);
		edtLocation = (EditText)findViewById(R.id.edtLocation);

		mContext = this.getApplicationContext();
		spinner = (Spinner) findViewById(R.id.spinner);
		placeAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, placeString);
		spinner.setAdapter(placeAdapter);
		spinner.setOnItemSelectedListener(itemSelectedListener);
	}

	private Button.OnClickListener btnEnterOnClick = new Button.OnClickListener() {
		public void onClick(View v) {
			Intent Scoring_Player = new Intent();
			Bundle bundle =new Bundle();
			scorerString = edtScorer.getText().toString();
			locationString = edtLocation.getText().toString();
			bundle.putString("Scorer", scorerString);
			bundle.putString("Location", locationString);
			bundle.putString("Place", mplaceString);
			Scoring_Player.putExtras(bundle);
			Scoring_Player.setClass(Scoring.this, Player.class);
			startActivity(Scoring_Player);
			finish();
		}
	};

	private Spinner.OnItemSelectedListener itemSelectedListener = new Spinner.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			mplaceString = spinner.getSelectedItem().toString();
			//Log.i("Item",mplaceString);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};
}
