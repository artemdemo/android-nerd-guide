package me.artemdemo.criminalintent;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Using fragments requires activities that know how to manage fragments
 * Basically we can use here "extends Activity", but in order to provide compatible code fro previous android versions I'm using FragmentActivity instead
 */
public abstract class SingleFragmentActivity extends FragmentActivity {
    /*
     *  We adding abstract method createFragment()
     *  Subclasses of SingleFragmentActivity will implement this method to return an instance of the fragment that the activity is hosting.
     */
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_fragment);
        
        /*
         * fragmentContainer can already exist in the list (for example onCreate will be called after rotation)
         * Therefore we need to try to find it 
         */
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        // If there is no fragment, then create one
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }
}
