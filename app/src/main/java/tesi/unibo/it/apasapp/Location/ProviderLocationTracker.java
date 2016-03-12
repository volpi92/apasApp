package tesi.unibo.it.apasapp.Location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Volpi on 21/02/2016.
 */
public class ProviderLocationTracker implements LocationListener, LocationTracker {

    // The minimum distance to change Updates in meters
    private static final long MIN_UPDATE_DISTANCE = 1;

    // The minimum time between updates in milliseconds
    private static final long MIN_UPDATE_TIME = 1000;

    private LocationManager lm;

    public enum ProviderType {
        NETWORK,
        GPS
    }

    ;

    private String provider;

    private Location lastLocation;
    private long lastTime;
    private Context context;
    private boolean isRunning;

    private LocationUpdateListener listener;


    public ProviderLocationTracker(Context context, ProviderType type, LocationUpdateListener listener) {
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (type == ProviderType.NETWORK) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            provider = LocationManager.GPS_PROVIDER;
        }
        this.listener = listener;
        this.context = context;
        this.isRunning = false;
    }


    @Override
    public void onLocationChanged(Location newLoc) {
        long now = System.currentTimeMillis();
        if(listener != null){
            listener.onUpdate(newLoc); //INTERRUPT o EVENT
        }
        lastLocation = newLoc;
        lastTime = now;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }

    @Override
    public void start() {
        if (isRunning) {
            //Already running, do nothing
            return;
        }
        isRunning = true;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.requestLocationUpdates(provider, MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE, this);
        lastLocation = null;
        lastTime = 0;
        return;
    }

    @Override
    public void stop() {
        if (isRunning) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            listener = null;
            lm.removeUpdates(this);
            isRunning = false;
            Toast.makeText(context, listener==null?"StopMethod: listener null":"listener NON NULL", Toast.LENGTH_SHORT).show();
        }
    }


    /*Polling
    @Override
    public Location getLocation() { //POLLING
        if(lastLocation == null){
            return null;
        }
        if(System.currentTimeMillis() - lastTime > 5 * MIN_UPDATE_TIME){
            return null;
        }
        Log.d("LOCATION: ", "Latitude: " + lastLocation.getLatitude() + ", Longitude: " + lastLocation.getLongitude());
        return lastLocation;
    }
    */

    public LocationUpdateListener getListener() {
        return  listener;
    }

    public boolean getRunning() {
        return isRunning;
    }


}
