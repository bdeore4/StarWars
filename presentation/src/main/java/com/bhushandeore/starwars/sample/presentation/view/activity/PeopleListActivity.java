package com.bhushandeore.starwars.sample.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.bhushandeore.starwars.sample.presentation.R;
import com.bhushandeore.starwars.sample.presentation.di.HasComponent;
import com.bhushandeore.starwars.sample.presentation.di.components.DaggerPeopleComponent;
import com.bhushandeore.starwars.sample.presentation.di.components.PeopleComponent;
import com.bhushandeore.starwars.sample.presentation.model.PeopleModel;
import com.bhushandeore.starwars.sample.presentation.view.fragment.PeopleListFragment;

/**
 * Activity that shows a list of Peoples.
 */
public class PeopleListActivity extends BaseActivity implements HasComponent<PeopleComponent>,
        PeopleListFragment.PeopleListListener {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, PeopleListActivity.class);
    }

    private PeopleComponent peopleComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new PeopleListFragment());
        }
    }

    private void initializeInjector() {
        this.peopleComponent = DaggerPeopleComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public PeopleComponent getComponent() {
        return peopleComponent;
    }

    @Override
    public void onPeopleClicked(PeopleModel peopleModel) {
        this.navigator.navigateToPeopleDetails(this, peopleModel.id);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
