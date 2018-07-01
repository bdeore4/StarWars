package com.bhushandeore.starwars.sample.presentation.presenter;

import android.content.Context;

import com.bhushandeore.starwars.sample.domain.interactor.GetPeopleList;
import com.bhushandeore.starwars.sample.presentation.mapper.PeopleModelDataMapper;
import com.bhushandeore.starwars.sample.presentation.view.PeopleListView;
import io.reactivex.observers.DisposableObserver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PeopleListPresenterTest {

  private PeopleListPresenter peopleListPresenter;

  @Mock private Context mockContext;
  @Mock private PeopleListView mockPeopleListView;
  @Mock private GetPeopleList mockGetPeopleList;
  @Mock private PeopleModelDataMapper mockPeopleModelDataMapper;

  @Before
  public void setUp() {
    peopleListPresenter = new PeopleListPresenter(mockGetPeopleList, mockPeopleModelDataMapper);
    peopleListPresenter.setView(mockPeopleListView);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testUserListPresenterInitialize() {
    given(mockPeopleListView.context()).willReturn(mockContext);

    peopleListPresenter.initialize(1);

    verify(mockPeopleListView).hideRetry();
    verify(mockPeopleListView).showLoading();
    verify(mockGetPeopleList).execute(any(DisposableObserver.class), GetPeopleList.Params.forPeople(1));
  }
}
