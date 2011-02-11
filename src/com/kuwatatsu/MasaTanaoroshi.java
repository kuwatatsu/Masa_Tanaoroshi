package com.kuwatatsu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MasaTanaoroshi extends ListActivity implements OnItemClickListener {
	private static final int TOROKU = 0;
	private static final int KAKUNIN = 1;
	private static final int MAIL = 2;
	private static final int CLEAR = 3;
	private static final int SETTING = 4;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] countries = getResources().getStringArray(R.array.function_array);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, countries));
        
        ListView lv = getListView();

        lv.setOnItemClickListener(this);
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch(position) {
		case TOROKU:
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "ONE_D_MODE");
			try {
                startActivityForResult(intent, 0);
            } catch (ActivityNotFoundException e) {
                new AlertDialog.Builder(MasaTanaoroshi.this)
                        .setTitle("QR Scaner not found.")
                        .setMessage("Please install QR code scanner")
                        .setPositiveButton("OK", null)
                        .show();
            }

			break;
		case KAKUNIN:
			Intent intent_kakunin = new Intent(this, Kakunin.class);
			try {
				startActivity(intent_kakunin);
            } catch (ActivityNotFoundException e) {
                new AlertDialog.Builder(MasaTanaoroshi.this)
                        .setTitle("Kakunin Activity not found.")
                        .setMessage("Error")
                        .setPositiveButton("OK", null)
                        .show();
            }
			break;
		case MAIL:
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			String tenpo = prefs.getString("tenpo", null);
			String mail_addr = prefs.getString("mail_addr", null);
			Date today_date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String today_string = sdf.format(today_date);

			String title = "廃棄商品一覧:日付-" + today_string + ":店舗コード-" + tenpo;
			String body = "廃棄商品一覧:日付-" + today_string + ":店舗コード-" + tenpo;
			String filename = "file://" + "/sdcard/" + getPackageName() + "/sample.txt";
			Uri uri = Uri.parse("mailto:" + mail_addr);
			Intent it = new Intent(Intent.ACTION_SEND);
			String[] addr = {mail_addr};
			it.putExtra(Intent.EXTRA_EMAIL, addr);
			it.putExtra(Intent.EXTRA_SUBJECT, title);
			it.putExtra(Intent.EXTRA_TEXT, body);
			it.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/com.kuwatatsu/sample.txt"));
			it.setType("text/csv");
			startActivity(Intent.createChooser(it, "Choose Email Client")); 
			break;
		case CLEAR:
			String fileName = "/sdcard/" + getPackageName() + "/sample.txt";  

			File file = new File(fileName);
			file.delete();

			break;
		case SETTING:
			Intent intent_setting = new Intent(this, Setting.class);
			try {
				startActivity(intent_setting);
            } catch (ActivityNotFoundException e) {
                new AlertDialog.Builder(MasaTanaoroshi.this)
                        .setTitle("Setting Activity not found.")
                        .setMessage("Error")
                        .setPositiveButton("OK", null)
                        .show();
            }
			break;
		default:
				break;
		}
	}
	
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                final String barcode = intent.getStringExtra("SCAN_RESULT");

    			Intent intent_next = new Intent(this, Toroku.class);
    			intent_next.putExtra("com.kuwatatsu.CODE", barcode);
    			try {
                    startActivityForResult(intent_next, 1);
                } catch (ActivityNotFoundException e) {
                    new AlertDialog.Builder(MasaTanaoroshi.this)
                            .setTitle("Toroku Activity not found.")
                            .setMessage("Error")
                            .setPositiveButton("OK", null)
                            .show();
                }

            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
            }
        }
    }

}