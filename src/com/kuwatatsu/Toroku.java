package com.kuwatatsu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
		finish();
	}

}
