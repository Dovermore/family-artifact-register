package com.unimelb.family_artifact_register.UI.Artifact.ArtifactMap.Util;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.Util.IFragment;

/**
 * parent fragment for map fragment
 */
public abstract class BasePlacesFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    private static final String TAG = BasePlacesFragment.class.getSimpleName();

    /**
     * Create a new Places client instance
     */
    protected PlacesClient mPlacesClient = null;

    /**
     * This event fires 1st, before creation of fragment or any views The onAttach method is called
     * when the Fragment instance is associated with an Activity. This does not mean the Activity is
     * fully initialized.
     *
     * @param context context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            // To do: Might use in the future
            // this.listener = (FragmentActivity) context;

            if (!Places.isInitialized()) {
                // Initialize the SDK
                Places.initialize(getActivity(), getString(R.string.google_api_key));
            }
            /*
              Initialize Places. For simplicity, the API key is hard-coded.
             */
            this.mPlacesClient = Places.createClient(getActivity());
        }
    }
}
