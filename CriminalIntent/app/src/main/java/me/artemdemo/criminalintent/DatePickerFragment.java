package me.artemdemo.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {
    public static final String EXTRA_DATE = "me.artemdemo.criminalintent.date";

    private static final String TAG = "DatePickerFragment";
    
    private Date mDate;

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }
    
    private void saveResult(int year, int month, int day) {
        // Translate year, month, day into a Date object using a calendar
        mDate = new GregorianCalendar(year, month, day).getTime();

        // Update argument to preserve selected value on rotation
        getArguments().putSerializable(EXTRA_DATE, mDate);
    }
    
    /*
     * In order to send data back to CrimeFragment we need to use onActivityResult()
     */
    private void sendResult( int resultCode ) {
        if (getTargetFragment() == null)
            return;
        
        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mDate);
        
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }
    
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
        
        // Create a Calendar to get the year, month and day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        
        View v = getActivity().getLayoutInflater()
                 .inflate(R.layout.dialog_date, null);

        final DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                /*
                 * The problem is that this code is no longer working in Nexus API 21 (lollipop)
                 * Therefor I need to be creative - I'm using saveResult() on positive button click
                 */
                // Translate year, month, day into a Date object using a calendar
                mDate = new GregorianCalendar(year, month, day).getTime();

                // Update argument to preserve selected value on rotation
                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveResult(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .create();
    }
}
