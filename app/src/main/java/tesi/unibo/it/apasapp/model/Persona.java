package tesi.unibo.it.apasapp.model;

import java.util.Date;

/**
 * Created by Volpi on 10/03/2016.
 */
public class Persona {

    int idPersona;
    String nome, cognome, sesso, email, telefono, stato, regione, provincia, castello, indirizzo, username, password, privilegio;
    Date dataNascita;

    public Persona(int idPersona, String nome, String cognome, String sesso, String email, String telefono, String stato, String regione, String provincia, String castello, String indirizzo, String username, String password, String privilegio, Date dataNascita) {
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

    public String getCognome() {
        return this.cognome;
    }

    public String getSesso() {
        return this.sesso;
    }

    public String getEmail() {
        return this.email;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public String getStato() {
        return this.stato;
    }

    public String getRegione() {
        return this.regione;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public String getCastello() {
        return this.castello;
    }

    public String getIndirizzo() {
        return this.indirizzo;
    }

    public String getUserName() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Date getDataNascita() {
        return this.dataNascita;
    }

}
