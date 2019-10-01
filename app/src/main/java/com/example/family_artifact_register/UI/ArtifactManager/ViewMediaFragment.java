package com.example.family_artifact_register.UI.ArtifactManager;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.family_artifact_register.IFragment;
import com.example.family_artifact_register.R;
import com.example.family_artifact_register.UI.Util.ImagesRecyclerViewAdapter;

import java.util.List;

import static com.example.family_artifact_register.UI.Util.Constant.MEDIA_LIST;
import static com.example.family_artifact_register.UI.Util.Constant.MEDIA_TYPE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_IMAGE;
import static com.example.family_artifact_register.UI.Util.MediaProcessHelper.TYPE_VIDEO;

public class ViewMediaFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = ViewMediaFragment.class.getSimpleName();

    private int mediaType;

    private List<Uri> mediaList;

    /**
     * recycler view adapter
     */
    private ImagesRecyclerViewAdapter imagesRecyclerViewAdapter;

    RecyclerView imageRecyclerView;

    public ViewMediaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "me fragment created");
        return inflater.inflate(R.layout.fragment_view_media, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FrameLayout frameLayout = view.findViewById(R.id.fragment_view_media_container);

        Bundle bundle = this.getArguments();

        if(bundle != null){
            // get media type and media list
            mediaType = bundle.getInt(MEDIA_TYPE);
            mediaList = (List<Uri>) bundle.getSerializable(MEDIA_LIST);
        } else {
            Log.e(TAG, "error can't get data from parent fragment !!!");
        }

        if (mediaType == TYPE_IMAGE) {
            // set frame layout param
            FrameLayout.LayoutParams layoutParam = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 400);
            layoutParam.gravity = Gravity.CENTER;

            // set recycler view images
            RecyclerView.LayoutParams recyclerViewParam = new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
            );
            imageRecyclerView = new RecyclerView(getContext());
            imageRecyclerView.setLayoutParams(recyclerViewParam);

            // images horizontally
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            imageRecyclerView.setLayoutManager(layoutManager);

            // image adapter
            imagesRecyclerViewAdapter = new ImagesRecyclerViewAdapter();
            for (Uri image: mediaList) {
                imagesRecyclerViewAdapter.addData(image);
            }

            imageRecyclerView.setAdapter(imagesRecyclerViewAdapter);

            frameLayout.setLayoutParams(layoutParam);
            frameLayout.addView(imageRecyclerView);
        } else if (mediaType == TYPE_VIDEO) {
            // set frame layout param
            FrameLayout.LayoutParams layoutParam = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 400);
            layoutParam.gravity = Gravity.CENTER;

            // set media
            VideoView mediaView = new VideoView(getContext());
            mediaView.setVideoURI(mediaList.get(0));
            mediaView.setMediaController(new MediaController(getContext()));
            mediaView.start();
            mediaView.requestFocus();
            mediaView.setOnCompletionListener(mp -> {
                Log.d(TAG, "Video play finish.");
            });
            mediaView.setOnErrorListener((mp, what, extra) -> {
                Log.d(TAG, "Video play error!!!");
                return false;
            });

            frameLayout.setLayoutParams(layoutParam);
            frameLayout.addView(mediaView);
        } else {
            Log.e(TAG, "unknown media type !!!");
        }


    }

    /**
     * @return created view media fragment
     */
    public static ViewMediaFragment newInstance() { return new ViewMediaFragment(); }
}
