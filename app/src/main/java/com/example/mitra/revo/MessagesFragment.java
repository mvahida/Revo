package com.example.mitra.revo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MessagesFragment extends Fragment {
    DataSource datasource;
    CustomAdapter adapter;
    View rootView;
	public MessagesFragment(){}

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_messages, menu);

    }
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_messages, container, false);
        setHasOptionsMenu(true);
        datasource = new DataSource(getActivity());
        datasource.open();

        List<Message> values = datasource.getAllRecords();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        adapter = new CustomAdapter(getActivity(),
                android.R.layout.simple_list_item_1,values, datasource,inflater);
        final ListView lst_messages = (ListView)rootView.findViewById(R.id.lst_messages);
        lst_messages.setAdapter(adapter);

        return rootView;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_event) {
            Intent intent = new Intent(getActivity(), NewEvent.class);
            startActivityForResult(intent,1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            datasource.open();
            ListView lst_messages = (ListView)rootView.findViewById(R.id.lst_messages);
            List<Message> values = datasource.getAllRecords();
            adapter.clear();
            adapter.addAll(values);
            lst_messages.setAdapter(adapter);
            ((CustomAdapter)lst_messages.getAdapter()).notifyDataSetChanged();
            datasource.close();
        }
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
