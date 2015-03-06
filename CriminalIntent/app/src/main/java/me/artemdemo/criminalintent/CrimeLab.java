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
    
    public void addCrime(Crime c) {
        mCrimes.add(c);
    };
    
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
