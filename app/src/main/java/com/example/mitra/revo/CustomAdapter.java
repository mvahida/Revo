package com.example.mitra.revo;

import java.util.List;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
            ImageView img_star = (ImageView) view.findViewById(R.id.img_star);
            TextView Title = (TextView) view.findViewById(R.id.txt_title);
            img_star.setTag("unchecked");

            img_star.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    ImageView img_star = (ImageView)v;
                    int id = img_star.getId();
                    if( img_star.getTag().toString() == "unchecked") {
                        img_star.setImageDrawable(getContext().getResources().getDrawable(R.drawable.star26checked));
                        img_star.setTag("checked");
                        datasource.open();
                        Message data = objects.get(Int_Index);
                        ContentValues values = new ContentValues();
                        values.put(DatabaseHelper.xIsImportant, 1);
                        datasource.UpdateRecord(data, values);
                        data.setIsImportant(1);
                        objects.set(Int_Index, data);
                        notifyDataSetChanged();
                        datasource.close();
                    }
                    else
                    {
                        img_star.setImageDrawable(getContext().getResources().getDrawable(R.drawable.star26));
                        img_star.setTag("unchecked");
                        datasource.open();
                        Message data = objects.get(Int_Index);
                        ContentValues values = new ContentValues();
                        values.put(DatabaseHelper.xIsImportant, 0);
                        datasource.UpdateRecord(data, values);
                        data.setIsImportant(0);
                        objects.set(Int_Index, data);
                        notifyDataSetChanged();
                        datasource.close();
                    }
                }
            });
            final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            datasource.open();
                            datasource.delete(objects.get(Int_Index));
                            objects.remove(Int_Index);
                            notifyDataSetChanged();
                            datasource.close();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };
            Title.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context1);
                    builder.setMessage("Are you sure you want to delete this message?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                    return true;
                }
            });
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
        Message data = objects.get(position);
		if (null != data) {
			viewHolder.Title.setText(data.getTitle());
            if(data.getIsImportant() == 1)
            {
                viewHolder.img.setImageDrawable(getContext().getResources().getDrawable(R.drawable.star26checked));
            }
            else
            {
                viewHolder.img.setImageDrawable(getContext().getResources().getDrawable(R.drawable.star26));
            }
		}
		return view;
	}
}
