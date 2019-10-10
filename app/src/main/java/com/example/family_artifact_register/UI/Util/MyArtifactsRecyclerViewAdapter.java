package com.example.family_artifact_register.UI.Util;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.family_artifact_register.PresentationLayer.ArtifactManagerPresenter.ArtifactItemWrapper;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.ArtifactTimeline.TimelineActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.family_artifact_register.UI.ArtifactTimeline.TimelineActivity.TIMELINE_ID_KEY;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;

/**
 * @author XuLin Yang 904904,
 * @time 2019-9-21 15:57:25
 * @description
 */
public class MyArtifactsRecyclerViewAdapter extends RecyclerView.Adapter<MyArtifactsRecyclerViewHolder> {

    private static final String TAG = MyArtifactsRecyclerViewAdapter.class.getSimpleName();
    private List<ArtifactItemWrapper> artifactItemWrapperList;

//    private FragmentManager fm;

    private Context context;

    private boolean isFullScreen = false;

    private List<Uri> mediaList;

    public MyArtifactsRecyclerViewAdapter(Context context) {
        this.artifactItemWrapperList = new ArrayList<>();
        this.context = context;
//        this.fm = fm;
    }

    @NonNull
    @Override
    public MyArtifactsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_artifact, parent, false);
        return new MyArtifactsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyArtifactsRecyclerViewHolder holder, int position) {
        ArtifactItemWrapper artifactItemWrapper = artifactItemWrapperList.get(position);

        holder.time.setText(artifactItemWrapper.getLastUpdateDateTime());
        holder.description.setText(artifactItemWrapper.getDescription());

        mediaList = new ArrayList<>();
        for (String mediaUrl: artifactItemWrapper.getLocalMediaDataUrls()) {
            Log.d(TAG, "media uri" + mediaUrl);
            mediaList.add(Uri.parse(mediaUrl));
        }

        holder.clearFrame();
        // set frame layout param
        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParam.gravity = Gravity.CENTER;
        layoutParam.topMargin = 20;
        layoutParam.bottomMargin = 20;

        // image view
        if (artifactItemWrapper.getMediaType() == TYPE_IMAGE) {

            View imagesRecyclerView = getImageRecyclerView();

            holder.frame.addView(imagesRecyclerView);
            holder.frame.setLayoutParams(layoutParam);
        // video view
        } else if (artifactItemWrapper.getMediaType() == TYPE_VIDEO) {
            ImageView iv = getVideoThumbnail();

            ImageView playIcon = getVideoPlayIcon();

            // set frame's layout and add image view to it programmatically
            holder.frame.addView(iv);
            holder.frame.addView(playIcon);
            holder.frame.setLayoutParams(layoutParam);
        } else {
            Log.e(TAG, "unknown media type !!!");
        }

        holder.navigateToArtifactTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "#" + position + " holder.navigateToArtifactTimeline clicked");
                Intent activityChangeIntent = new Intent(context, TimelineActivity.class);
                activityChangeIntent.putExtra(TIMELINE_ID_KEY, artifactItemWrapperList.get(position).getArtifactTimelineId());
                context.startActivity(activityChangeIntent);
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

    public void setData(List<ArtifactItemWrapper> newData) {
        artifactItemWrapperList.clear();

        Collections.sort(newData, new Comparator<ArtifactItemWrapper>() {
            @Override
            public int compare(ArtifactItemWrapper artifactItemWrapper, ArtifactItemWrapper t1) {
                return -1 * artifactItemWrapper.getLastUpdateDateTime().compareTo(t1.getLastUpdateDateTime());
            }
        });
        artifactItemWrapperList.addAll(newData);
        notifyDataSetChanged();
    }

    // *************************************** getter & setters ***********************************
    private RecyclerView getImageRecyclerView() {
        // recycler view adapter for display images
        ImagesRecyclerViewAdapter imagesRecyclerViewAdapter;
        // recycler view for display images
        RecyclerView imageRecyclerView;

        // set recycler view images
        RecyclerView.LayoutParams recyclerViewParam = new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        );
        imageRecyclerView = new RecyclerView(context);
        imageRecyclerView.setLayoutParams(recyclerViewParam);

        // images horizontally
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
        );
        imageRecyclerView.setLayoutManager(layoutManager);

        // set the divider between list item
        DividerItemDecoration divider = new DividerItemDecoration(
                imageRecyclerView.getContext(),
                layoutManager.getOrientation()
        );
        divider.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider_wechat_white));
        imageRecyclerView.addItemDecoration(divider);

        // image adapter
        imagesRecyclerViewAdapter = new ImagesRecyclerViewAdapter(
                200,
                200,
                context
        );
        for (Uri image: mediaList) {
            imagesRecyclerViewAdapter.addData(image);
        }
        imageRecyclerView.setAdapter(imagesRecyclerViewAdapter);

        return imageRecyclerView;
    }

    private ImageView getVideoThumbnail() {
        // set up image view layout
        ImageView iv = new ImageView(context);
        iv.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
        // image cropped in center to ge square
        iv.setAdjustViewBounds(true);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // set thumbnail from video to image
        Glide.with(context)
                .load(mediaList.get(0)) // or URI/path
                .into(iv); //imageview to set thumbnail to
        // start video when clicked the thumbnail
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // whole screen dialog of image
                Dialog dia = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

                VideoView videoView = new VideoView(context);
                videoView.setLayoutParams(
                        new ActionBar.LayoutParams(
                                ActionBar.LayoutParams.MATCH_PARENT,
                                ActionBar.LayoutParams.MATCH_PARENT
                        )
                );
                videoView.setVideoURI(mediaList.get(0));
                videoView.setMediaController(new MediaController(context));
                videoView.start();
                videoView.requestFocus();
                videoView.setOnCompletionListener(mp -> {
                    Log.d(TAG, "Video play finish.");
                });
                videoView.setOnErrorListener((mp, what, extra) -> {
                    Log.d(TAG, "Video play error!!!");
                    return false;
                });
                // click to return
                videoView.setOnClickListener(v -> {
                    dia.dismiss();
                });
                dia.setContentView(videoView);
                dia.show();

                dia.setCanceledOnTouchOutside(true); // Sets whether this dialog is
                Window w = dia.getWindow();
                WindowManager.LayoutParams lp = w.getAttributes();
                lp.x = 0;
                lp.y = 40;
                dia.onWindowAttributesChanged(lp);
            }
        });

        return iv;
    }

    private ImageView getVideoPlayIcon() {
        // add a play icon to the thumbnail
        ImageView playIcon = new ImageView(context);
        LinearLayout.LayoutParams playIconLayout = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        playIconLayout.gravity = Gravity.CENTER;
        playIcon.setLayoutParams(playIconLayout);
        playIcon.setImageResource(R.drawable.ic_play_circle_filled_white);

        return playIcon;
    }


//    public void addData(ArtifactItem artifactItem) {
//        // 0 to add data at start
//        this.artifactItemWrapperList.add(0, artifactItem);
//        notifyDataSetChanged();
//    }
}
