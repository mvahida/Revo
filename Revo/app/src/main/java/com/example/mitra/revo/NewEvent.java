package com.example.mitra.revo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Mitra on 3/4/2015.
 */
public class NewEvent extends ActionBarActivity {
    DataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event);

        TextView txt_repeat = (TextView) findViewById(R.id.txt_repeat);
        txt_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(NewEvent.this);
                dialog.setContentView(R.layout.repeat_list);
                dialog.getWindow().setBackgroundDrawable
                        (new ColorDrawable(android.graphics.Color.DKGRAY));
                dialog.getWindow().setTitle("Repeat every");
                ListView listView = (ListView) dialog.findViewById(R.id.listView);
                String[] newsFeed = {"Every day", "Every week", "Every year"};
                listView.setAdapter(new ArrayAdapter<String>(NewEvent.this, android.R.layout.simple_list_item_1, newsFeed));
                dialog.show();
            }
        });
        TextView txt_addnotification = (TextView) findViewById(R.id.txt_addnotification);
        txt_addnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final EditText edt_date_from = (EditText) findViewById(R.id.edt_date_from);
        edt_date_from.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog DatePickerDialog1 = new DatePickerDialog(
                        NewEvent.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edt_date_from.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                DatePickerDialog1.show();
            }
        });
        final EditText edt_date_to = (EditText) findViewById(R.id.edt_date_to);
        edt_date_to.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog DatePickerDialog1 = new DatePickerDialog(
                        NewEvent.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edt_date_to.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                DatePickerDialog1.show();
            }
        });
        final EditText edt_time_from = (EditText) findViewById(R.id.edt_time_from);
        edt_time_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edt_time_from.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        final EditText edt_time_to = (EditText) findViewById(R.id.edt_time_to);
        edt_time_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edt_time_to.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            String repeatType = ((TextView) findViewById(R.id.txt_repeat)).getText().toString();
            String notificationIntervals = ((TextView) findViewById(R.id.txt_addnotification)).getText().toString();
            String dateFrom = ((EditText) findViewById(R.id.edt_date_from)).getText().toString();
            String dateTo = ((EditText) findViewById(R.id.edt_date_to)).getText().toString();
            String timeFrom = ((EditText) findViewById(R.id.edt_time_from)).getText().toString();
            String timeTo = ((EditText) findViewById(R.id.edt_time_to)).getText().toString();
            String edt_map = ((EditText) findViewById(R.id.edt_map)).getText().toString();
            String edt_title = ((EditText) findViewById(R.id.edt_title)).getText().toString();
            Save save = new Save();
            save.execute(repeatType, notificationIntervals, dateFrom, dateTo
                    , timeFrom, timeTo, edt_map, edt_title);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class Save extends AsyncTask<String, Void, Message> {
        private ProgressDialog progress = null;

        @Override
        protected Message doInBackground(String... params) {
            // TODO Auto-generated method stub
            datasource = new DataSource(NewEvent.this);
            datasource.open();
            Message message = datasource.createMessage(params[0], params[1], params[2]
                    , params[3], params[4], params[5], params[6], params[7]);
            datasource.close();
            return message;
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(NewEvent.this, null,
                    "Please wait");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Message result) {
            // TODO Auto-generated method stub
            if (result != null) {
                Toast.makeText(
                        NewEvent.this,
                        "Saved",
                        Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(NewEvent.this,
                        "Oops! The event Did not save", Toast.LENGTH_LONG)
                        .show();
            }
            progress.dismiss();
            super.onPostExecute(result);
        }

    }
}
