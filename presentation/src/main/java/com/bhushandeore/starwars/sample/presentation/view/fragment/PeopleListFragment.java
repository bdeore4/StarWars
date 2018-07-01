package com.bhushandeore.starwars.sample.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.bhushandeore.starwars.sample.presentation.R;
import com.bhushandeore.starwars.sample.presentation.di.components.PeopleComponent;
import com.bhushandeore.starwars.sample.presentation.model.PeopleModel;
import com.bhushandeore.starwars.sample.presentation.presenter.PeopleListPresenter;
import com.bhushandeore.starwars.sample.presentation.view.PeopleListView;
import com.bhushandeore.starwars.sample.presentation.view.adapter.PeoplesAdapter;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Fragment that shows a list of peoples.
 */
public class PeopleListFragment extends BaseFragment implements PeopleListView {

    /**
     * Interface for listening people list events.
     */
    public interface PeopleListListener {
        void onPeopleClicked(final PeopleModel peopleModel);
    }

    @Inject
    PeopleListPresenter peopleListPresenter;

    @Inject
    PeoplesAdapter peoplesAdapter;

    @Bind(R.id.rv_peoples)
    RecyclerView rv_peoples;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

    @Bind(R.id.empty_title)
    AppCompatTextView mEmptyTitle;

    private PeopleListListener peopleListListener;
    private int page = 1;
    private boolean mIsNoMoreResults;

    public PeopleListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof PeopleListListener) {
            this.peopleListListener = (PeopleListListener) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(PeopleComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_people_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.peopleListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadPeopleList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.peopleListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.peopleListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rv_peoples.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.peopleListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.peopleListListener = null;
    }

    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
    }

    @Override
    public void renderPeopleList(Collection<PeopleModel> peopleModelCollection) {
        try {

            if (peopleModelCollection != null) {
                if (peoplesAdapter != null && page == 1) {
                    peoplesAdapter.resetCollection();
                }

                if (peopleModelCollection.size() > 0 && peopleModelCollection.size() % 10 == 0) {
                    page++;
                    mIsNoMoreResults = true;
                } else {
                    mIsNoMoreResults = false;
                }

                if (peoplesAdapter != null) {
                    peoplesAdapter.setLoaded();
                    peoplesAdapter.removeProgressItem();
                    peoplesAdapter.setPeoplesCollection(peopleModelCollection);
                }
            }
        } catch (Exception e) {
            Timber.e("Exception caught in onSuccessGetBlogPostList ==>> " + e.getMessage());
        }
    }

    @Override
    public void viewPeople(PeopleModel peopleModel) {
        if (this.peopleListListener != null) {
            this.peopleListListener.onPeopleClicked(peopleModel);
        }
    }

    @Override
    public void showViewRetry() {
        if (page == 1) {
            showRetry();
        }
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    private void setupRecyclerView() {
        this.rv_peoples.setLayoutManager(new LinearLayoutManager(context()));
        this.peoplesAdapter.setRecyclerView(this.rv_peoples);
        this.rv_peoples.setAdapter(peoplesAdapter);
        this.peoplesAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_peoples.setHasFixedSize(true);
    }

    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

    private void handleEmptyScreen() {
        try {
            if (mEmptyTitle != null) {
                if (peoplesAdapter != null && peoplesAdapter.getPeoplesCollection() != null && peoplesAdapter.getPeoplesCollection().size() < 1) {
                    mEmptyTitle.setVisibility(View.VISIBLE);
                    mEmptyTitle.setText("Sorry, we couldn't find any people.");
                } else {
                    mEmptyTitle.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            Timber.e("Exception caught in handleEmptyScreen ==>> " + e.getMessage());
        }
    }

    /**
     * Loads all peoples.
     */
    private void loadPeopleList() {
        this.peopleListPresenter.initialize(page);
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        PeopleListFragment.this.loadPeopleList();
    }

    private PeoplesAdapter.OnItemClickListener onItemClickListener =
            new PeoplesAdapter.OnItemClickListener() {

                @Override
                public void onPeopleItemClicked(PeopleModel peopleModel) {
                    if (PeopleListFragment.this.peopleListPresenter != null && peopleModel != null) {
                        PeopleListFragment.this.peopleListPresenter.onPeopleClicked(peopleModel);
                    }
                }

                @Override
                public void getMorePeoples() {
                    try {
                        if (isThereInternetConnection()) {
                            if (peoplesAdapter != null) {
                                peoplesAdapter.addProgressItem();
                                loadPeopleList();
                            }
                        } else {

                            showToastMessage("No Internet Connection. Please try Again.");
                        }
                    } catch (Exception e) {
                        Timber.e("Exception caught in getMoreBlogPosts ==>> " + e.getMessage());
                    }
                }

                @Override
                public void handleEmptyState() {
                    handleEmptyScreen();
                }

                @Override
                public boolean stopPaginationRequest() {
                    return mIsNoMoreResults;
                }
            };
}
