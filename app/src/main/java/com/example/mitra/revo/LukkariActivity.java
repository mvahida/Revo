package com.example.mitra.revo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LukkariActivity extends ActionBarActivity {

    Button      lukkariSetupBtn;

    EditText    lukkariSetupText;

    private Handler myHandler;
    private Thread downloadThread;

    private String str;
    JSONObject object;

    private ProgressDialog dialogue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lukkari);

        lukkariSetupBtn     = (Button) findViewById(R.id.btn_lukkari);
        lukkariSetupText    = (EditText) findViewById(R.id.text_lukkari);

        lukkariSetupBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lukkariUrl = lukkariSetupText.getText().toString();

                SharedPreferences prefs = getApplicationContext().getSharedPreferences("MemoryHelperPreference", Context.MODE_PRIVATE);

                String userId = prefs.getString("user_id", "");

                object = new JSONObject();
                try {
                    object.put( "user_id", userId );
                    object.put( "ical_url", lukkariUrl );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                myHandler = new Handler();

                dialogue = new ProgressDialog(LukkariActivity.this);
                dialogue.setTitle("Revo");
                dialogue.setMessage("Loading Please wait.....");
                dialogue.setCancelable(false);
                dialogue.setIndeterminate(true);
                dialogue.show();

                downloadThread = new MyDownloadThread();
                downloadThread.start();

            }
        });

    }


    public String postData() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://memoryhelper.netne.net/memoryhelper/index.php/icalparser/parse_ical_data");

        String signupResponse = "";

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("data", object.toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            signupResponse = EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return signupResponse;
    }


    private class MyRunnable implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            downloadComplete();
            dialogue.dismiss();
        }
    }

    private void downloadComplete(){
        try {
            Toast.makeText( getApplication(), str, Toast.LENGTH_LONG ).show();
        }
         catch (Exception e) {
            Log.d("Exception in download complete", e.getMessage());
        }
    }

    private void downloadStart() {
        str = postData();
    }

    public class MyDownloadThread extends Thread {

        @Override
        public void run() {
            try {
                downloadStart();
                myHandler.post(new MyRunnable());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lukkari, menu);
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
