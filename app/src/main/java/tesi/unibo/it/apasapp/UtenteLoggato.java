package tesi.unibo.it.apasapp;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by Volpi on 15/02/2016.
 */
public class UtenteLoggato implements Serializable {

    private String privilegi;
    private String nome, cognome, stato, regione, provincia, castello, telefono, indirizzo, email, password, username;
    private Date dataDiNascita;

    public UtenteLoggato(String nome, String cognome, Date dataDiNascita, String email, String stato, String regione, String provincia, String castello, String username, String password, String privilegi) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.email = email;
        this.stato = stato;
        this.regione = regione;
        this.provincia = provincia;
        this.castello = castello;
        this.username = username;
        this.password = password;
    }

    public String getNome() { return nome; }
    public void setNome() { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome() { this.nome = cognome; }

    public Date getDataDiNascita() { return dataDiNascita; }
    public void setDataDiNascita() { this.dataDiNascita = dataDiNascita; }

    public String getEmail() { return email; }
    public void setEmail() { this.email = email; }

    public String getStato() { return stato; }
    public void setStato() { this.stato = stato; }

    public String getRegione() { return regione; }
    public void setRegione() { this.regione = regione; }

    public String getProvincia() { return provincia; }
    public void setProvincia() { this.provincia = provincia; }

    public String getCastello() { return castello; }
    public void setCastello() { this.castello = castello; }

    public String getUsername() { return username; }
    public void setUsername() { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword() { this.password = password; }


    public String getPrivilegi() {
        return getPrivilegi();
    }
}
