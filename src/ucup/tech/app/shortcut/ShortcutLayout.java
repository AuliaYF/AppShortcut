package ucup.tech.app.shortcut;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings.System;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class ShortcutLayout extends LinearLayout{
	public Context mContext;
	private HorizontalScrollView mScrollView;
	private LinearLayout mButtonHolder;
	private Button mButton;
	private final int btnWidth = 32;
	private final int btnHeight = 32;

	public ShortcutLayout(Context context, AttributeSet set) {
		super(context, set);
		this.mContext = context;
		this.mScrollView = new HorizontalScrollView(context);
		this.mScrollView.setBackgroundColor(Color.TRANSPARENT);
		this.mScrollView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		this.mButtonHolder = new LinearLayout(context);
		this.mButtonHolder.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 32));
		this.mButtonHolder.setOrientation(LinearLayout.HORIZONTAL);
		this.mScrollView.addView(mButtonHolder);
		this.addView(mScrollView);
		initialize();
		this.mContext.registerReceiver(new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				addButton(intent.getStringExtra("btnName"), intent.getStringExtra("pName"));
			}
		}, new IntentFilter("_test_"));
	}
	private void initialize(){
		String buttons = System.getString(mContext.getContentResolver(), "buttons");
		buttons = buttons.substring(0, buttons.length() - 1);
		/*Toast.makeText(mContext, buttons, Toast.LENGTH_LONG).show();*/
		for (String row : buttons.split("\\|")) {
			/*Toast.makeText(mContext, row, Toast.LENGTH_LONG).show();*/
			String[] button = row.split("\\,");
			String btnName = button[0];
			String pName = button[1];
			addButton(btnName, pName);
		}
	}
	private void addButton(String btnName, String pName){
		mButton = new Button(mContext);
		mButton.setLayoutParams(new LayoutParams(btnWidth, btnHeight));
		mButton.setText(btnName);
		mButton.setTag(pName);
		mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				try{
					Intent LaunchIntent = ShortcutLayout.this.mContext.getPackageManager().getLaunchIntentForPackage(arg0.getTag().toString());
					ShortcutLayout.this.mContext.startActivity(LaunchIntent);
				}catch(Exception e){
					Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
				}

			}
		});
		mButtonHolder.addView(mButton);
	}
}
