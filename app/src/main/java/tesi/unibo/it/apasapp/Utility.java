package tesi.unibo.it.apasapp;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;
import java.util.HashMap;

/**
 * Created by Volpi on 15/02/2016.
 */
public class Utility {



    public static boolean isInternetAvailable() {
            try {
                InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
                if (ipAddr.equals("")) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
    }


    public static String[] getStati(Resources resource) {
        return resource.getStringArray(R.array.stati_array);
    }
    public static String[] getRegioni(Resources resource) {
        return resource.getStringArray(R.array.regioni_array);
    }
    
    public static String[] getCastelli(Resources resource) {
        return resource.getStringArray(R.array.castelli_array);
    }

    public static String[] getTipo(Resources resource) {
        return resource.getStringArray(R.array.tipo);
    }

    public static String[] getRazzeCani(Resources resource) {
        return resource.getStringArray(R.array.razze_cani);
    }

    public static String[] getRazzeGatti(Resources resource) {
        return resource.getStringArray(R.array.razze_gatti);
    }
    
    public static HashMap<String, String[]> getRegioniProvince(Resources resource) {
        HashMap<String, String[]> mapRegioneToProvincia = new HashMap<>();
        mapRegioneToProvincia.put("Abruzzo", resource.getStringArray(R.array.privince_abruzzo_array));
        mapRegioneToProvincia.put("Basilicata", resource.getStringArray(R.array.province_basilicata_array));
        mapRegioneToProvincia.put("Calabria", resource.getStringArray(R.array.province_calabria_array));
        mapRegioneToProvincia.put("Campania", resource.getStringArray(R.array.province_campania_array));
        mapRegioneToProvincia.put("Emilia Romagna", resource.getStringArray(R.array.province_emiliaromagna_array));
        mapRegioneToProvincia.put("Friuli Venezia Giulia", resource.getStringArray(R.array.province_friuliveneziagiulia));
        mapRegioneToProvincia.put("Lazio", resource.getStringArray(R.array.province_lazio));
        mapRegioneToProvincia.put("Liguria", resource.getStringArray(R.array.province_liguria));
        mapRegioneToProvincia.put("Lombardia", resource.getStringArray(R.array.province_lombardia));
        mapRegioneToProvincia.put("Marche", resource.getStringArray(R.array.province_marche));
        mapRegioneToProvincia.put("Piemonte", resource.getStringArray(R.array.province_piemonte));
        mapRegioneToProvincia.put("Sardegna", resource.getStringArray(R.array.province_sardegna));
        mapRegioneToProvincia.put("Sicilia", resource.getStringArray(R.array.province_sicilia));
        mapRegioneToProvincia.put("Toscana", resource.getStringArray(R.array.province_toscana));
        mapRegioneToProvincia.put("Trentino Alto Adige", resource.getStringArray(R.array.province_trentinoaltoadige));
        mapRegioneToProvincia.put("Umbria", resource.getStringArray(R.array.province_umbria));
        mapRegioneToProvincia.put("Valle d'Aosta", resource.getStringArray(R.array.province_valledaosta));
        return mapRegioneToProvincia;
    }
    
    

}
