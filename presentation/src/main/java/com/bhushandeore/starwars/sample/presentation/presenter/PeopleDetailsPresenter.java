package com.bhushandeore.starwars.sample.presentation.presenter;

import android.support.annotation.NonNull;

import com.bhushandeore.starwars.sample.domain.People;
import com.bhushandeore.starwars.sample.domain.exception.DefaultErrorBundle;
import com.bhushandeore.starwars.sample.domain.exception.ErrorBundle;
import com.bhushandeore.starwars.sample.domain.interactor.DefaultObserver;
import com.bhushandeore.starwars.sample.domain.interactor.GetPeopleDetails;
import com.bhushandeore.starwars.sample.domain.interactor.GetPeopleDetails.Params;
import com.bhushandeore.starwars.sample.presentation.exception.ErrorMessageFactory;
import com.bhushandeore.starwars.sample.presentation.di.PerActivity;
import com.bhushandeore.starwars.sample.presentation.mapper.PeopleModelDataMapper;
import com.bhushandeore.starwars.sample.presentation.model.PeopleModel;
import com.bhushandeore.starwars.sample.presentation.view.PeopleDetailsView;
import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class PeopleDetailsPresenter implements Presenter {

  private PeopleDetailsView viewDetailsView;

  private final GetPeopleDetails getPeopleDetailsUseCase;
  private final PeopleModelDataMapper peopleModelDataMapper;

  @Inject
  public PeopleDetailsPresenter(GetPeopleDetails getPeopleDetailsUseCase,
                                PeopleModelDataMapper peopleModelDataMapper) {
    this.getPeopleDetailsUseCase = getPeopleDetailsUseCase;
    this.peopleModelDataMapper = peopleModelDataMapper;
  }

  public void setView(@NonNull PeopleDetailsView view) {
    this.viewDetailsView = view;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  @Override public void destroy() {
    this.getPeopleDetailsUseCase.dispose();
    this.viewDetailsView = null;
  }

  /**
   * Initializes the presenter by showing/hiding proper views
   * and retrieving people details.
   */
  public void initialize(int id) {
    this.hideViewRetry();
    this.showViewLoading();
    this.getPeopleDetails(id);
  }

  private void getPeopleDetails(int id) {
    this.getPeopleDetailsUseCase.execute(new PeopleDetailsObserver(), Params.forPeople(id));
  }

  private void showViewLoading() {
    this.viewDetailsView.showLoading();
  }

  private void hideViewLoading() {
    this.viewDetailsView.hideLoading();
  }

  private void showViewRetry() {
    this.viewDetailsView.showRetry();
  }

  private void hideViewRetry() {
    this.viewDetailsView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.context(),
        errorBundle.getException());
    this.viewDetailsView.showError(errorMessage);
  }

  private void showPeopleDetailsInView(People people) {
    final PeopleModel peopleModel = this.peopleModelDataMapper.transform(people);
    this.viewDetailsView.renderPeople(peopleModel);
  }

  private final class PeopleDetailsObserver extends DefaultObserver<People> {

    @Override public void onComplete() {
      PeopleDetailsPresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
      PeopleDetailsPresenter.this.hideViewLoading();
      PeopleDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      PeopleDetailsPresenter.this.showViewRetry();
    }

    @Override public void onNext(People people) {
      PeopleDetailsPresenter.this.showPeopleDetailsInView(people);
    }
  }
}
