package com.bhushandeore.starwars.sample.presentation.exception;

import android.test.AndroidTestCase;
import com.bhushandeore.starwars.sample.data.exception.NetworkConnectionException;
import com.bhushandeore.starwars.sample.data.exception.PeopleNotFoundException;
import com.bhushandeore.starwars.sample.presentation.R;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ErrorMessageFactoryTest extends AndroidTestCase {

  @Override protected void setUp() throws Exception {
    super.setUp();
  }

  public void testNetworkConnectionErrorMessage() {
    String expectedMessage = getContext().getString(R.string.exception_message_no_connection);
    String actualMessage = ErrorMessageFactory.create(getContext(),
        new NetworkConnectionException());

    assertThat(actualMessage, is(equalTo(expectedMessage)));
  }

  public void testPeopleNotFoundErrorMessage() {
    String expectedMessage = getContext().getString(R.string.exception_message_people_not_found);
    String actualMessage = ErrorMessageFactory.create(getContext(), new PeopleNotFoundException());

    assertThat(actualMessage, is(equalTo(expectedMessage)));
  }
}
