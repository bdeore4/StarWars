package com.bhushandeore.starwars.sample.presentation.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.bhushandeore.starwars.sample.presentation.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProgressViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.progress_bar)
    public ProgressBar mProgress;

    public ProgressViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }

}