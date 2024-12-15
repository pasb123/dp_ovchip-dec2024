package nl.hu.ovchip.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_nummer")
    private int productNummer;
    @Column(name = "naam")
    private String naam;
    @Column(name = "beschrijving")
    private String beschrijving;
    @Column(name = "prijs")
    private double prijs;
    @ElementCollection
    @CollectionTable(
            name = "ov_chipkaart_product",
            joinColumns = @JoinColumn(name = "product_nummer")
    )
    @Column(name = "kaart_nummer")
    private List<Integer> ovChipkaartNummers= new ArrayList<>();

    public Product(int productNummer, String naam, String beschrijving, double prijs) {
        if (String.valueOf(productNummer).length()<=10){
        this.productNummer = productNummer;}
        else{
            throw new IllegalArgumentException("the productnumber is too long");}
        if (naam.length()<=30){
        this.naam = naam;}
        else {
            throw new IllegalArgumentException("the name is too long");
        }
        this.beschrijving = beschrijving;
        if (String.valueOf(prijs).length()<=19){
        this.prijs = prijs;}
        else{
            throw new IllegalArgumentException("the price format is invalid, check the decimals or the price length");
        }

    }

    protected Product() {

    }

    public int getProductNummer() {
        return productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public List<Integer> getOvChipkaarten() {
        return ovChipkaartNummers;
    }

    public void removeOVChipkaart(Integer ovChipkaartNummer){
        this.ovChipkaartNummers.remove(ovChipkaartNummer);
    }
    public void addOVChipkaart(Integer ovChipkaartNummer){
        this.ovChipkaartNummers.add(ovChipkaartNummer);
    }

    @Override
    public String toString() {
        return "Product " +
                "productNummer= " + productNummer +
                ", naam= '" + naam + '\'' +
                ", beschrijving= '" + beschrijving + '\'' +
                ", prijs=" + prijs +
                ", ovChipkaarten= " + ovChipkaartNummers;
    }
}
