package com.example.family_artifact_register.FoundationLayer.MapModel;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.family_artifact_register.FoundationLayer.Util.DBConstant;
import com.example.family_artifact_register.FoundationLayer.Util.FirebaseStorageHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton manager for managing firebase related access with MapLocations.
 */
public class MapLocationManager {
    /**
    Tag for logging
     */
    private static final String TAG = MapLocationManager.class.getSimpleName();

    private static final MapLocationManager ourInstance = new MapLocationManager();

    public static MapLocationManager getInstance() {
        return ourInstance;
    }

    /**
     * The database reference used.
     */
    private CollectionReference mMapLocationCollection;

    /**
     * Storage reference for storing photo
     */
    private StorageReference mMapLocationPhotoReference;

    private MapLocationManager() {
        mMapLocationCollection = FirebaseFirestore.getInstance().collection(DBConstant.MAP_LOCATION);
        mMapLocationPhotoReference = FirebaseStorage.getInstance().getReference(DBConstant.MAP_LOCATION_PHOTO_URL);
    }

    /**
     * Store the mapLocation to database.
     *
     * @param mapLocation The map location to store to database
     */
    public void storeMapLocation(MapLocation mapLocation) {
        DocumentReference mapLocationReference;
        // Assign mapLocationId if not yet assigned.
        if (mapLocation.getMapLocationId() == null) {
            // Generate the id automatically
            mapLocationReference = mMapLocationCollection.document();
            mapLocation.setMapLocationId(mapLocationReference.getId());
        } else {
            mapLocationReference = mMapLocationCollection
                    .document(mapLocation.getMapLocationId());
        }
        // Get reference based on current mapLocation id
        StorageReference mapLocationImagesReference = mMapLocationPhotoReference
                .child(mapLocation.getMapLocationId());

        // Save to database
        // First save image then along with the reference push to database
        // Need to:
        // 1. generate corresponding image url from current url (map)
        // 2. store the url
        Map<String, String> imageUrlMap = new HashMap<>();

        int i = 0;
        Log.i(TAG, "making location image reference map...");
        if (mapLocation.getImageUrls() != null) {
            for (String imageUrl : mapLocation.getImageUrls()) {
                imageUrlMap.put(mapLocation.getMapLocationId()+"_"+i, imageUrl);
                Log.i(TAG, "Url: {" + mapLocation.getMapLocationId()+"_"+i + ", " + imageUrlMap.get(imageUrl) + "}");
                i += 1;
            }
        }

        Log.i(TAG, "adding location image...");
        for (String key: imageUrlMap.keySet()) {
            Log.i(TAG, "Url: {" + key + ", " + imageUrlMap.get(key) + "}");
            FirebaseStorageHelper.getInstance()
                    .uploadByUri(Uri.parse(imageUrlMap.get(key)), mapLocationImagesReference, key)
                    .addOnFailureListener(e -> Log.w(TAG,
                            "Error Uploading image Url: {" + key + ", " +
                            imageUrlMap.get(key) + "}, e:" + e.toString()))
                    .addOnSuccessListener(taskSnapshot -> Log.d(TAG,
                            "Successfully upload image Url: {" + key + ", " +
                                    imageUrlMap.get(key) + "}"));
        }

        Log.i(TAG, "adding map location...");
        // Now store the actual MapLocation
        mapLocationReference.set(mapLocation)
                    .addOnFailureListener(e -> Log.w(TAG,
                            "Error Uploading Location:" + mapLocation.toString() +
                                    "e:" + e.toString()));
    }


    /**
     * Get Map location based on ID, the LiveData returned from here won't be updated and need to be
     * requested again if want to refresh (this is different from UserManager
     * @param mapLocationId Id of MapLocation
     * @return MapLocation found by id.
     */
    public LiveData<MapLocation> getMapLocationById(String mapLocationId) {
        MutableLiveData<MapLocation> mapLocationMutableLiveData = new MutableLiveData<>();
        mMapLocationCollection.document(mapLocationId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                // TODO something need to be done?
            }
        });
        return mapLocationMutableLiveData;
    }
}
