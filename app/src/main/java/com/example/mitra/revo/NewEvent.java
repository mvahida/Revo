package com.example.mitra.revo;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Mitra on 3/4/2015.
 */
public class NewEvent extends ActionBarActivity {
    DataSource datasource;
    String plaindatefrom,plaindateto;
    private PendingIntent pendingIntent;
    int SelectedHour, SelectedMinute, SelectedMonth, SelectedYear, SelectedDay;
    Spinner spn_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_event);

        Spinner spn_catagory = (Spinner) findViewById(R.id.spn_catagory);
        List<String> list = new ArrayList<>();
        list.add("Meeting");
        list.add("Courses");
        list.add("Study Affairs");
        list.add("Facebook events");
        list.add("Others");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
        spn_catagory.setAdapter(dataAdapter);

        Spinner spn_repeat = (Spinner) findViewById(R.id.spn_repeat);
        List<String> list_repeat = new ArrayList<>();
        list_repeat.add("Do not repeat");
        list_repeat.add("Every day");
        list_repeat.add("Every Week");
        list_repeat.add("Every Month");
        list_repeat.add("Every year");
        ArrayAdapter<String> dataAdapter_repeat = new ArrayAdapter<String>(this, R.layout.spinner_repeat, list_repeat);
        spn_repeat.setAdapter(dataAdapter_repeat);

        spn_notification = (Spinner) findViewById(R.id.spn_notification);
        List<String> list_notification = new ArrayList<>();
        list_notification.add("Add notification");
        list_notification.add("15 minutes before");
        list_notification.add("30 minutes before");
        list_notification.add("45 minutes before");
        ArrayAdapter<String> dataAdapter_notification = new ArrayAdapter<String>(this, R.layout.spinner_repeat, list_notification);
        spn_notification.setAdapter(dataAdapter_notification);

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
                                String newMonth = String.format("%02d", monthOfYear+1);
                                String newDay = String.format("%02d", dayOfMonth);
                                EditText edt_date_to = (EditText) findViewById(R.id.edt_date_to);
                                edt_date_from.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                edt_date_to.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                plaindatefrom = String.valueOf(year) + String.valueOf(newMonth)
                                        + String.valueOf(newDay);
                                SelectedDay = dayOfMonth;
                                SelectedYear = year;
                                SelectedMonth = monthOfYear;
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
                                String newMonth = String.format("%02d", monthOfYear+1);
                                String newDay = String.format("%02d", dayOfMonth);
                                edt_date_to.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                plaindateto = String.valueOf(year) + String.valueOf(newMonth)
                                        + String.valueOf(newDay);
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
                        SelectedHour = selectedHour;
                        SelectedMinute = selectedMinute;
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
            String repeatType = String.valueOf(((Spinner) findViewById(R.id.spn_repeat)).getSelectedItemPosition());
            String notificationIntervals = String.valueOf(((Spinner) findViewById(R.id.spn_notification)).getSelectedItemPosition());
//            String dateFrom = ((EditText) findViewById(R.id.edt_date_from)).getText().toString();
//            String dateTo = ((EditText) findViewById(R.id.edt_date_to)).getText().toString();
            String timeFrom = ((EditText) findViewById(R.id.edt_time_from)).getText().toString();
            String timeTo = ((EditText) findViewById(R.id.edt_time_to)).getText().toString();
            String category = String.valueOf(((Spinner) findViewById(R.id.spn_catagory)).getSelectedItemPosition());
            String edt_title = ((EditText) findViewById(R.id.edt_title)).getText().toString();

            if( edt_title.isEmpty() || timeFrom.isEmpty() || plaindatefrom.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"You should enter Title,Time From and Date From",Toast.LENGTH_LONG).show();
                return false;
            }

            Save save = new Save();
            save.execute(repeatType, notificationIntervals, plaindatefrom, plaindateto
                    , timeFrom, timeTo, category, edt_title, "0");

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.set(Calendar.MONTH, SelectedMonth);
            cal.set(Calendar.YEAR, SelectedYear);
            cal.set(Calendar.DAY_OF_MONTH, SelectedDay);
            if (spn_notification.getSelectedItemPosition() == 1) {
                int interval = SelectedMinute - 15;
                if(interval<0)
                {
                    interval = 60-interval;
                    SelectedHour = SelectedHour-1;
                }
                cal.set(Calendar.HOUR_OF_DAY, SelectedHour);
                cal.set(Calendar.MINUTE, interval);
            } else if (spn_notification.getSelectedItemPosition() == 2) {
                int interval = SelectedMinute - 30;
                if(interval<0)
                {
                    interval = 60-interval;
                    SelectedHour = SelectedHour-1;
                }
                cal.set(Calendar.HOUR_OF_DAY, SelectedHour);
                cal.set(Calendar.MINUTE, interval);
            } else if (spn_notification.getSelectedItemPosition() == 3) {
                int interval = SelectedMinute - 45;
                if(interval<0)
                {
                    interval = 60-interval;
                    SelectedHour = SelectedHour-1;
                }
                cal.set(Calendar.HOUR_OF_DAY, SelectedHour);
                cal.set(Calendar.MINUTE, interval);
            }
            else {
                cal.set(Calendar.HOUR_OF_DAY, SelectedHour);
                cal.set(Calendar.MINUTE, SelectedMinute);
            }

            AlarmManager alarmMgr = (AlarmManager)getApplicationContext().getSystemService(getApplicationContext().ALARM_SERVICE);
            Intent intent = new Intent(getApplicationContext(), Alarm.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

            alarmMgr.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmIntent);

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
                    , params[3], params[4], params[5], Integer.valueOf(params[6]), params[7], Integer.valueOf(params[8]));
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
