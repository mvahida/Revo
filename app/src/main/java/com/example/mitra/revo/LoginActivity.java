package com.example.mitra.revo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends Activity {


    Button loginBtn;

    private Handler myHandler;

    private Thread downloadThread;

    private String str;
    JSONObject object;

    private ProgressDialog dialogue;

    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginBtn = (Button) findViewById(R.id.Login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( username.getText().toString().equals("") || password.getText().toString().equals("") ) {
                    Toast.makeText(getApplication(),"Username and/or Password is empty", Toast.LENGTH_LONG).show();
                    return;
                }

                object = new JSONObject();
                try {
                    object.put("username", username.getText().toString());
                    object.put("password", password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                myHandler = new Handler();

                dialogue = new ProgressDialog(LoginActivity.this);
                dialogue.setTitle("Revo");
                dialogue.setMessage("Loading Please wait.....");
                dialogue.setCancelable(false);
                dialogue.setIndeterminate(true);
                dialogue.show();

                downloadThread = new MyDownloadThread();
                downloadThread.start();

                //Intent messagesIntent = new Intent(getApplicationContext(), MessagesActivity.class);
                //startActivity(messagesIntent);
            }
        });

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

    private void downloadStart() {

        //str = getData( "http://www.ephlux.com/oowdata-andoid.json" );

        str = postData();

        //Gson gson = new Gson();
        //str = gson.fromJson( jsonStr, GsonString.class );

    }

    public String postData() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://memoryhelper.netne.net/memoryhelper/index.php/users/login");

        String loginResponse = "";

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("data", object.toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            loginResponse = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return loginResponse;
    }

    private String getData( String webUrl ){

        String data = "";




        /*try {
            URL url = new URL( webUrl );
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            data = readStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return data;
    }

    private class MyRunnable implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            downloadComplete();
            dialogue.dismiss();
        }
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        String output = "";


        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";

            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                output+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return output;

    }

    private void downloadComplete(){
        try {
            JSONObject jObject = new JSONObject(str);

            String userAuth = jObject.getString("is_auth");
            if( userAuth.equals("0") ) {
                Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Logged in Successfully", Toast.LENGTH_LONG).show();

                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("MemoryHelperPreference", Context.MODE_PRIVATE).edit();
                editor.putString( "logged_in", "1" );
                editor.commit();

                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);

            }
        } catch (Exception e) {
            Log.d("Exception in download complete", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
