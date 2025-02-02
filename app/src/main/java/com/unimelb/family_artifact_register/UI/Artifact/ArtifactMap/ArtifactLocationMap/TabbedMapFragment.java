package com.unimelb.family_artifact_register.UI.Artifact.ArtifactMap.ArtifactLocationMap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.unimelb.family_artifact_register.R;
import com.unimelb.family_artifact_register.Util.IFragment;

/**
 * fragment with two map fragment: happened map and stored map
 */
public class TabbedMapFragment extends Fragment implements IFragment {
    /**
     * class tag
     */
    public static final String TAG = TabbedMapFragment.class.getSimpleName();

    public TabbedMapFragment() {
        // Required empty public constructor
    }

    /**
     * @return created tabbed map fragment
     */
    public static TabbedMapFragment newInstance() {
        return new TabbedMapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "me fragment created");
        return inflater.inflate(R.layout.fragment_tabbed_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.artifact_map_title);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(getString(R.string.artifact_items_map_happened_title), AllArtifactHappenedMapFragment.class)
                .add(getString(R.string.artifact_items_map_stored_title), AllArtifactStoredMapFragment.class)
                .create());

        ViewPager viewPager = view.findViewById(R.id.fragment_tabbed_map_view_pager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = view.findViewById(R.id.fragment_tabbed_map_view_pager_tab);
        viewPagerTab.setViewPager(viewPager);
    }
}
