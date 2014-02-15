package com.example.bluesurge;

import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MainActivity extends Activity {

	BluetoothComms connectedBTService = null;
	IBinder mBinder = null;
	
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
        	        	
        	        	ServiceConnection serviceConnect = new ServiceConnection() {

							@Override
							public void onServiceConnected(ComponentName arg0,
									IBinder arg1) {
								// TODO Auto-generated method stub
								Log.e("MainActivity", "Service connected");
								connectedBTService = ((BluetoothComms.LocalBinder)arg1).getService();
								mBinder = arg1;
							}

							@Override
							public void onServiceDisconnected(ComponentName name) {
								// TODO Auto-generated method stub
								
							}
        	        		
        	        	};
        	        	Intent connectIntent = new Intent(button.getContext(), ConnectActivity.class);
        	        	Intent serviceIntent = new Intent(button.getContext(), BluetoothComms.class);
        	        	if( startService(serviceIntent) != null ) {
        	        		Log.e("MainActivity", "Started BT service");
        	        	}
        	    		if( bindService(serviceIntent, serviceConnect, 0) == true) {
        	    			Log.e("MainActivity", "Bound to BT service");
        	    		}
        	        	connectIntent.putExtra("REMOTE_BT", remoteBT);       	    		
        	    		startActivity(connectIntent);
        	        //}
        	    }
        	};
        	button.setOnClickListener(listen);
        }
    }
}
