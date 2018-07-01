package com.bhushandeore.starwars.sample.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhushandeore.starwars.sample.presentation.R;
import com.bhushandeore.starwars.sample.presentation.constant.PresentationConstant;
import com.bhushandeore.starwars.sample.presentation.model.PeopleModel;
import com.bhushandeore.starwars.sample.presentation.view.ProgressViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Adaptar that manages a collection of {@link PeopleModel}.
 */
public class PeoplesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickListener {
        void onPeopleItemClicked(PeopleModel peopleModel);

        void getMorePeoples();

        void handleEmptyState();

        boolean stopPaginationRequest();
    }

    private List<PeopleModel> peoplesCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    private int mVisibleThreshold = 2;
    private int mLastVisibleItem, mTotalItemCount;
    private boolean mLoading;

    @Inject
    PeoplesAdapter(Context context) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.peoplesCollection = Collections.emptyList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case PresentationConstant.ITEM_VIEW_TYPE_PEOPLE_CODE:
                View userView = inflater.inflate(R.layout.row_people, parent, false);
                viewHolder = new PeopleViewHolder(userView);
                break;
            case PresentationConstant.ITEM_VIEW_TYPE_PROGRESS_CODE:
                View progressView = inflater.inflate(R.layout.progress_bar_load_more_item, parent, false);
                viewHolder = new ProgressViewHolder(progressView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        try {
            switch (viewHolder.getItemViewType()) {
                case PresentationConstant.ITEM_VIEW_TYPE_PEOPLE_CODE:
                    PeopleViewHolder peopleViewHolder = (PeopleViewHolder) viewHolder;
                    configurePeopleViewHolder(peopleViewHolder);
                    break;
                case PresentationConstant.ITEM_VIEW_TYPE_PROGRESS_CODE:
                    ProgressViewHolder progressViewHolder = (ProgressViewHolder) viewHolder;
                    configureProgressViewHolder(progressViewHolder);
                    break;
            }
        } catch (Exception e) {
            Timber.e("Exception caught in onBindViewHolder ==>>" + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return peoplesCollection != null ? peoplesCollection.size() : 0;
    }

    public void setLoaded() {
        mLoading = false;
    }

    @Override
    public int getItemViewType(int position) {
        final PeopleModel peopleModel = peoplesCollection.get(position);
        String itemViewType = peopleModel.itemViewType != null ? peopleModel.itemViewType : "";
        if (itemViewType.equalsIgnoreCase(PresentationConstant.ITEM_VIEW_TYPE_PROGRESS)) {
            return PresentationConstant.ITEM_VIEW_TYPE_PROGRESS_CODE;
        } else if (itemViewType.equalsIgnoreCase(PresentationConstant.ITEM_VIEW_TYPE_PEOPLE)) {
            return PresentationConstant.ITEM_VIEW_TYPE_PEOPLE_CODE;
        } else {
            return PresentationConstant.ITEM_VIEW_TYPE_PEOPLE_CODE;
        }
    }

    private void configurePeopleViewHolder(final PeopleViewHolder holder) {
        try {
            final PeopleModel peopleModel = this.peoplesCollection.get(holder.getAdapterPosition());
            holder.textViewTitle.setText(peopleModel.name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PeoplesAdapter.this.onItemClickListener != null) {
                        PeoplesAdapter.this.onItemClickListener.onPeopleItemClicked(peopleModel);
                    }
                }
            });
        } catch (Exception e) {
            Timber.e("Exception caught in configureBlogPostViewHolder ==>> " + e.getMessage());
        }
    }

    private void configureProgressViewHolder(ProgressViewHolder progressViewHolder) {
        try {
            progressViewHolder.mProgress.setIndeterminate(true);
        } catch (Exception e) {
            Timber.e("Exception caught in configureProgressViewHolder ==>> " + e.getMessage());
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPeoplesCollection(final Collection<PeopleModel> collection) {
        try {
            mRecyclerView.post(new Runnable() {
                public void run() {
                    try {
                        if (collection != null && collection.size() > 0) {
                            peoplesCollection.addAll((List<PeopleModel>) collection);
                            notifyDataSetChanged();
                        }
                        if (onItemClickListener != null) {
                            onItemClickListener.handleEmptyState();
                        }
                    } catch (Exception e) {
                        Timber.e("Exception caught in notifyDataSetChanged ==>>" + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            Timber.e("Exception caught in setCollection ==>> " + e.getMessage());
        }
    }

    public List<PeopleModel> getPeoplesCollection() {
        return peoplesCollection;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        peoplesCollection = new ArrayList<PeopleModel>();
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        }
        setScrollListener();
    }

    private void setScrollListener() {
        try {
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    try {
                        super.onScrolled(recyclerView, dx, dy);
                        mTotalItemCount = mLinearLayoutManager.getItemCount();
                        mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                        if (!mLoading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {
                            if (onItemClickListener != null && peoplesCollection.size() > 2) {
                                if (onItemClickListener.stopPaginationRequest()) {
                                    onItemClickListener.getMorePeoples();
                                }
                            }
                            mLoading = true;
                        }
                    } catch (Exception e) {
                        Timber.e("Exception caught in setScrollListener onScrolled ==>> " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            Timber.e("Exception caught in setScrollListener ==>> " + e.getMessage());
        }
    }

    public void addProgressItem() {
        try {
            mRecyclerView.post(new Runnable() {
                public void run() {
                    try {
                        if (peoplesCollection.size() > 0) {
                            PeopleModel progressView = new PeopleModel();
                            progressView.itemViewType = PresentationConstant.ITEM_VIEW_TYPE_PROGRESS;
                            peoplesCollection.add(progressView);
                            notifyItemInserted(peoplesCollection.size() - 1);
                        }
                    } catch (Exception e) {
                        Timber.e("Exception caught in notifyItemInserted ==>>" + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            Timber.e("Exception caught in addProgressItem ==>> " + e.getMessage());
        }
    }

    public void removeProgressItem() {
        try {
            mRecyclerView.post(new Runnable() {
                public void run() {
                    try {
                        if (peoplesCollection.size() > 0) {
                            peoplesCollection.remove(peoplesCollection.size() - 1);
                            notifyItemRemoved(peoplesCollection.size());
                        }
                    } catch (Exception e) {
                        Timber.e("Exception caught in notifyItemRemoved ==>>" + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            Timber.e("Exception caught in removeProgressItem ==>> " + e.getMessage());
        }
    }


    public void resetCollection() {
        try {
            mRecyclerView.post(new Runnable() {
                public void run() {
                    try {
                        if (peoplesCollection != null) {
                            peoplesCollection.clear();
                            notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        Timber.e("Exception caught in notifyDataSetChanged ==>>" + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            Timber.e("Exception caught ==>> " + e.getMessage());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validatePeoplesCollection(Collection<PeopleModel> peoplesCollection) {
        if (peoplesCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class PeopleViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        AppCompatTextView textViewTitle;

        PeopleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
