package com.bhushandeore.starwars.sample.domain.interactor;

import com.bhushandeore.starwars.sample.domain.executor.PostExecutionThread;
import com.bhushandeore.starwars.sample.domain.executor.ThreadExecutor;
import com.bhushandeore.starwars.sample.domain.repository.PeopleRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetPeopleListTest {

  private GetPeopleList getPeopleList;

  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;
  @Mock private PeopleRepository mockPeopleRepository;

  @Before
  public void setUp() {
    getPeopleList = new GetPeopleList(mockPeopleRepository, mockThreadExecutor,
        mockPostExecutionThread);
  }

  @Test
  public void testGetPeopleListUseCaseObservableHappyCase() {
    getPeopleList.buildUseCaseObservable(GetPeopleList.Params.forPeople(1));

    verify(mockPeopleRepository).peopleList(1);
    verifyNoMoreInteractions(mockPeopleRepository);
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }
}
