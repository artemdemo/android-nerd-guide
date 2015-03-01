package me.artemdemo.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment {
    public static final String EXTRA_CRIME_ID = "me.artemdemo.crimeintent.crime_id";

    private static final String TAG = "CrimeFragment";
    
    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;
    
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    public CrimeFragment() {
    }
    
    /*
     * newInstance() make CrimeFragment more generic, it doesn't depends now on extra fields that should come from hosting activity
     * As part of instantiation crimeId should be passed by calling to this function
     * It will some from CrimePagerActivity
     */
    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);
        
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get( getActivity() ).getCrime(crimeId);
    }

    private void updateDate() {
        DateFormat _df = DateFormat.getDateTimeInstance(); // Format will be Dec 31, 1969 4:00:00 PM
        mDateButton.setText( _df.format(mCrime.getDate()) );
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*
         * Getting references in Fragment.onCreateView(...) works nearly the same as in
         * Activity.onCreate(...) . The only difference is that you call View.findViewById(int) on the fragment’s view.
         */
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        
        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText( mCrime.getTitle() );
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle( s.toString() );
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        mDateButton = (Button)v.findViewById(R.id.crime_date);
        updateDate();
        
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance( mCrime.getDate() );
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE); // I want date back after closing the dialog, therefore I'm using setTargetFragment()
                dialog.show(fm, DIALOG_DATE);
            }
        });
        
        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked( mCrime.isSolved() );
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data
                    .getSerializableExtra( DatePickerFragment.EXTRA_DATE );
            mCrime.setDate( date );
            updateDate();
        }
    }
}
