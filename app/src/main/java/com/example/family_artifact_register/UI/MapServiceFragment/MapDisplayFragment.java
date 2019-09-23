package com.example.family_artifact_register.UI.MapServiceFragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.family_artifact_register.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link MapDisplayFragment.OnFragmentInteractionListener} interface to handle interaction events.
 * Use the {@link MapDisplayFragment#newInstance} factory method to create an instance of this
 * fragment.
 */
public class MapDisplayFragment extends BasePlacesFragment implements OnMapReadyCallback {
    /**
     * class tag
     */
    public static final String TAG = MapDisplayFragment.class.getSimpleName();
    static final String LOCATIONS = "locations";
    static final String STATIC = "static";
    // Stores the map object to be operated
    GoogleMap mMap;
    // Stores the locations to be displayed on screen
    private List<MyLocation> locations = new ArrayList<>();
    private boolean isStatic = false;
    // MapView the current fragment is operating on
    private MapView mapView;

    // TODO to be implemented in the future for a correct way for interaction
    private OnFragmentInteractionListener mListener;

    /**
     * Required empty public constructor
     */
    public MapDisplayFragment() { }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters. The map is by default interactive.
     * @param locations The locations to be displayed on the google map
     *
     * @return A new instance of fragment MapDisplayFragment.
     */
    public static MapDisplayFragment newInstance(List<MyLocation> locations) {
        return MapDisplayFragment.newInstance(locations, false);
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters. The points to be displayed is by default empty
     * @param staticMap If the map is static (limited interaction)
     *
     * @return A new instance of fragment MapDisplayFragment.
     */
    public static MapDisplayFragment newInstance(boolean staticMap) {
        return MapDisplayFragment.newInstance(new ArrayList<>(), staticMap);
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     * @param locations The locations to be displayed on the google map
     * @param staticMap If the map displayed should be static (limited interaction)
     *
     * @return A new instance of fragment MapDisplayFragment.
     */
    public static MapDisplayFragment newInstance(List<MyLocation> locations, boolean staticMap) {
        MapDisplayFragment fragment = new MapDisplayFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(STATIC, staticMap);
        bundle.putSerializable(LOCATIONS, (Serializable) locations);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @return A new instance of fragment MapDisplayFragment.
     */
    public static MapDisplayFragment newInstance() {
        MapDisplayFragment fragment = new MapDisplayFragment();
        Bundle bundle = new Bundle();
        List<MyLocation> locations = new ArrayList<>();
        bundle.putSerializable(LOCATIONS, (Serializable) locations);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.locations = (List<MyLocation>) this.getArguments().get(LOCATIONS);
            this.isStatic = (Boolean) this.getArguments().get(STATIC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map_display, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        // TODO Make it possbile to use lite map view, google map
        if (isStatic) {
            GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
        }
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    public void setDisplayLocations(List<MyLocation> locations) {
        this.locations = locations;
        for (MyLocation myLocation:locations) {
            Log.i(TAG, myLocation.toString());
        }
        displayLocations();
    }

    public List<MyLocation> getLocations() {
        return locations;
    }

    /**
     * Display the locations currently held in this fragment
     */
    private void displayLocations() {
        // Only display with marker if map is not null and there are locations stored
        if (mMap != null && locations != null && locations.size() != 0) {
            mMap.clear();
            List<Marker> markers = new ArrayList<>();
            for (MyLocation myLocation: this.locations) {
                // TODO can build map (with icon) here
                markers.add(mMap.addMarker(new MarkerOptions()
                        .position(myLocation.getLatLng())
                        .title(myLocation.getName())
                        .snippet(myLocation.getAddress())));
            }
            CameraUpdate cu = MarkerZoomStrategyFactory
                    .getMarkerZoomStrategyFactory()
                    .getMarkerZoomStrategy(markers.size())
                    .makeCameraUpdate(markers);
            mMap.animateCamera(cu);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        displayLocations();
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this fragment to allow an
     * interaction in this fragment to be communicated to the activity and potentially other
     * fragments contained in that activity.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
