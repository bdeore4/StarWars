package com.bhushandeore.starwars.sample.presentation.activity;

import android.app.Fragment;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import com.bhushandeore.starwars.sample.presentation.R;
import com.bhushandeore.starwars.sample.presentation.view.activity.PeopleListActivity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class PeopleListActivityTest extends ActivityInstrumentationTestCase2<PeopleListActivity> {

  private PeopleListActivity peopleListActivity;

  public PeopleListActivityTest() {
    super(PeopleListActivity.class);
  }

  @Override protected void setUp() throws Exception {
    super.setUp();
    this.setActivityIntent(createTargetIntent());
    peopleListActivity = getActivity();
  }

  @Override protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testContainsPeopleListFragment() {
    Fragment peopleListFragment =
            peopleListActivity.getFragmentManager().findFragmentById(R.id.fragmentContainer);
    assertThat(peopleListFragment, is(notNullValue()));
  }

  public void testContainsProperTitle() {
    String actualTitle = this.peopleListActivity.getTitle().toString().trim();

    assertThat(actualTitle, is("People List"));
  }

  private Intent createTargetIntent() {
    Intent intentLaunchActivity =
        PeopleListActivity.getCallingIntent(getInstrumentation().getTargetContext());

    return intentLaunchActivity;
  }
}
