package com.kuwatatsu;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MasaTanaoroshi extends ListActivity implements OnItemClickListener {
	private static final int TOROKU = 0;
	private static final int KAKUNIN = 1;
	private static final int CLEAR = 2;
	
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
			break;
		case CLEAR:
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