package me.artemdemo.criminalintent;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.UUID;

public class CrimePagerActivity extends ActionBarActivity {
    // We're adding ViewPager - it will do the trick with swiping right and left
    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * There is no XML file with view definition
         * Instead I'm using ids.xml file, that will contain id of the view.
         * Entire view will be created in the code (only id should be in XML, as required by API) 
         */
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);
        
        // Setting up the ViewPage adapter
        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return mCrimes.size();
            }
            
            @Override
            public Fragment getItem(int i) {
                Crime crime = mCrimes.get(i);
                return CrimeFragment.newInstance( crime.getId() );
            }
        });
        
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrollStateChanged(int i) {}

            @Override
            public void onPageScrolled(int i, float v, int i2) {}

            @Override
            public void onPageSelected(int pos) {
                Crime crime = mCrimes.get(pos);
                if ( crime.getTitle() != null ) {
                    setTitle(crime.getTitle());
                }
            }
        });
        
        UUID crimeId = (UUID)getIntent()
                .getSerializableExtra( CrimeFragment.EXTRA_CRIME_ID );
        for ( int i = 0; i < mCrimes.size(); i++ ) {
            if ( mCrimes.get(i).getId().equals(crimeId) ) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
