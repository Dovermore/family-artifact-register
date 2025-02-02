package com.unimelb.family_artifact_register.UI.Artifact.ArtifactHub.Util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimelb.family_artifact_register.PresentationLayer.HubPresenter.HubViewModel;
import com.unimelb.family_artifact_register.PresentationLayer.Util.ArtifactPostWrapper;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Artifact.ArtifactComment.ArtifactCommentActivity;
import com.unimelb.family_artifact_register.UI.Artifact.ArtifactDetail.ArtifactDetailActivity;
import com.unimelb.family_artifact_register.UI.Artifact.ArtifactTimeline.TimelineActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.unimelb.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getImageRecyclerView;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getVideoPlayIcon;
import static com.unimelb.family_artifact_register.UI.Util.MediaViewHelper.getVideoThumbnail;

/**
 * @author Haichao Song 854035,
 * @time 2019-9-18 14:28:43
 * @description Adapter for models recycler view
 */
public class HubModelAdapter extends RecyclerView.Adapter<HubModelViewHolder> {

    private static final String TAG = HubModelAdapter.class.getSimpleName();
    private List<ArtifactPostWrapper> artifactItemWrapperList;

    private Context context;

    private List<Uri> mediaList;

    private HubViewModel viewModel;

    public HubModelAdapter(Context context, HubViewModel hubViewModel) {
        this.artifactItemWrapperList = new ArrayList<>();
        this.context = context;
        this.viewModel = hubViewModel;
    }

    @NonNull
    @Override
    public HubModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,
                parent, false);
        return new HubModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HubModelViewHolder holder, int position) {
        ArtifactPostWrapper artifactItemWrapper = artifactItemWrapperList.get(position);

        holder.username.setText(artifactItemWrapper.getUserInfoWrapper().getUid());
        holder.time.setText(artifactItemWrapper.getArtifactItemWrapper().getUploadDateTime());
        holder.description.setText(artifactItemWrapper.getArtifactItemWrapper().getDescription());
        holder.username.setText(artifactItemWrapper.getUserInfoWrapper().getDisplayName());
        String url = artifactItemWrapper.getUserInfoWrapper().getPhotoUrl();

        // set poster avatar in post item
        if (url != null) {
            holder.avatar.setImageURI(Uri.parse(url));
        }

        // set like image in post item
        holder.likes.setText(Integer.toString(artifactItemWrapper.getArtifactItemWrapper()
                .getLikes().size()));
        Log.d(TAG, "likes: " + artifactItemWrapper.getArtifactItemWrapper().getLikes());
        if ((artifactItemWrapper.getArtifactItemWrapper().getLikes().size() != 0) &&
                (artifactItemWrapper.getArtifactItemWrapper().getLikes().keySet().contains(
                        viewModel.getCurrentUid()))) {
            holder.like.setImageResource(R.drawable.ic_liked);
            holder.like.setTag("liked");
        } else {
            holder.like.setImageResource(R.drawable.ic_like);
            holder.like.setTag("unliked");
        }

        mediaList = new ArrayList<>();
        for (String mediaUrl : artifactItemWrapper.getArtifactItemWrapper()
                .getLocalMediaDataUrls()) {
            Log.d(TAG, "media uri" + mediaUrl);
            mediaList.add(Uri.parse(mediaUrl));
        }

        // render images or video in post frame layout
        holder.clearFrame();

        // set frame layout param
        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParam.gravity = Gravity.CENTER;
        layoutParam.topMargin = 20;
        layoutParam.bottomMargin = 20;
        layoutParam.leftMargin = 20;
        layoutParam.rightMargin = 20;

        // image view
        if (artifactItemWrapper.getArtifactItemWrapper().getMediaType() == TYPE_IMAGE) {

            View imagesRecyclerView = getImageRecyclerView(500, 500,
                    mediaList, context);

            holder.postImage.addView(imagesRecyclerView);
            holder.postImage.setLayoutParams(layoutParam);
            // video view
        } else if (artifactItemWrapper.getArtifactItemWrapper().getMediaType() == TYPE_VIDEO) {
            ImageView iv = getVideoThumbnail(500, 500,
                    mediaList.get(0), context);

            ImageView playIcon = getVideoPlayIcon(context);

            // set frame's layout and add image view to it programmatically
            holder.postImage.addView(iv);
            holder.postImage.addView(playIcon);
            holder.postImage.setLayoutParams(layoutParam);
        } else {
            Log.e(TAG, "unknown media type !!!");
        }

        // go to detail page when user click detail button
        holder.viewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pid = artifactItemWrapper.getArtifactItemWrapper().getPostId();
                Intent i = new Intent(view.getContext(), ArtifactDetailActivity.class);
                i.putExtra("artifactItemPostId", pid);
                context.startActivity(i);
            }
        });

        // go to timeline page when user click timeline page
        holder.timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tid = artifactItemWrapper.getArtifactItemWrapper().getArtifactTimelineId();
                Intent i = new Intent(view.getContext(), TimelineActivity.class);
                i.putExtra("timelineID", tid);
                context.startActivity(i);
            }
        });

        // change like image and set local likes number when user click like image
        // send likes map change to backend
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int likes_before = Integer.valueOf(holder.likes.getText().toString());
                Log.d(TAG, "number of likes before:" + likes_before);
                if (holder.like.getTag() == "liked") {
                    holder.like.setImageResource(R.drawable.ic_like);
                    holder.like.setTag("unlike");
                    holder.likes.setText(String.valueOf(likes_before - 1));
                } else {
                    holder.like.setImageResource(R.drawable.ic_liked);
                    holder.like.setTag("liked");
                    holder.likes.setText(String.valueOf(likes_before + 1));
                }
                viewModel.getLikeChange(holder.like.getTag().toString(), artifactItemWrapper
                        .getArtifactItemWrapper().getPostId());
            }
        });

        // go to comment page when user press comment button
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pid = artifactItemWrapper.getArtifactItemWrapper().getPostId();
                Intent i = new Intent(view.getContext(), ArtifactCommentActivity.class);
                i.putExtra("artifactItemPostId", pid);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (artifactItemWrapperList == null) {
            return 0;
        }
        return artifactItemWrapperList.size();
    }

    /**
     * sort and replace post item list with new data
     *
     * @param newData new changed list of post items
     */
    public void setData(List<ArtifactPostWrapper> newData) {
        artifactItemWrapperList.clear();

        if (newData != null) {
            Collections.sort(newData, new Comparator<ArtifactPostWrapper>() {
                @Override
                public int compare(ArtifactPostWrapper artifactItemWrapper,
                                   ArtifactPostWrapper t1) {
                    return -1 * artifactItemWrapper.getArtifactItemWrapper().
                            getLastUpdateDateTime().compareTo(t1.getArtifactItemWrapper()
                            .getLastUpdateDateTime());
                }
            });
            artifactItemWrapperList.addAll(newData);
        }
        notifyDataSetChanged();
    }


    // *************************************** getter & setters ***********************************
    public void addData(ArtifactPostWrapper artifactItemWrapper) {
        // 0 to add data at start
        this.artifactItemWrapperList.add(0, artifactItemWrapper);
        notifyDataSetChanged();
    }
}
