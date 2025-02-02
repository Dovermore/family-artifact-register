package com.unimelb.family_artifact_register.UI.Artifact.ViewArtifact.Util;

import android.view.View;
import android.widget.TextView;

import com.unimelb.family_artifact_register.R;

import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;
import tellh.com.stickyheaderview_rv.adapter.ViewBinder;

/**
 * adapter sticky header
 */
public class ArtifactItemHeaderViewBinder extends ViewBinder<StickyArtifactItemHeader, ArtifactItemHeaderViewBinder.ViewHolder> {

    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(StickyHeaderViewAdapter adapter, ViewHolder holder, int position, StickyArtifactItemHeader entity) {
        holder.tvPrefix.clearComposingText();
        holder.tvPrefix.setText(entity.getPrefix());
    }

    @Override
    public int getItemLayoutId(StickyHeaderViewAdapter adapter) {
        return R.layout.artifact_item_sticky_header;
    }

    /**
     * sticky header's view holder
     */
    static class ViewHolder extends ViewBinder.ViewHolder {
        /**
         * simple text
         */
        TextView tvPrefix;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tvPrefix = rootView.findViewById(R.id.tv_prefix);
        }
    }
}
