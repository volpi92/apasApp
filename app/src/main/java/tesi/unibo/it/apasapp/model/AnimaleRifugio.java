package tesi.unibo.it.apasapp.model;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by Volpi on 10/03/2016.
 */
public class AnimaleRifugio {

    int idAnimaleRifugio;
    String nome, sesso, tipo, razza, descrizione, linkFoto, storia;
    Bitmap foto;
    Date dataEntrata;
    Date dataMorte;
    Date dataAdozione;
    int idPersona;

    public AnimaleRifugio (int idAnimaleRifugio, String nome, String linkFoto) {
        this.idAnimaleRifugio = idAnimaleRifugio;
        this.nome = nome;
        this.linkFoto = linkFoto;
    }

    public AnimaleRifugio (int idAnimaleRifugio, String nome, String sesso, String tipo, String razza, String descrizione, String linkFoto, String storia, Date dataEntrata, Date dataMorte, Date dataAdozione, int idPersona) {
        this.idAnimaleRifugio = idAnimaleRifugio;
        this.nome = nome;
        this.sesso = sesso;
        this.tipo = tipo;
        this.razza = razza;
        this.descrizione = descrizione;
        this.linkFoto = linkFoto;
        this.storia = storia;
        this.dataEntrata = dataEntrata;
        this.dataMorte = dataMorte;
        this.dataAdozione = dataAdozione;
        this.idPersona = idPersona;
    }

    public int getIdAnimaleRifugio() {
        return this.idAnimaleRifugio;
    }

    public String getNome() {
        return this.nome;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getRazza() {
        return this.razza;
    }

    public String getSesso() {
        return this.sesso;
    }

    public String getDescrizione() {
        return this.descrizione;
    }


    public String getLinkFoto() {
        return this.linkFoto;
    }

    public String getStoria() {
        return this.storia;
    }

    public Date getDataEntrata() {
        return this.dataEntrata;
    }

    public Date getDataMorte() {
        return this.dataMorte;
    }

    public Date getDataAdozione() {
        return this.getDataAdozione();
    }

    public int getIdPersona() {
        return this.idPersona;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public Bitmap getFoto() {
        return this.foto;
    }

}
