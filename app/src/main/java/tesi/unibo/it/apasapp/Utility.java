package tesi.unibo.it.apasapp;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Volpi on 15/02/2016.
 */
public class Utility {

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
    
    public static Date convertStringToDateFromWeb(String stringDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }



}
