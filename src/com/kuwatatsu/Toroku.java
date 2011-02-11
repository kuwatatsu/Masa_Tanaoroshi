package com.kuwatatsu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Toroku extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toroku);
		
		Button button = (Button)findViewById(R.id.toroku);
		button.setOnClickListener(this);
		
		Intent intent = getIntent();
		String code = intent.getStringExtra("com.kuwatatsu.CODE");

		TextView textView = (TextView)findViewById(R.id.code);
		textView.setText(code);

	}

    @Override
    protected void onResume() {
    	super.onResume();
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

	
	@Override
	public void onClick(View arg0) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String tenpo = prefs.getString("tenpo", null);
		Date today_date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String today_string = sdf.format(today_date);

		TextView shohinNo = (TextView)findViewById(R.id.code);
		Spinner spinner = (Spinner)findViewById(R.id.spinner1);
		String fileName = "/sdcard/" + getPackageName() + "/sample.txt";  

		File file = new File(fileName);  
		file.getParentFile().mkdir();  

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file, true);
		} catch (FileNotFoundException e) {
			finish();
		}  
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			osw = new OutputStreamWriter(fos, "UTF-8");
			bw = new BufferedWriter(osw);  
			bw.write(shohinNo.getText() + "," + (String)spinner.getSelectedItem() + "," + tenpo + "," + today_string + "\n");
			bw.flush();
			bw.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  

		finish();
	}

}
