package com.example.mitra.revo;

import java.util.List;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Message> {
	private List<Message> objects = null;
    DataSource datasource;
	Context context1;
	final LayoutInflater inflater;

	public CustomAdapter(Context context,
			int textviewid, List<Message> objects
			,DataSource DataSource,LayoutInflater inflater1) {
		super(context, textviewid, objects);
		this.objects = objects;
		context1 = context;
        datasource = new DataSource(context);
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
            viewHolder.img = (ImageView) view.findViewById(R.id.img_star);
            //viewHolder.imgb = (ImageButton) view.findViewById(R.id.imageButton);

			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
        Message data = objects.get(position);
		if (null != data) {
			viewHolder.Title.setText(data.getTitle());
            if(data.getIsImportant() == 1)
            {
                //viewHolder.img.setImageDrawable(getContext().getResources().getDrawable(R.drawable.star26checked));
            }
            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    datasource.open();
//                    Message message = datasource.UpdateRecord()
//                    datasource.close();
                    ImageView img = (ImageView)v;
                    img.setImageDrawable(getContext().getResources().getDrawable(R.drawable.star26checked));
                }
            });
		}
		return view;
	}

}
