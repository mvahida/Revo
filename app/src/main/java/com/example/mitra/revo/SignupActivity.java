package com.example.mitra.revo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

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
import java.util.regex.Pattern;


public class SignupActivity extends Activity {


    Button signupBtn;

    private Handler myHandler;
    private Thread downloadThread;

    private String str;
    JSONObject object;

    private ProgressDialog dialogue;

    EditText signupUsername;
    EditText signupPassword;
    EditText signupConfirmPassword;

    GoogleCloudMessaging gcm;
    String PROJECT_NUMBER = "983456904439";
    String regid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        signupBtn = (Button) findViewById(R.id.signup);

        signupUsername          = (EditText) findViewById(R.id.signup_username);
        signupPassword          = (EditText) findViewById(R.id.signup_password);
        signupConfirmPassword   = (EditText) findViewById(R.id.signup_confirm_password);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username     = signupUsername.getText().toString();
                String password     = signupPassword.getText().toString();
                String confirmPass  = signupConfirmPassword.getText().toString();

                if( username.equals("") || password.equals("") ) {
                    Toast.makeText(getApplication(),"Username and/or Password is empty", Toast.LENGTH_LONG).show();
                    return;
                }

                if( !password.equals(confirmPass) ) {
                    Toast.makeText(getApplication(),"Please make sure your passwords match.", Toast.LENGTH_LONG).show();
                    return;
                }

                //Get Device registeration id from gcm.
                getRegId();

                /*object = new JSONObject();
                try {
                    object.put("username", signupUsername.getText().toString());
                    object.put("password", signupPassword.getText().toString());
                    object.put("firstname", signupUsername.getText().toString());
                    object.put("lastname", signupUsername.getText().toString());
                    object.put("gcm_registration_id", "10");
                    object.put("active", "1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                myHandler = new Handler();

                dialogue = new ProgressDialog(SignupActivity.this);
                dialogue.setTitle("Revo");
                dialogue.setMessage("Loading Please wait.....");
                dialogue.setCancelable(false);
                dialogue.setIndeterminate(true);
                dialogue.show();

                downloadThread = new MyDownloadThread();
                downloadThread.start();*/
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
        str = postData();
    }

    public String postData() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://memoryhelper.netne.net/memoryhelper/index.php/users/signup");

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
            final String[] tokens = str.split(Pattern.quote("divider"));
            JSONObject jObject = new JSONObject(str);

            String userAuth = jObject.getString("status");
            if( userAuth.equals("0") ) {
                Toast.makeText(getApplicationContext(), "Problem with Signup try again later.", Toast.LENGTH_LONG).show();
            } else if( userAuth.equals("2") ) {
                Toast.makeText(getApplicationContext(), "Another user has already signed up with this username", Toast.LENGTH_LONG).show();
            } else if( userAuth.equals("1") ) {
                Toast.makeText(getApplicationContext(), "Congrats!! You have Successfully Signed Up.", Toast.LENGTH_LONG).show();

                //Toast.makeText(getApplicationContext(), jObject.getString("data"), Toast.LENGTH_LONG).show();

                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("MemoryHelperPreference", Context.MODE_PRIVATE).edit();
                editor.putString( "user_id", jObject.getString("data") );
                editor.commit();

                Intent loginIntent = new Intent( getApplicationContext(), LoginActivity.class );
                startActivity( loginIntent );

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

    public void getRegId(){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regid = gcm.register(PROJECT_NUMBER);
                    msg = "Device registered, registration ID=" + regid;
                    Log.i("GCM", msg);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.d("gmc id is", msg);

                object = new JSONObject();
                try {
                    object.put("username", signupUsername.getText().toString());
                    object.put("password", signupPassword.getText().toString());
                    object.put("firstname", signupUsername.getText().toString());
                    object.put("lastname", signupUsername.getText().toString());
                    object.put("gcm_registration_id", regid);
                    object.put("active", "1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                myHandler = new Handler();

                dialogue = new ProgressDialog(SignupActivity.this);
                dialogue.setTitle("Revo");
                dialogue.setMessage("Loading Please wait.....");
                dialogue.setCancelable(false);
                dialogue.setIndeterminate(true);
                dialogue.show();

                downloadThread = new MyDownloadThread();
                downloadThread.start();

            }
        }.execute(null, null, null);
    }

}