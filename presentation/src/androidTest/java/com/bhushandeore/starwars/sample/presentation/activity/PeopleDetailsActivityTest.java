package com.bhushandeore.starwars.sample.presentation.activity;

import android.app.Fragment;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import com.bhushandeore.starwars.sample.presentation.R;
import com.bhushandeore.starwars.sample.presentation.view.activity.PeopleDetailsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class PeopleDetailsActivityTest extends ActivityInstrumentationTestCase2<PeopleDetailsActivity> {

  private static final int FAKE_PEOPLE_ID = 10;

  private PeopleDetailsActivity peopleDetailsActivity;

  public PeopleDetailsActivityTest() {
    super(PeopleDetailsActivity.class);
  }

  @Override protected void setUp() throws Exception {
    super.setUp();
    this.setActivityIntent(createTargetIntent());
    this.peopleDetailsActivity = getActivity();
  }

  @Override protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testContainsPeopleDetailsFragment() {
    Fragment peopleDetailsFragment =
            peopleDetailsActivity.getFragmentManager().findFragmentById(R.id.fragmentContainer);
    assertThat(peopleDetailsFragment, is(notNullValue()));
  }

  public void testContainsProperTitle() {
    String actualTitle = this.peopleDetailsActivity.getTitle().toString().trim();

    assertThat(actualTitle, is("People Details"));
  }

  public void testLoadPeopleHappyCaseViews() {
    onView(withId(R.id.rl_retry)).check(matches(not(isDisplayed())));
    onView(withId(R.id.rl_progress)).check(matches(not(isDisplayed())));

    onView(withId(R.id.name_label)).check(matches(isDisplayed()));
    onView(withId(R.id.height_value)).check(matches(isDisplayed()));
    onView(withId(R.id.mass_value)).check(matches(isDisplayed()));
  }

  public void testLoadPeopleHappyCaseData() {
    onView(withId(R.id.name_label)).check(matches(withText("Luke Skywalker")));
    onView(withId(R.id.height_value)).check(matches(withText("4.3688")));
    onView(withId(R.id.mass_value)).check(matches(withText("77")));
  }

  private Intent createTargetIntent() {
    Intent intentLaunchActivity =
        PeopleDetailsActivity.getCallingIntent(getInstrumentation().getTargetContext(), FAKE_PEOPLE_ID);

    return intentLaunchActivity;
  }
}
