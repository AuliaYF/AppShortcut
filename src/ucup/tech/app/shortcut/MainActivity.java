package ucup.tech.app.shortcut;

import android.os.Bundle;
import android.provider.Settings.System;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ShortcutLayout sl = (ShortcutLayout)findViewById(R.id.xxx);
		final EditText et = (EditText)findViewById(R.id.et);
		Button btn = (Button)findViewById(R.id.btn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setAction("_test_");
				i.putExtra("btnName", "Button");
				i.putExtra("pName", et.getText().toString());
				sendBroadcast(i);
				String buttons = System.getString(getContentResolver(), "buttons");
				System.putString(getContentResolver(), "buttons", buttons + "Button" + "," + et.getText().toString() + "|");
			}
		});
	}

}