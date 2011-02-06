package com.kuwatatsu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
	public void onClick(View arg0) {
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
			bw.write(shohinNo.getText() + "," + (String)spinner.getSelectedItem() + "\n");
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
