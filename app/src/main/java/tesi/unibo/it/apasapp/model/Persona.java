package tesi.unibo.it.apasapp.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tesi.unibo.it.apasapp.Utility;

/**
 * Created by Volpi on 10/03/2016.
 */
public class Persona implements Serializable {

    int idPersona;
    String nome, cognome, sesso, email, telefono, stato, regione, provincia, castello, indirizzo, username, password, privilegio, dataNascita;

    public Persona(int idPersona, String nome, String cognome, String sesso, String email, String telefono, String stato, String regione, String provincia, String castello, String indirizzo, String username, String password, String privilegio, String dataNascita) {
        this.idPersona = idPersona;
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.email = email;
        this.telefono = telefono;
        this.stato = stato;
        this.regione = regione;
        this.provincia = provincia;
        this.castello = castello;
        this.indirizzo = indirizzo;
        this.username = username;
        this.password = password;
        this.privilegio = privilegio;
        this.dataNascita = dataNascita;
    }

    public Persona(int idPersona, String nome, String cognome, String sesso, String email, String telefono, String stato, String regione, String provincia, String castello, String indirizzo, String privilegio, String dataNascita) {
        this.idPersona = idPersona;
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.email = email;
        this.telefono = telefono;
        this.stato = stato;
        this.regione = regione;
        this.provincia = provincia;
        this.castello = castello;
        this.indirizzo = indirizzo;
        this.username = username;
        this.password = password;
        this.privilegio = privilegio;
        this.dataNascita = dataNascita;
    }


    public int getIdPersona() {
        return this.idPersona;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getSesso() {
        return this.sesso;
    }


    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getEmail() {
        return this.email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getStato() {
        return this.stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getRegione() {
        return this.regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCastello() {
        return this.castello;
    }

    public void setCastello(String castello) {
        this.castello = castello;
    }

    public String getIndirizzo() {
        return this.indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getPrivilegio() {
        return this.privilegio;
    }

    public void setPrivilegio(String privilegio) {
        this.privilegio = privilegio;
    }

    public String getUserName() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getDataNascita() {
        return this.dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public static Persona createPersonaFromJson(JSONObject jsonObject) {
        Persona p = null;
        try {
            String nome = jsonObject.getString("Nome");
            int idPersona = jsonObject.getInt("IdPersona");
            String cognome = jsonObject.getString("Cognome");
            String sesso = jsonObject.getString("Sesso");
            String email = jsonObject.getString("Email");
            String telefono = jsonObject.getString("Telefono");
            String stato = jsonObject.getString("Stato");
            String regione = jsonObject.getString("Regione");
            String provincia = jsonObject.getString("Provincia");
            String castello = jsonObject.getString("Castello");
            String indirizzo = jsonObject.getString("Indirizzo");
            String privilegio = jsonObject.getString("Privilegio");
            String userName = jsonObject.getString("UserName");
            String password = jsonObject.getString("Password");
            String dataNascita = jsonObject.getString("DataNascita");
            /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = Utility.convertStringToDateFromWeb(dataNascita);*/

            if(stato.equals("Italia")) {
                p = new Persona(idPersona, nome, cognome, sesso, email, telefono, stato, regione, provincia, null, indirizzo, userName, password, privilegio, dataNascita);
            } else {
                p = new Persona(idPersona, nome, cognome, sesso, email, telefono, stato, null, null, castello, indirizzo, userName, password, privilegio, dataNascita);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return p;
    }



}
