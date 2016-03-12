package tesi.unibo.it.apasapp.Location;

import android.location.Location;

/**
 * Created by Volpi on 21/02/2016.
 */
public interface LocationTracker {
    public interface LocationUpdateListener{
        public void onUpdate(Location newLoc);
    }

    public void start();

    public void stop();

    //public Location getLocation();

}