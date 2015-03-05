package com.example.mitra.revo;

import java.util.List;

import javax.sql.DataSource;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Message> {
	private List<Message> objects = null;
	Context context1;
	DataSource DataSource1;
	final LayoutInflater inflater;

	public CustomAdapter(Context context,
			int textviewid, List<Message> objects
			,DataSource DataSource,LayoutInflater inflater1) {
		super(context, textviewid, objects);
		this.objects = objects;
		context1 = context;
		DataSource1 = DataSource;
		inflater = inflater1;
	}

	@Override
	public int getCount() {
		return ((null != objects) ? objects.size() : 0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Message getItem(int position) {
		return ((null != objects) ? objects.get(position) : null);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final int Int_Index = position;
		ViewHolder viewHolder = new ViewHolder();
		if (null == view) {
			LayoutInflater vi = (LayoutInflater) context1
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(R.layout.messagesitem, null);
			viewHolder.Title = (TextView) view.findViewById(R.id.txt_title);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
        Message data = objects.get(position);
		if (null != data) {
			viewHolder.Title.setText(data.getTitle());
		}
		return view;
	}

}
