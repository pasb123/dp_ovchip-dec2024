package nl.hu.ovchip.domain;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ov_chipkaart")
public class OVChipkaart {
    @Id
    @Column(name = "kaart_nummer", length = 10)
    private int kaartNummer;
    @Column(name = "geldig_tot")
    private Date geldigTot;
    @Column(name = "klasse", length = 10)
    private int klasse;
    @Column(name = "saldo",length = 18, precision = 2)
    private double saldo;
    @ManyToOne
    @JoinColumn(name = "reiziger_id",nullable = false)
    private Reiziger reiziger;
    @Transient
    private List<Product> producten= new ArrayList<>();

    protected OVChipkaart() {
    }

    public OVChipkaart(int kaartNummer, Date geldigTot, int klasse, double saldo, Reiziger reiziger) {
        this.kaartNummer = kaartNummer;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }
    public void addProduct(Product product){
        this.producten.add(product);
    }
    public void removeProduct(Product product){
        this.producten.remove(product);
    }

    public List<Product> getProducten() {
        return producten;
    }

    @Override
    public String toString() {
        return "Een OVChipkaart met " +
                "kaartNummer=" + kaartNummer +
                ", geldigTot=" + geldigTot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reiziger=" + reiziger +
                ", producten=" + producten;
    }
}
