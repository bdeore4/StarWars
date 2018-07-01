package com.bhushandeore.starwars.sample.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhushandeore.starwars.sample.presentation.R;
import com.bhushandeore.starwars.sample.presentation.di.components.PeopleComponent;
import com.bhushandeore.starwars.sample.presentation.model.PeopleModel;
import com.bhushandeore.starwars.sample.presentation.presenter.PeopleDetailsPresenter;
import com.bhushandeore.starwars.sample.presentation.view.PeopleDetailsView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Fragment that shows details of a certain people.
 */
public class PeopleDetailsFragment extends BaseFragment implements PeopleDetailsView {
    private static final String PARAM_PEOPLE_ID = "param_people_id";

    @Inject
    PeopleDetailsPresenter peopleDetailsPresenter;

    @Bind(R.id.name_label)
    TextView nameValue;
    @Bind(R.id.created_date_label)
    TextView createdDateValue;
    @Bind(R.id.height_value)
    TextView heightValue;
    @Bind(R.id.mass_value)
    TextView massValue;

    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

    public static PeopleDetailsFragment forPeople(int id) {
        final PeopleDetailsFragment peopleDetailsFragment = new PeopleDetailsFragment();
        final Bundle arguments = new Bundle();
        arguments.putInt(PARAM_PEOPLE_ID, id);
        peopleDetailsFragment.setArguments(arguments);
        return peopleDetailsFragment;
    }

    public PeopleDetailsFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(PeopleComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_people_details, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.peopleDetailsPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadPeopleDetails();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.peopleDetailsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.peopleDetailsPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.peopleDetailsPresenter.destroy();
    }

    @Override
    public void renderPeople(PeopleModel peopleModel) {
        try {
            if (peopleModel != null) {
                this.nameValue.setText(peopleModel.name);
                this.createdDateValue.setText(peopleModel.created);

                if (peopleModel.height.equalsIgnoreCase("unknown")) {
                    this.heightValue.setText(peopleModel.height);
                } else {
                    double meter = (Integer.parseInt(peopleModel.height)) * 0.0254;
                    this.heightValue.setText(String.valueOf(meter));

                }
                this.massValue.setText(peopleModel.mass);
            }
        } catch (Exception e) {
            Timber.d("Exception when when rendering data: " + e);
        }
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
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    /**
     * Load people details.
     */
    private void loadPeopleDetails() {
        if (this.peopleDetailsPresenter != null) {
            this.peopleDetailsPresenter.initialize(currentPeopleId());
        }
    }

    /**
     * Get current people id from fragments arguments.
     */
    private int currentPeopleId() {
        final Bundle arguments = getArguments();
        return arguments.getInt(PARAM_PEOPLE_ID);
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        PeopleDetailsFragment.this.loadPeopleDetails();
    }
}
