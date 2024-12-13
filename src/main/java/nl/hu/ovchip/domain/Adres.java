package nl.hu.ovchip.domain;

import jakarta.persistence.*;

@Entity
@Table(name= "adres")
public class Adres {
    @Id
    @Column(name = "adres_id")
    private int adres_id;
    @Column(name = "postcode")
    private String postcode;
    @Column(name = "huisnummer")
    private String huisnummer;
    @Column(name="straat")
    private String straat;
    @Column(name = "woonplaats")
    private String woonplaats;
    @OneToOne
    @JoinColumn(name = "reiziger_id", nullable = false)
    private Reiziger reiziger;

    public Adres(int adres_id, String postcode, String huisnummer, String straat, String woonplaats,Reiziger reiziger) {
        this.adres_id = adres_id;
        if (postcode.length()<=10){
        this.postcode = postcode;}
        else {
            throw new IllegalArgumentException("postcode is te lang");
        }

        if (huisnummer.length()<=10){
        this.huisnummer = huisnummer;}
        else {
            throw new IllegalArgumentException("huisnummer is te lang");
        }
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger=reiziger;
    }

    protected Adres() {

    }

    public int getAdres_id() {
        return adres_id;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }


    public Reiziger getReiziger() {
        return reiziger;
    }

    @Override
    public String toString() {
        return "Een adres met " +
                "adres_id=" + adres_id +
                ", postcode='" + postcode + '\'' +
                ", huisnummer='" + huisnummer + '\'' +
                ", straat='" + straat + '\'' +
                ", woonplaats='" + woonplaats + '\'' +
                ", reiziger_id=" + reiziger.getId() +
                '}';
    }
}
