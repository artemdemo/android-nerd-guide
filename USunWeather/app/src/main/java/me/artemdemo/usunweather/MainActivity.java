package me.artemdemo.usunweather;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * The use of application icon plus title as a standard layout is discouraged on API 21 devices and newer.
         * Source: http://stackoverflow.com/a/26850864
         */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo( R.drawable.main_icon ); // I'm actually using xml file that add padding to the icon
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private ArrayAdapter<String> mForecastAdapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            String[] forecastArray = {
                    "Today - Sunny 88/63",
                    "Tomorrow - Foggy 70/40",
                    "Weds - Cloudy 72/63",
                    "Thurs - Sunny 90/12",
                    "Fri - Sunny 80/76",
                    "Sat - Sunny 88/63",
                    "Sun - Sunny 88/63"
            };

            List<String> weekFirecast = new ArrayList<String>(Arrays.asList(forecastArray));

            mForecastAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.list_item_forecast, // ID of list item layout
                    R.id.list_item_forecast_textview, // ID of the textview to populate
                    weekFirecast // Forecast data
            );

            ListView listview = (ListView) rootView.findViewById(R.id.listview_forecast);
            listview.setAdapter(mForecastAdapter);

            return rootView;
        }
    }
}
