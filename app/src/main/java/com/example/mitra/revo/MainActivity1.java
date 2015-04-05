package com.example.mitra.revo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.FacebookSdk;
//import com.facebook.Profile;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;

import java.util.Arrays;


public class MainActivity1 extends Activity {

    TextView loginView;
    ImageButton uniLoginBtn;

    String isLoggedIn = "";

//    private LoginButton fbLoginBtn;
    //private UiLifecycleHelper uiHelper;

    /*Button btnRegId;
    EditText etRegId;
    GoogleCloudMessaging gcm;
    String regid;
    String PROJECT_NUMBER = "983456904439";*/

//    CallbackManager callbackManager;
//
//    MainActivity obj = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( !isInternetOn() ) {
            finish();
            return;
        }

//        FacebookSdk.sdkInitialize(getApplicationContext());
//        callbackManager = CallbackManager.Factory.create();
//
//        LoginManager.getInstance().registerCallback(callbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//
//
//
//                        Profile profile = Profile.getCurrentProfile();
//
//                        if(profile != null ){
//                            Toast.makeText( getApplicationContext(), "not null", Toast.LENGTH_LONG ).show();
//                        } else {
//                            Toast.makeText( getApplicationContext(), "null", Toast.LENGTH_LONG ).show();
//                        }
//
//                        //Toast.makeText( getApplicationContext(), "First Name is" + profile.getFirstName(), Toast.LENGTH_LONG ).show();
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        Toast.makeText( getApplicationContext(), "on cancel", Toast.LENGTH_LONG ).show();
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        Toast.makeText( getApplicationContext(), "on error", Toast.LENGTH_LONG ).show();
//                    }
//                });

        setContentView(R.layout.activity_main1);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MemoryHelperPreference", Context.MODE_PRIVATE);

        String str = prefs.getString("logged_in", "");

        if( str.equals("1") ) {
            Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainIntent);
        } else {
            Toast.makeText(getApplicationContext(), "stuck to login activity", Toast.LENGTH_LONG).show();
        }

        loginView   = (TextView) findViewById(R.id.login_existing_account);
        uniLoginBtn = (ImageButton) findViewById(R.id.uniLoginBtn);
//        fbLoginBtn  = (LoginButton) findViewById(R.id.fb_login_button);
//        fbLoginBtn.setBackgroundResource(R.drawable.facebook);
//        fbLoginBtn.setReadPermissions("user_friends");
//        fbLoginBtn.setReadPermissions("public_profile");

        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        uniLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(signupIntent);
            }
        });

//        fbLoginBtn.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LoginManager.getInstance().logInWithReadPermissions(obj, Arrays.asList("public_profile", "user_friends"));
//            }
//        });

    }

    /*public void getRegId(){
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
                etRegId.setText(msg + "\n");
            }
        }.execute(null, null, null);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(this, "Please make sure you are connected to the Internet.", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
