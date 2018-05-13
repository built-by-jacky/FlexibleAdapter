package eu.davidea.samples.flexibleadapter.overall;

import android.animation.Animator;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.helpers.AnimatorHelper;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.utils.FlexibleUtils;
import eu.davidea.samples.flexibleadapter.R;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Model object representing Overall functionality as CardView.
 */
public class FeatureItem extends AbstractFlexibleItem<FeatureItem.LabelViewHolder> {

    private int id;
    private String title;
    private String description;
    private Drawable icon;

    public FeatureItem(int id, String title) {
        this.id = id;
        this.title = title;
        setSelectable(false);
        //Allow dragging
        setDraggable(true);
    }

    public FeatureItem withDescription(String description) {
        this.description = description;
        return this;
    }

    public FeatureItem withIcon(Drawable icon) {
        this.icon = icon;
        return this;
    }

    public FeatureItem withEnabled(boolean enabled) {
        setEnabled(enabled);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeatureItem that = (FeatureItem) o;
        return id == that.id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.recycler_overall_item;
    }

    @Override
    public LabelViewHolder createViewHolder(View view, FlexibleAdapter adapter) {
        return new LabelViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, LabelViewHolder holder, int position, List payloads) {
        if (getTitle() != null) {
            holder.mTitle.setText(getTitle());
            holder.mTitle.setEnabled(isEnabled());
        }
        if (getDescription() != null) {
            holder.mSubtitle.setText(FlexibleUtils.fromHtmlCompat(getDescription()));
            holder.mSubtitle.setEnabled(isEnabled());
        }
        if (getIcon() != null) {
            holder.mIcon.setImageDrawable(getIcon());
        }
    }

    @Override
    public int hashCode() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }


    public static class LabelViewHolder extends FlexibleViewHolder {

        @BindView(R.id.title)
        public TextView mTitle;
        @BindView(R.id.subtitle)
        public TextView mSubtitle;
        @BindView(R.id.icon_background)
        public ImageView mIcon;

        public LabelViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            ButterKnife.bind(this, view);
        }

        @Override
        public void scrollAnimators(@NonNull List<Animator> animators, int position, boolean isForward) {
            if (mAdapter.getRecyclerView().getLayoutManager() instanceof GridLayoutManager) {
                if (position % 2 != 0)
                    AnimatorHelper.slideInFromRightAnimator(animators, itemView, mAdapter.getRecyclerView(), 0.5f);
                else
                    AnimatorHelper.slideInFromLeftAnimator(animators, itemView, mAdapter.getRecyclerView(), 0.5f);
            } else {
                if (isForward)
                    AnimatorHelper.slideInFromBottomAnimator(animators, itemView, mAdapter.getRecyclerView());
                else
                    AnimatorHelper.slideInFromTopAnimator(animators, itemView, mAdapter.getRecyclerView());
            }
        }
    }

}