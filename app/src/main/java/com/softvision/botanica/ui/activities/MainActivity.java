package com.softvision.botanica.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.softvision.botanica.BotanicaApplication;
import com.softvision.botanica.R;
import com.softvision.botanica.common.pojo.nested.PlantPOJO;
import com.softvision.botanica.common.pojo.out.QueryOutputPOJO;
import com.softvision.botanica.common.pojo.util.BundlePojoConverter;
import com.softvision.botanica.ui.BotanicaActivity;
import com.softvision.botanica.ui.async.QueryPlantsTask;
import com.softvision.botanica.ui.fragments.NavigationDrawerFragment;
import com.softvision.botanica.ui.views.custom.TileImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends BotanicaActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, View.OnClickListener {
    private static final long RANDOM_DELAY = 4000;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private List<TileImageView> flipImgs = new ArrayList<>();

    private QueryOutputPOJO lastResult;
    private Runnable randomRunnable = new RandomPlantRunnable();
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        flipImgs.add((TileImageView) findViewById(R.id.image_1_1));
        flipImgs.add((TileImageView) findViewById(R.id.image_1_2));
        flipImgs.add((TileImageView) findViewById(R.id.image_1_3));
        flipImgs.add((TileImageView) findViewById(R.id.image_2_1));
        flipImgs.add((TileImageView) findViewById(R.id.image_2_2));
        flipImgs.add((TileImageView) findViewById(R.id.image_2_3));
        flipImgs.add((TileImageView) findViewById(R.id.image_3_1));
        flipImgs.add((TileImageView) findViewById(R.id.image_3_2));
        flipImgs.add((TileImageView) findViewById(R.id.image_3_3));
        for (TileImageView flipImg : flipImgs) {
            flipImg.setOnClickListener(this);
        }

        new QueryPlantsTask("" , 60) {
            @Override
            protected void onPostExecute(QueryOutputPOJO result) {
                super.onPostExecute(result);
                if(isSuccess()) {
                    lastResult = result;
                    int count = Math.min(result.getPlants().size(), flipImgs.size());
                    for (int i = 0; i < count; i++) {
                        flipImgs.get(i).setUrl(result.getPlants().get(i).getPicture());
                        flipImgs.get(i).setTag(R.id.plant_object_tag_key, result.getPlants().get(i));
                    }
                    BotanicaApplication.getMainHandler().postDelayed(randomRunnable, RANDOM_DELAY);
                }
            }
        }.execute();
    }

    @Override
    public void onClick(View v) {
        PlantPOJO plant = (PlantPOJO) v.getTag(R.id.plant_object_tag_key);
        if(plant != null) {
            Intent intent = new Intent(this, PlantActivity.class);
            intent.putExtra(PlantActivity.PLANT_KEY, BundlePojoConverter.pojo2Bundle(plant));
            startActivity(intent);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.search_section);
                break;
            case 2:
                mTitle = getString(R.string.add_section);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    private class RandomPlantRunnable implements Runnable {
        @Override
        public void run() {
            if(isVisible() &&!isFinishing() && lastResult != null) {
                int plantIndex = random.nextInt(lastResult.getPlants().size());
                int viewIndex = random.nextInt(flipImgs.size());
                flipImgs.get(viewIndex).setUrl(lastResult.getPlants().get(plantIndex).getPicture());
                flipImgs.get(viewIndex).setTag(R.id.plant_object_tag_key, lastResult.getPlants().get(plantIndex));
                BotanicaApplication.getMainHandler().postDelayed(this, RANDOM_DELAY);
            }
        }
    }
}
