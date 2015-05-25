package com.example.mitra.revo;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.content.Intent;

public class LogoutFragment extends Fragment {
	
	public LogoutFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Intent intent = new Intent(LogoutFragment.this.getActivity(), LoginActivity.class);
        LogoutFragment.this.startActivity(intent);
        View rootView = inflater.inflate(R.layout.fragment_logout, container, false);
        Toast toast = Toast.makeText(getActivity(), "You have logged out!", Toast.LENGTH_SHORT);
        toast.show();
        return rootView;
    }
}
