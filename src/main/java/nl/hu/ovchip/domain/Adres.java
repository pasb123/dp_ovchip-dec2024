package nl.hu.ovchip.domain;

public class Adres {
    private int adres_id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reiziger_id;

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
        this.reiziger_id=reiziger.getId();
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

    public int getReiziger_id() {
        return reiziger_id;
    }

    @Override
    public String toString() {
        return "Een adres met " +
                "adres_id=" + adres_id +
                ", postcode='" + postcode + '\'' +
                ", huisnummer='" + huisnummer + '\'' +
                ", straat='" + straat + '\'' +
                ", woonplaats='" + woonplaats + '\'' +
                ", reiziger_id=" + reiziger_id +
                '}';
    }
}
