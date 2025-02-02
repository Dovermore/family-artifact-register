package com.unimelb.family_artifact_register.UI.Artifact.ArtifactMap.DisplayLocationMap.CustomizedWindowDisplayLocationMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.Marker;
import com.google.android.material.button.MaterialButton;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Artifact.ArtifactDetail.ArtifactDetailActivity;
import com.unimelb.family_artifact_register.UI.Artifact.ArtifactTimeline.TimelineActivity;
import com.unimelb.family_artifact_register.Util.IFragment;

import static com.unimelb.family_artifact_register.UI.Artifact.ArtifactDetail.ArtifactDetailActivity.ARTIFACT_ITEM_ID_KEY;
import static com.unimelb.family_artifact_register.UI.Artifact.ArtifactTimeline.TimelineActivity.TIMELINE_ID_KEY;

/**
 * customized interactive info window for google map marker adapter pattern
 */
public class MyInfoWindow extends Fragment implements IFragment {

    /**
     * class tag
     */
    public static final String TAG = MyInfoWindow.class.getSimpleName();

    /**
     * marker
     */
    private Marker marker;

    /**
     * empty constructor
     */
    public MyInfoWindow() {
    }

    /**
     * @param marker marker
     * @return created interactive info window
     */
    public static MyInfoWindow newInstance(Marker marker) {
        MyInfoWindow myInfoWindow = new MyInfoWindow();
        myInfoWindow.marker = marker;
        return myInfoWindow;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.custom_info_map_window, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        render(view);
    }

    /**
     * inflate data on view
     *
     * @param view view
     */
    private void render(View view) {
        TextView title = view.findViewById(R.id.title);
        title.setText(marker.getTitle());
        String[] lines = marker.getSnippet().split("\n");
        String snippet = lines[0];
        String happenedTime = lines[1];
        String address = lines[2];
        String artifactItemId = lines[3];
        String timelineId = lines[4];

        TextView description = view.findViewById(R.id.snippet);
        description.setText(snippet);
        TextView addressView = view.findViewById(R.id.address);
        addressView.setText(address);
        TextView time = view.findViewById(R.id.create_time);
        time.setText(happenedTime);

        MaterialButton timeline = view.findViewById(R.id.timeline_button);
        timeline.setOnClickListener(view1 -> {
            System.out.println("clicked timeline !!!" + timelineId);
            Intent intent = new Intent(getContext(), TimelineActivity.class);
            intent.putExtra(TIMELINE_ID_KEY, timelineId);
            startActivity(intent);
        });
        MaterialButton detail = view.findViewById(R.id.detail_button);
        detail.setOnClickListener(view1 -> {
            System.out.println("clicked detail !!!" + artifactItemId);
            Intent intent = new Intent(getContext(), ArtifactDetailActivity.class);
            intent.putExtra(ARTIFACT_ITEM_ID_KEY, artifactItemId);
            startActivity(intent);
        });
    }
}
