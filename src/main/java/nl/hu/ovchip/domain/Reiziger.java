package nl.hu.ovchip.domain;
import java.sql.Date;

public class Reiziger  {
    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;


    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.reiziger_id=reiziger_id;
        this.voorletters=voorletters;
        this.tussenvoegsel=tussenvoegsel;
        this.achternaam=achternaam;
        this.geboortedatum=geboortedatum;
    }

    public int getId() {
        return reiziger_id;
    }

    public void setId(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    @Override
    public String toString() {
        return "nl.hu.ovchip.domain.Reiziger met reiziger_id=" + getId() +
                ", voorletters='" + getVoorletters() + '\'' +
                ", tussenvoegsel='" + getTussenvoegsel() + '\'' +
                ", achternaam='" + getAchternaam() + '\'' +
                ", geboortedatum=" + getGeboortedatum() +
                '}';
    }
}
