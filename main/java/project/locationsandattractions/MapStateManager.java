package project.locationsandattractions;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Camera;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Christian on 2017-01-18.
 */

public class MapStateManager {
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";

    private static final String PREFS_NAME = "mapCameraState";

    private SharedPreferences mapStatePrefs;

    public MapStateManager(Context context) {
        mapStatePrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

    }

    public void saveMapState(GoogleMap map) {
        SharedPreferences.Editor editor = mapStatePrefs.edit();
        CameraPosition position = map.getCameraPosition();

        editor.putFloat(LATITUDE, (float) position.target.latitude);
        editor.putFloat(LONGITUDE, (float) position.target.longitude);
        editor.commit();
    }

    public CameraPosition getSavedCameraPosition() {
        double latitude = mapStatePrefs.getFloat(LATITUDE, 0);
        if(latitude == 0) {
            return null;
        }

        double longitude = mapStatePrefs.getFloat(LONGITUDE, 0);
        LatLng target = new LatLng(latitude, longitude);
        CameraPosition position = new CameraPosition(target, 0, 0 ,0);
        return position;
    }
}
