package com.bhushandeore.starwars.sample.presentation.presenter;

import android.content.Context;
import com.bhushandeore.starwars.sample.domain.interactor.GetPeopleDetails;
import com.bhushandeore.starwars.sample.domain.interactor.GetPeopleDetails.Params;
import com.bhushandeore.starwars.sample.presentation.mapper.PeopleModelDataMapper;
import com.bhushandeore.starwars.sample.presentation.view.PeopleDetailsView;
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
public class PeopleDetailsPresenterTest {

  private static final int PEOPLE_ID = 1;

  private PeopleDetailsPresenter peopleDetailsPresenter;

  @Mock private Context mockContext;
  @Mock private PeopleDetailsView mockPeopleDetailsView;
  @Mock private GetPeopleDetails mockGetPeopleDetails;
  @Mock private PeopleModelDataMapper mockPeopleModelDataMapper;

  @Before
  public void setUp() {
    peopleDetailsPresenter = new PeopleDetailsPresenter(mockGetPeopleDetails, mockPeopleModelDataMapper);
    peopleDetailsPresenter.setView(mockPeopleDetailsView);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testPeopleDetailsPresenterInitialize() {
    given(mockPeopleDetailsView.context()).willReturn(mockContext);

    peopleDetailsPresenter.initialize(PEOPLE_ID);

    verify(mockPeopleDetailsView).hideRetry();
    verify(mockPeopleDetailsView).showLoading();
    verify(mockGetPeopleDetails).execute(any(DisposableObserver.class), any(Params.class));
  }
}
