package edu.datetimepicker;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by User on 23-03-2017.
 */

public class DatePickerFragment extends DialogFragment implements NumberPicker.OnValueChangeListener {

    private NumberPicker datePicker;
    private NumberPicker timePicker;
    private String[] dateSlot;
    private String[] timeSlot;
    private int selectedDate;
    private int selectedTime;
    private int nextDays = 0;

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        if (picker.getId() == R.id.numberPickerDate) {

            selectedDate = newVal;
            if(dateSlot[selectedDate].toString().contains("Fri")){
                timeSlot = getTimeSlotFri();
                timePicker.setDisplayedValues(null);
                timePicker.setMaxValue(timeSlot.length - 1);
                timePicker.setDisplayedValues(timeSlot);
                timePicker.setValue(0);
                timePicker.invalidate();
            }else{
                timeSlot = getTimeSlot();
                timePicker.setDisplayedValues(null);
                timePicker.setMaxValue(timeSlot.length - 1);
                timePicker.setDisplayedValues(timeSlot);
                timePicker.setValue(0);
                timePicker.invalidate();
            }

        } else if (picker.getId() == R.id.numberPickerTime) {
            selectedTime = newVal;
        }
    }

    public void setNextDays(int days) {
        nextDays = days;
    }

    public interface DateDialogListener {
        void onFinishDialog(String dateTime);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

        datePicker = (NumberPicker) v.findViewById(R.id.numberPickerDate);
        datePicker.setOnValueChangedListener(this);
        timePicker = (NumberPicker) v.findViewById(R.id.numberPickerTime);
        timePicker.setOnValueChangedListener(this);


        dateSlot = new String[nextDays];

        dateSlot = getDateSlot();
        timeSlot = getTimeSlot();

        timePicker.setMaxValue(timeSlot.length - 1);
        timePicker.setDisplayedValues(timeSlot);

        datePicker.setMaxValue(dateSlot.length - 1);
        datePicker.setDisplayedValues(dateSlot);


        return new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Pick Date and Time")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DateDialogListener activity = (DateDialogListener) getActivity();
                                activity.onFinishDialog(timeSlot[selectedTime]+", "+dateSlot[selectedDate]);
                                dismiss();
                            }
                        })
                .create();
    }


    private String[] getTimeSlot() {
        return getResources().getStringArray(R.array.time_picker);
    }

    private String[] getTimeSlotFri() {
        return getResources().getStringArray(R.array.time_picker_fri);
    }

    private String[] getDateSlot() {

        String[] calender = new String[nextDays];

        SimpleDateFormat format = new SimpleDateFormat("EEE dd/MM/yy");
        Calendar date = Calendar.getInstance();

        for(int i = 0; i < nextDays; i++){
            calender[i] = format.format(date.getTime());
            date.add(Calendar.DATE  , 1);
        }


        return calender;
    }


}
