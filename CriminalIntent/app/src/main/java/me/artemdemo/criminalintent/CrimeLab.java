package me.artemdemo.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 *  CrimeLab will contain the list of crimes.
 *  CrimeLab is a singleton (allow to create only one instance of itself)
 *  A singleton exists as long as the application stays in memory, so storing the list in a singleton will keep
 *  the crime data available no matter what happens with activities, fragments, and their lifecycles.
 */
public class CrimeLab {
    private ArrayList<Crime> mCrimes;
    
    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    
    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mCrimes = new ArrayList<Crime>();
        for ( int i=0; i<100; i++ ) {
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved( i % 2 == 0 );
            mCrimes.add(c);
        }
    }
    
    /**
     * get() will create CrimeLab only if there is no one in memory,
     * otherwise it will return CrimeLab
     */
    public static CrimeLab get(Context c) {
        if ( sCrimeLab == null ) {
            // pay attention that we a passing context by using getApplicationContext()
            // in this way you can be sure, that you're passing application context and not smth else
            sCrimeLab = new CrimeLab( c.getApplicationContext() );
        }
        return sCrimeLab;
    }
    
    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }
    
    public Crime getCrime(UUID id) {
        for (Crime c : mCrimes) {
            if ( c.getId().equals(id) )
                return c;
        }
        return null;
    }
}
