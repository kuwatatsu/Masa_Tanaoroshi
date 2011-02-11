package com.kuwatatsu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class Kakunin extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kakunin);

		String fileName = "/sdcard/" + getPackageName() + "/sample.txt";  

		StringBuffer stringBuffer = null;

		File file = new File(fileName);  
		file.getParentFile().mkdir();  

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			return;
		}  
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);
			stringBuffer = new StringBuffer();
			String line = null;
			while((line = br.readLine()) != null) {
				stringBuffer.append(line).append("\n");
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  

		TextView textView = (TextView)findViewById(R.id.kakunin_view);
		textView.setText(stringBuffer.toString());

		
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
	
}
