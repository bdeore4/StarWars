package com.bhushandeore.starwars.sample.domain.interactor;

import com.bhushandeore.starwars.sample.domain.executor.PostExecutionThread;
import com.bhushandeore.starwars.sample.domain.executor.ThreadExecutor;
import com.bhushandeore.starwars.sample.domain.interactor.GetPeopleDetails.Params;
import com.bhushandeore.starwars.sample.domain.repository.PeopleRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetPeopleDetailsTest {

  private static final int PEOPLE_ID = 123;

  private GetPeopleDetails getPeopleDetails;

  @Mock private PeopleRepository mockPeopleRepository;
  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;

  @Rule public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    getPeopleDetails = new GetPeopleDetails(mockPeopleRepository, mockThreadExecutor,
        mockPostExecutionThread);
  }

  @Test
  public void testGetPeopleDetailsUseCaseObservableHappyCase() {
    getPeopleDetails.buildUseCaseObservable(Params.forPeople(PEOPLE_ID));

    verify(mockPeopleRepository).peopleDetails(PEOPLE_ID);
    verifyNoMoreInteractions(mockPeopleRepository);
    verifyZeroInteractions(mockPostExecutionThread);
    verifyZeroInteractions(mockThreadExecutor);
  }

  @Test
  public void testShouldFailWhenNoOrEmptyParameters() {
    expectedException.expect(NullPointerException.class);
    getPeopleDetails.buildUseCaseObservable(Params.forPeople(PEOPLE_ID));
  }
}
