package com.unimelb.family_artifact_register.UI.Artifact.NewArtifact;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.UI.Artifact.NewArtifact.Util.ActivityFragmentListener.MediaListener;
import com.unimelb.family_artifact_register.UI.Util.OnBackPressedListener;
import com.unimelb.family_artifact_register.Util.IFragment;

import java.util.List;

/**
 * fragment to for user to preview chosen video
 */
public class NewArtifactPreviewVideoFragment extends Fragment implements IFragment, OnBackPressedListener {
    /**
     * class tag
     */
    public static final String TAG = NewArtifactPreviewVideoFragment.class.getSimpleName();

    /**
     * required empty constructor
     */
    public NewArtifactPreviewVideoFragment() {
    }

    public static NewArtifactPreviewVideoFragment newInstance() {
        return new NewArtifactPreviewVideoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_artifact_preview_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VideoView video = view.findViewById(R.id.fragment_new_artifact_preview_video_view);
        List<Uri> medias = ((MediaListener) getActivity()).getData();
        if (!medias.isEmpty()) {
            // only one video uri in the list
            video.setVideoURI(medias.get(0));
            video.setMediaController(new MediaController(getContext()));
            video.start();
            video.requestFocus();
            video.setOnCompletionListener(mp -> {
                Log.d(TAG, "Video play finish.");
            });

            video.setOnErrorListener((mp, what, extra) -> {
                Log.d(TAG, "Video play error!!!");
                return false;
            });
        } else {
            Log.e(TAG, "no video found !!!");
        }

        FloatingActionButton confirm = view.findViewById(R.id.fragment_new_artifact_preview_video_floating_button_confirm);
        confirm.setOnClickListener(view1 -> {
            NewArtifactHappenedTimeFragment happenedTime = NewArtifactHappenedTimeFragment.newInstance();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack("next");
            fragmentTransaction.replace(R.id.activity_new_artifact_main_view, happenedTime);
            fragmentTransaction.commit();
        });
    }

    // ************************************ implement interface ***********************************
    @Override
    public void onBackPressed() {
        ((MediaListener) getActivity()).clearData();
    }
}
