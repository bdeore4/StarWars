package com.bhushandeore.starwars.sample.presentation.presenter;

import android.support.annotation.NonNull;

import com.bhushandeore.starwars.sample.domain.People;
import com.bhushandeore.starwars.sample.domain.exception.DefaultErrorBundle;
import com.bhushandeore.starwars.sample.domain.exception.ErrorBundle;
import com.bhushandeore.starwars.sample.domain.interactor.DefaultObserver;
import com.bhushandeore.starwars.sample.domain.interactor.GetPeopleList;
import com.bhushandeore.starwars.sample.presentation.di.PerActivity;
import com.bhushandeore.starwars.sample.presentation.exception.ErrorMessageFactory;
import com.bhushandeore.starwars.sample.presentation.mapper.PeopleModelDataMapper;
import com.bhushandeore.starwars.sample.presentation.model.PeopleModel;
import com.bhushandeore.starwars.sample.presentation.view.PeopleListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class PeopleListPresenter implements Presenter {

    private PeopleListView viewListView;

    private final GetPeopleList getPeopleListUseCase;
    private final PeopleModelDataMapper peopleModelDataMapper;

    @Inject
    public PeopleListPresenter(GetPeopleList getPeopleListUseCase,
                               PeopleModelDataMapper peopleModelDataMapper) {
        this.getPeopleListUseCase = getPeopleListUseCase;
        this.peopleModelDataMapper = peopleModelDataMapper;
    }

    public void setView(@NonNull PeopleListView view) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getPeopleListUseCase.dispose();
        this.viewListView = null;
    }

    /**
     * Initializes the presenter by start retrieving the people list.
     */
    public void initialize(int page) {
        this.loadPeopleList(page);
    }

    /**
     * Loads all peoples.
     */
    private void loadPeopleList(int page) {
        this.hideViewRetry();

        if (page == 1) {
            this.showViewLoading();
        }
        this.getPeopleList(page);
    }

    public void onPeopleClicked(PeopleModel peopleModel) {
        this.viewListView.viewPeople(peopleModel);
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.context(),
                errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void showPeopleCollectionInView(Collection<People> peoplesCollection) {
        final Collection<PeopleModel> peopleModelsCollection =
                this.peopleModelDataMapper.transform(peoplesCollection);
        this.viewListView.renderPeopleList(peopleModelsCollection);
    }

    private void getPeopleList(int page) {
        this.getPeopleListUseCase.execute(new PeopleListObserver(), GetPeopleList.Params.forPeople(page));
    }

    private final class PeopleListObserver extends DefaultObserver<List<People>> {

        @Override
        public void onComplete() {
            PeopleListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            PeopleListPresenter.this.hideViewLoading();
            PeopleListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            viewListView.showViewRetry();
        }

        @Override
        public void onNext(List<People> peoples) {
            PeopleListPresenter.this.showPeopleCollectionInView(peoples);
        }
    }
}
