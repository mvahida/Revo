package com.example.mitra.revo;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SettingFragment extends Fragment {
	
	public SettingFragment(){}

    Switch weboodiSwitch;
    Switch optimaSwitch;
    Switch lukkariSwitch;

    Button btn_lukkari;

    String lukkariSettingId = "1";
    String weboodiSettingId = "2";
    String optimaSettingId  = "3";

    String url = "http://memoryhelper.netne.net/memoryhelper/index.php/settings/get_user_settings";
    //String url = "http://memoryhelper.netne.net/memoryhelper/index.php/settings/update_setting_by_user_id";

    Boolean editSettings = false;

    private Handler myHandler;
    private Thread downloadThread;

    private String str;
    JSONObject object;

    private ProgressDialog dialogue;

    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        SharedPreferences prefs = getActivity().getSharedPreferences("MemoryHelperPreference", Context.MODE_PRIVATE);

        userId = prefs.getString("user_id", "");

        initiateRequest( userId );

        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        btn_lukkari = (Button)rootView.findViewById(R.id.btn_lukkari);
        btn_lukkari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LukkariActivity.class);
                startActivity(intent);
            }
        });

        weboodiSwitch = (Switch)rootView.findViewById(R.id.switch_weboodi);
        optimaSwitch = (Switch)rootView.findViewById(R.id.switch_optima);
        lukkariSwitch = (Switch)rootView.findViewById(R.id.switch_lukkari);

        return rootView;
    }

    private void initiateRequestEditSettings(String userId, String componentId, String status) {
        object = new JSONObject();
        try {
            object.put("user_id", userId);
            object.put("component_id", componentId);
            object.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        myHandler = new Handler();

        dialogue = new ProgressDialog(getActivity());
        dialogue.setTitle("Revo");
        dialogue.setMessage("Loading Please wait.....");
        dialogue.setCancelable(false);
        dialogue.setIndeterminate(true);
        dialogue.show();

        downloadThread = new MyDownloadThreadSettings();
        downloadThread.start();

    }

    private void initiateRequest(String userId) {
        object = new JSONObject();
        try {
            object.put("user_id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        myHandler = new Handler();

        dialogue = new ProgressDialog(getActivity());
        dialogue.setTitle("Revo");
        dialogue.setMessage("Loading Please wait.....");
        dialogue.setCancelable(false);
        dialogue.setIndeterminate(true);
        dialogue.show();

        downloadThread = new MyDownloadThread();
        downloadThread.start();

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

    public class MyDownloadThreadSettings extends Thread {

        @Override
        public void run() {
            try {
                downloadStartSettings();
                myHandler.post(new MyRunnableSettings());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void downloadStart() {
        str = postData();
    }

    private void downloadStartSettings() {
        str = postDataSettings();
    }

    public String postData() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost( "http://memoryhelper.netne.net/memoryhelper/index.php/settings/get_user_settings" );

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

    public String postDataSettings() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://memoryhelper.netne.net/memoryhelper/index.php/settings/update_setting_by_user_id");

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

    private class MyRunnableSettings implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            downloadCompleteSettings();
            dialogue.dismiss();
        }
    }

    private void downloadComplete(){
        try {
                final String[] tokens = str.split(Pattern.quote("divider"));
                /*Toast.makeText(getActivity(), tokens[0], Toast.LENGTH_LONG).show();*/

                JSONArray arr = new JSONArray( tokens[0] );

                for(int i = 0; i < arr.length(); i++){

                    JSONObject c = arr.getJSONObject(i);
                    String componentId  = c.getString("component_id");
                    String status       = c.getString("status");

                    if( componentId.equals("1") ) {

                        lukkariSwitch.setOnCheckedChangeListener(null);
                        if( status.equals("1")) {
                            lukkariSwitch.setChecked( true );
                        } else {
                            lukkariSwitch.setChecked( false );
                        }

                        lukkariSwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                if( isChecked ) {
                                    initiateRequestEditSettings(userId, lukkariSettingId, "1");
                                } else {
                                    initiateRequestEditSettings(userId, lukkariSettingId, "0");
                                }
                            }
                        });

                    } else if ( componentId.equals("2") ) {

                        weboodiSwitch.setOnCheckedChangeListener(null);
                        if( status.equals("1")) {
                            weboodiSwitch.setChecked( true );
                        } else {
                            weboodiSwitch.setChecked( false );
                        }

                        weboodiSwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                if( isChecked ) {
                                    initiateRequestEditSettings(userId, weboodiSettingId, "1");
                                } else {
                                    initiateRequestEditSettings(userId, weboodiSettingId, "0");
                                }
                            }
                        });

                    } else if ( componentId.equals("3") ) {

                        optimaSwitch.setOnCheckedChangeListener(null);
                        if( status.equals("1")) {
                            optimaSwitch.setChecked( true );
                        } else {
                            optimaSwitch.setChecked( false );
                        }

                        optimaSwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                if( isChecked ) {
                                    initiateRequestEditSettings(userId, optimaSettingId, "1");
                                } else {
                                    initiateRequestEditSettings(userId, optimaSettingId, "0");
                                }
                            }
                        });
                    }
                }

        } catch (Exception e) {
            Log.d("Exception in download complete", e.getMessage());
        }
    }

    private void downloadCompleteSettings(){
        try {
                final String[] tokens = str.split(Pattern.quote("divider"));
                JSONObject jObject = new JSONObject(str);
                String settingStatus = jObject.getString("status");
                if( settingStatus.equals("0") ) {
                    Toast.makeText(getActivity(), "Problem updating the setting.", Toast.LENGTH_LONG).show();
                } else if( settingStatus.equals("1") ) {
                    Toast.makeText(getActivity(), "Setting updated.", Toast.LENGTH_LONG).show();
                }

        } catch (Exception e) {
            Log.d("Exception in download complete", e.getMessage());
        }
    }
}
