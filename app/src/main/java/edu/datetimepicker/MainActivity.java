package edu.datetimepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements DatePickerFragment.DateDialogListener {


    /***
     * show date and time in this format
     * 09.00 AM - 10.00 AM, Thu 23/03/17
     */
    TextView dateTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateTime = (TextView) findViewById(R.id.date_time);

    }

    public void showTimePickerDialog(View v) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setNextDays(20);
        datePickerFragment.show(getFragmentManager(), "timePicker");
    }


    @Override
    public void onFinishDialog(String dateTimeValue) {
        dateTime.setText("" +dateTimeValue);
    }
}
