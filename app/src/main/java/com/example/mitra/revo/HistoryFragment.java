package com.example.mitra.revo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class HistoryFragment extends Fragment {
    DataSource datasource;
    CustomAdapter adapter;
	public HistoryFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        datasource = new DataSource(getActivity());
        datasource.open();
        List<Message> values = datasource.getBaseHistory();
        adapter = new CustomAdapter(getActivity(),
                android.R.layout.simple_list_item_1,values, datasource,inflater);
        final ListView lst_history = (ListView)rootView.findViewById(R.id.lst_history);
        lst_history.setAdapter(adapter);

        return rootView;
    }
    @Override
    public void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        datasource.close();
        super.onPause();
    }
}
