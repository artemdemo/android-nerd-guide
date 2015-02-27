package me.artemdemo.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Using fragments requires activities that know how to manage fragments
 * Basically we can use here "extends Activity", but in order to provide compatible code fro previous android versions I'm using FragmentActivity instead
 */
public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }

}
