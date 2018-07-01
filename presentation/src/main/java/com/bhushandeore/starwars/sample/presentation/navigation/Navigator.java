package com.bhushandeore.starwars.sample.presentation.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.bhushandeore.starwars.sample.presentation.R;
import com.bhushandeore.starwars.sample.presentation.view.activity.PeopleDetailsActivity;
import com.bhushandeore.starwars.sample.presentation.view.activity.PeopleListActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator() {
        //empty
    }

    /**
     * Goes to the people list screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToPeopleList(Context context) {
        if (context != null) {
            Intent intentToLaunch = PeopleListActivity.getCallingIntent(context);
            intentToLaunch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            context.startActivity(intentToLaunch);
            ((Activity)context).overridePendingTransition(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit);
        }
    }

    /**
     * Goes to the people details screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToPeopleDetails(Context context, int id) {
        if (context != null) {
            Intent intentToLaunch = PeopleDetailsActivity.getCallingIntent(context, id);
            context.startActivity(intentToLaunch);
            ((Activity)context).overridePendingTransition(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit);

        }
    }
}
