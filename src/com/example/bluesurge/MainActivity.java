package com.example.bluesurge;

import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshBTDevices();  
    }

    public void buttonClick(View view) {
    	
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
        	Button button = new Button(this);
        	list.addView(button);
            Log.e("MainActivity", "Added button");
        	BluetoothDevice remote = it.next();
        	button.setText(remote.getName());
        	OnClickListener listen = new OnClickListener()
        	{
        	    @Override
        	    public void onClick(View button)
        	    {
        	    	Button blah = (Button)button;
        	    	blah.setText("I CLICKED IT!");
        	    }
        	};
        	button.setOnClickListener(listen);
        }
    }
}
