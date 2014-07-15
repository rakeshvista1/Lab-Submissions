package com.ankit.funnyboat;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	Player player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d("RAKESH", "in MainActivity Oncreate before");
		
		Game.start(this);
		
		startService(new Intent(this,ConnectionService.class));
		
		//inserted code
		
		player = new Player();
		
		registerReceiver(receiver, new IntentFilter("myproject"));
		
		Log.d("RAKESH", "in MainActivity Oncreate after");
		
		//inserted code
	}

	@Override
	protected void onResume() {
		if (Game.current != null)
			Game.instance.resume();
		super.onResume();
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {

				// extra data inserted into the fired intent
				String data = bundle.getString("data");
				Log.d("RAKESH", "on MainActivity onReceive bundle not null" +data);

				if ("stomp".equalsIgnoreCase(data)) {
					player.jump();
					Log.d("RAKESH", "in funnyboat jump1");
				}
				// Toast.makeText(getApplicationContext(), "Ok",
				// Toast.LENGTH_SHORT).show();
			} else {
				Log.d("RAKESH", "on MainActivity onReceive bundle null");
				// Toast.makeText(getApplicationContext(), "not",
				// Toast.LENGTH_SHORT).show();
			}
			// handleResult(bundle);
		}
	};
	
	void setPlayer(Player player){
		this.player = player;
	}
}