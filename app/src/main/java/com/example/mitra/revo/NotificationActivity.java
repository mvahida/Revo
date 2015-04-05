package com.example.mitra.revo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.regex.Pattern;


public class NotificationActivity extends ActionBarActivity {

    TextView notificationText;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        String[] values = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        Intent intent = getIntent();

        String[] data;

        String notification = intent.getStringExtra("notification");

        Pattern pattern = Pattern.compile(Pattern.quote(","));
        data = pattern.split(notification);

        Log.d("length", "length is" + data.length);

        /*String[] newData = new String[ data.length-1 ];

        for (int i=0; i< data.length - 2; i++) {
            newData[i] = data[i];
        }*/


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, data);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // Assign adapter to ListView
        //listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
