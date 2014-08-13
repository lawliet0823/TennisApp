package com.example.tennisanalysis;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Player extends Activity {

	public Socket socket;
	private Button btnStart;
	private Button btnPlayer1;
	private Button btnPlayer2;
	private EditText edtPlayer1;
	private EditText edtPlayer2;
	private TextView txtPlayer1Set;
	private TextView txtPlayer2Set;
	private TextView txtScore;
	private TextView txtGame;
	private Handler handler;
	private String Player1Name;
	private String Player2Name;
	private String setString = "0:0";
	private String gameString = "0:0";
	private String Scorer;
	private String Location;
	private String Place;
	private int Player1Set = 0;
	private int Player2Set = 0;
	private int Player1Game = 0;
	private int Player2Game = 0;
	private int Player1Score = 0;
	private int Player2Score = 0;
	private static final int button_Hide = 0;
	private static final int score_Change = 1;
	private static final int adv_Player1 = 2;
	private static final int adv_Player2 = 3;
	private static final int set = 4;
	private static final int reset = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);

		initControl_Player();
		btnStart.setOnClickListener(btnStartOnClick);
		btnPlayer1.setOnClickListener(btnPlayer1OnClick);
		btnPlayer2.setOnClickListener(btnPlayer2OnClick);

		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case button_Hide:
					btnStart.setVisibility(View.GONE);
					edtPlayer1.setVisibility(View.GONE);
					edtPlayer2.setVisibility(View.GONE);
					txtScore.setVisibility(View.VISIBLE);
					txtPlayer1Set.setVisibility(View.VISIBLE);
					txtPlayer2Set.setVisibility(View.VISIBLE);
					btnPlayer1.setVisibility(View.VISIBLE);
					btnPlayer2.setVisibility(View.VISIBLE);
					break;
				case score_Change:
					txtPlayer1Set.setText(String.valueOf(Player1Score));
					txtPlayer2Set.setText(String.valueOf(Player2Score));
					break;
				case adv_Player1:
					txtPlayer1Set.setText("A");
					break;
				case adv_Player2:
					txtPlayer2Set.setText("A");
					break;
				case set:
					txtScore.setText(setString);
					break;
				case reset:
					txtPlayer1Set.setText("0");
					txtPlayer2Set.setText("0");
					txtScore.setText("0:0");
					txtGame.setText(gameString);
					break;
				default:
					break;

				}
				super.handleMessage(msg);
			}
		};

		Thread serverThread = new Thread(new serverConnect());
		serverThread.start();
	}

	class serverConnect implements Runnable {
		public void run() {
			try {
				socket = new Socket(InetAddress.getByName("140.115.204.146"),
						51706);

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player, menu);
		return true;
	}

	public void initControl_Player() {
		btnStart = (Button) findViewById(R.id.btnStart);
		btnPlayer1 = (Button) findViewById(R.id.btnPlayer1);
		btnPlayer2 = (Button) findViewById(R.id.btnPlayer2);
		edtPlayer1 = (EditText) findViewById(R.id.edtPlayer1);
		edtPlayer2 = (EditText) findViewById(R.id.edtPlayer2);
		txtPlayer1Set = (TextView) findViewById(R.id.txtPlayer1Set);
		txtPlayer2Set = (TextView) findViewById(R.id.txtPlayer2Set);
		txtScore = (TextView) findViewById(R.id.txtScore);
		txtGame = (TextView) findViewById(R.id.txtGame);

		Bundle bundlePlayer = this.getIntent().getExtras();
		Scorer = bundlePlayer.getString("Scorer");
		Location = bundlePlayer.getString("Location");
		Place = bundlePlayer.getString("Place");
	}

	private Button.OnClickListener btnStartOnClick = new Button.OnClickListener() {
		public void onClick(View v) {
			Player1Name = edtPlayer1.getText().toString();
			Player2Name = edtPlayer2.getText().toString();

			Message msgButtonHide = new Message();
			msgButtonHide.what = button_Hide;
			Player.this.handler.sendMessage(msgButtonHide);
		}
	};

	private Button.OnClickListener btnPlayer1OnClick = new Button.OnClickListener() {
		public void onClick(View v) {
			boolean change = true;
			if (Player1Score == 30) {
				Player1Score += 10;
			} else if (Player1Score == 40 && (Player2Score < 40)) {
				Player1Score = 0;
				Player2Score = 0;
				Player1Set += 1;
				setSet();
			} else if (Player1Score == 40 && Player2Score == 40
					&& !(txtPlayer1Set.getText().toString()).equals("A")
					&& !(txtPlayer2Set.getText().toString()).equals("A")) {
				change = false;
			} else if ((txtPlayer1Set.getText().toString()).equals("A")) {
				Player1Score = 0;
				Player2Score = 0;
				Player1Set += 1;
				setSet();
			} else if ((txtPlayer2Set.getText().toString()).equals("A")) {
				Player1Score = 40;
				Player2Score = 40;
			} else {
				Player1Score += 15;
			}
			Message msgPlayer1 = new Message();
			if (change == true) {
				msgPlayer1.what = score_Change;
				Player.this.handler.sendMessage(msgPlayer1);
			} else {
				msgPlayer1.what = adv_Player1;
				Player.this.handler.sendMessage(msgPlayer1);
				change = true;
			}
			
			DataOutputStream writer;
			try {

				//default
				Player1Name = "player1";
				Player2Name = "player2";
				writer = new DataOutputStream( socket.getOutputStream());
				writer.writeUTF(
						Player1Name+";"+Player2Name+";"
								+ String.valueOf(Player1Score) + ":"
								+ String.valueOf(Player2Score) + ";"
								+setString+";"+gameString+";"
								+ Scorer + ";" + Location + ";" + Place);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};

	private Button.OnClickListener btnPlayer2OnClick = new Button.OnClickListener() {
		public void onClick(View v) {
			boolean change = true;
			if (Player2Score == 30) {
				Player2Score += 10;
			} else if (Player2Score == 40 && (Player1Score < 40)) {
				Player1Score = 0;
				Player2Score = 0;
				Player2Set += 1;
				setSet();
			} else if (Player1Score == 40 && Player2Score == 40
					&& !(txtPlayer1Set.getText().toString()).equals("A")
					&& !(txtPlayer2Set.getText().toString()).equals("A")) {
				change = false;
			} else if ((txtPlayer2Set.getText().toString()).equals("A")) {
				Player1Score = 0;
				Player2Score = 0;
				Player2Set += 1;
				setSet();
			} else if ((txtPlayer1Set.getText().toString()).equals("A")) {
				Player1Score = 40;
				Player2Score = 40;
			} else {
				Player2Score += 15;
			}
			Message msgPlayer2 = new Message();
			if (change == true) {
				msgPlayer2.what = score_Change;
				Player.this.handler.sendMessage(msgPlayer2);
			} else {
				msgPlayer2.what = adv_Player2;
				Player.this.handler.sendMessage(msgPlayer2);
				change = true;
			}
			
			//default
			Player1Name = "player1";
			Player2Name = "player2";
			/*
			OutputStream out;
			try {
				out = socket.getOutputStream();
				byte buffer[] = new byte[2048];
				buffer = (Player1Name+";"+Player2Name+";"
						+ String.valueOf(Player1Score) + ":"
						+ String.valueOf(Player2Score) + ";"
						+ String.valueOf(Player1Game) + ":"
						+ String.valueOf(Player2Game) + ";"
						+ Scorer + ";" + Location + ";" + Place).getBytes();
				out.write( buffer );
			} catch (Exception e) {
				// TODO: handle exception
			}
			*/
			DataOutputStream writer;
			try {
				writer = new DataOutputStream(socket.getOutputStream());
				writer.writeUTF(
						Player1Name+";"+Player2Name+";"
								+ String.valueOf(Player1Score) + ":"
								+ String.valueOf(Player2Score) + ";"
								+setString+";"+gameString+";"
								+ Scorer + ";" + Location + ";" + Place);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	};

	public void setSet() {
		Message msgSet = new Message();

		if (Player1Set == 6 || Player2Set == 6) {
			if (Player1Set == 6) {
				Player1Game += 1;
			}
			if (Player2Set == 6) {
				Player2Game += 1;
			}

			if (Player1Game == 3 || Player2Game == 3) {
				Toast.makeText(this, "Game finish", Toast.LENGTH_LONG).show();
				Intent trans = new Intent();
				trans.setClass(Player.this, MainActivity.class);
				startActivity(trans);
				finish();
			}

			Player1Score = 0;
			Player2Score = 0;
			Player1Set = 0;
			Player2Set = 0;

			gameString = String.valueOf(Player1Game) + ":"
					+ String.valueOf(Player2Game);
			msgSet.what = reset;
			Player.this.handler.sendMessage(msgSet);
			
			//default
			Player1Name = "player1";
			Player2Name = "player2";
			/*
			OutputStream out;
			try {
				out = socket.getOutputStream();
				byte buffer[] = new byte[2048];
				buffer = (Player1Name+";"+Player2Name+";"
						+ String.valueOf(Player1Score) + ":"
						+ String.valueOf(Player2Score) + ";"
						+ String.valueOf(Player1Game) + ":"
						+ String.valueOf(Player2Game) + ";"
						+ Scorer + ";" + Location + ";" + Place).getBytes();
				out.write( buffer );
			} catch (Exception e) {
				// TODO: handle exception
			}
			*/
			
			DataOutputStream writer;
			try {
				writer = new DataOutputStream(socket.getOutputStream());
				writer.writeUTF(
						Player1Name+";"+Player2Name+";"
						+ String.valueOf(Player1Score) + ":"
						+ String.valueOf(Player2Score) + ";"
						+setString+";"+gameString+";"
						+ Scorer + ";" + Location + ";" + Place);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			setString = "";
			setString = String.valueOf(Player1Set) + ":"
					+ String.valueOf(Player2Set);
			msgSet.what = set;
			Player.this.handler.sendMessage(msgSet);
			/*
			OutputStream out;
			try {
				out = socket.getOutputStream();
				byte buffer[] = new byte[2048];
				buffer = (Player1Name+";"+Player2Name+";"
						+ String.valueOf(Player1Score) + ":"
						+ String.valueOf(Player2Score) + ";"
						+ String.valueOf(Player1Game) + ":"
						+ String.valueOf(Player2Game) + ";"
						+ Scorer + ";" + Location + ";" + Place).getBytes();
				out.write( buffer );
			} catch (Exception e) {
				// TODO: handle exception
			}
			*/
			//default
			Player1Name = "player1";
			Player2Name = "player2";
			DataOutputStream writer;
			try {
				writer = new DataOutputStream(socket.getOutputStream());
				writer.writeUTF(
						Player1Name+";"+Player2Name+";"
						+ String.valueOf(Player1Score) + ":"
						+ String.valueOf(Player2Score) + ";"
						+setString+";"+gameString+";"	
						+ Scorer + ";" + Location + ";" + Place);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
