package com.example.bluesurge;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshBTDevices();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void refreshBTDevices() {
        ScrollView scroll = (ScrollView) findViewById(R.id.scrollView1);
        LinearLayout list = (LinearLayout) findViewById(R.id.list);
        BluetoothAdapter localBT = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> remoteBT = localBT.getBondedDevices();
        Iterator<BluetoothDevice> it = remoteBT.iterator();
        
        Log.e("MainActivity", "Searching for BT devices");
        while(it.hasNext()) {
        	BTButton button = new BTButton(this);
        	list.addView(button);
            Log.e("MainActivity", "Added button");
        	BluetoothDevice remote = it.next();
        	button.setText(remote.getName());
        	button.setRemoteBT(remote);
        	OnClickListener listen = new OnClickListener()
        	{
        	    @Override
        	    public void onClick(View button)
        	    {
        	    	BTButton btButton = (BTButton)button;
        	        BluetoothAdapter localBT = BluetoothAdapter.getDefaultAdapter();
        	        BluetoothDevice remoteBT = btButton.getRemoteBT();
        	        if( !localBT.isEnabled() && remoteBT == null ) {
        	        	Log.e("MainActivity", "Can't connect, BT is off or device doesn't exist");
        	        } else {
        	        	/*UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        	        	BluetoothSocket BTsocket = null;
        	        	try {
							BTsocket = remoteBT.createRfcommSocketToServiceRecord(uuid);
							BTsocket.connect();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							Log.e("MainActivity", "connect failed");
							return;
						}*/
        	        	
        	        	Intent toggleIntent = new Intent(button.getContext(), ToggleActivity.class);
        	        	toggleIntent.putExtra("REMOTE_BT", remoteBT);
        	    		startActivity(toggleIntent);
        	        }
        	    }
        	};
        	button.setOnClickListener(listen);
        }
    }
}
