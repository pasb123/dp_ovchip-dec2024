package nl.hu.ovchip.domain;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "reiziger")
public class Reiziger  {
    @Id
    @Column(name = "reiziger_id")
    private int reiziger_id;
    @Column(name = "voorletters")
    private String voorletters;
    @Column(name= "tussenvoegsel")
    private String tussenvoegsel;
    @Column(name = "achternaam")
    private String achternaam;
    @Column(name = "geboortedatum")
    private Date geboortedatum;
    @OneToOne(mappedBy = "reiziger", cascade = CascadeType.ALL, orphanRemoval = true)
    private Adres adres;
    @Transient
    private List<OVChipkaart> ovChipkaart;
    protected Reiziger(){}

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
        return "Reiziger met reiziger_id=" + getId() +
                ", voorletters='" + getVoorletters() + '\'' +
                ", tussenvoegsel='" + getTussenvoegsel() + '\'' +
                ", achternaam='" + getAchternaam() + '\'' +
                ", geboortedatum=" + getGeboortedatum() +
                '}';
    }
}
