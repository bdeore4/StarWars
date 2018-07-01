package com.bhushandeore.starwars.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.bhushandeore.starwars.sample.presentation.R;
import com.bhushandeore.starwars.sample.presentation.di.HasComponent;
import com.bhushandeore.starwars.sample.presentation.di.components.DaggerPeopleComponent;
import com.bhushandeore.starwars.sample.presentation.di.components.PeopleComponent;
import com.bhushandeore.starwars.sample.presentation.view.fragment.PeopleDetailsFragment;

/**
 * Activity that shows details of a certain people.
 */
public class PeopleDetailsActivity extends BaseActivity implements HasComponent<PeopleComponent> {

    private static final String INTENT_EXTRA_PARAM_PEOPLE_ID = "INTENT_PARAM_PEOPLE_ID";
    private static final String INSTANCE_STATE_PARAM_PEOPLE_ID = "STATE_PARAM_PEOPLE_ID";

    public static Intent getCallingIntent(Context context, int id) {
        Intent callingIntent = new Intent(context, PeopleDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_PEOPLE_ID, id);
        return callingIntent;
    }

    private int id;
    private PeopleComponent peopleComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_PEOPLE_ID, this.id);
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.id = getIntent().getIntExtra(INTENT_EXTRA_PARAM_PEOPLE_ID, -1);
            addFragment(R.id.fragmentContainer, PeopleDetailsFragment.forPeople(id));
        } else {
            this.id = savedInstanceState.getInt(INSTANCE_STATE_PARAM_PEOPLE_ID);
        }
    }

    private void initializeInjector() {
        this.peopleComponent = DaggerPeopleComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public PeopleComponent getComponent() {
        return peopleComponent;
    }
}
