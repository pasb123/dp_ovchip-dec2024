import nl.hu.ovchip.data.DAO.AdresDAO;
import nl.hu.ovchip.data.DAO.OVChipkaartDAO;
import nl.hu.ovchip.data.connection.HibernateConnect;
import nl.hu.ovchip.data.connection.PostgresConnect;
import nl.hu.ovchip.data.implementation.adres.AdresDAOHibernate;
import nl.hu.ovchip.data.implementation.adres.AdresDAOPsql;
import nl.hu.ovchip.data.implementation.ovchipkaart.OVChipkaartDAOHibernate;
import nl.hu.ovchip.data.implementation.ovchipkaart.OVChipkaartDAOPsql;
import nl.hu.ovchip.data.implementation.reiziger.ReizigerDAOHibernate;
import nl.hu.ovchip.data.implementation.reiziger.ReizigerDOAPsql;
import nl.hu.ovchip.data.DAO.ReizigerDAO;
import nl.hu.ovchip.domain.Adres;
import nl.hu.ovchip.domain.OVChipkaart;
import nl.hu.ovchip.domain.Reiziger;

import java.sql.*;
import java.util.List;


public class Main {
//connection link dummy
    private static Connection connection= PostgresConnect.getConnection();



    /**
     * P2. nl.hu.ovchip.domain.Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de nl.hu.ovchip.domain.Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test nl.hu.ovchip.data.DAO.ReizigerDAO -------------");
        System.out.println("test "+ rdao.getClass());
        //Haal 1 reiziger uit de DB
        Reiziger reiziger=rdao.findById(1);
        System.out.println("[Test] ReizigersDAO.findById geeft "+ reiziger );
        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] nl.hu.ovchip.data.DAO.ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum));
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        //update
        Reiziger updateSietske=new Reiziger(77, "S O", "", "Boersen", Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na nl.hu.ovchip.data.DAO.ReizigerDAO.update() ");
        rdao.update(sietske, updateSietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        //delete
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na nl.hu.ovchip.data.DAO.ReizigerDAO.delete() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
    }
    private static void testAdresDAO(AdresDAO adao,ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test nl.hu.ovchip.data.DAO.AdresDAO -------------");
        System.out.println("test " + adao.getClass());

        // Haal alle adressen op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] nl.hu.ovchip.data.DAO.AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Maak een nieuw adres aan en persisteer dit in de database
        String gbdatum = "1981-03-14";
        List<Reiziger> reizigers = rdao.findAll();
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na nl.hu.ovchip.data.DAO.ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        Adres nieuwAdres = new Adres(77, "1234AB", "123", "Langelaan", "Nieuwe Stad", rdao.findById(77));
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na nl.hu.ovchip.data.DAO.AdresDAO.save() ");
        adao.save(nieuwAdres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // update
        Adres updateAdres = new Adres(77, "6789WE", "456", "Gewijzigde Straat", "Gewijzigde Stad", rdao.findById(77));
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na nl.hu.ovchip.data.DAO.AdresDAO.update() ");
        adao.update(nieuwAdres, updateAdres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        //find by reiziger
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na nl.hu.ovchip.data.DAO.AdresDAO.findByReiziger() ");
        Adres adres3= adao.findByReiziger(sietske);
        System.out.println("het adres van " + sietske+" is "+ adres3);

        // delete
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na nl.hu.ovchip.data.DAO.AdresDAO.delete() ");
        adao.delete(adres3);
        rdao.delete(sietske);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");
        for (Adres a : adressen) {
            System.out.println(a);
        }
    }
    private static void testOVChipkaartDAO(OVChipkaartDAO odao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test nl.hu.ovchip.data.DAO.OVChipkaartDAO -------------");
        System.out.println("test " + odao.getClass());

        // Haal alle OVChipkaarten op uit de database
        List<OVChipkaart> ovChipkaarten = odao.findAll();
        System.out.println("[Test] nl.hu.ovchip.data.DAO.OVChipkaartDAO.findAll() geeft de volgende OVChipkaarten:");
        for (OVChipkaart o : ovChipkaarten) {
            System.out.println(o);
        }
        System.out.println();

        // Maak een nieuwe OVChipkaart aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        List<Reiziger> reizigers = rdao.findAll();
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na nl.hu.ovchip.data.DAO.ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        OVChipkaart oudeKaart = new OVChipkaart(12345, Date.valueOf("2025-12-31"), 2, 50.0, rdao.findById(77));
        System.out.print("[Test] Eerst " + ovChipkaarten.size() + " OVChipkaarten, na nl.hu.ovchip.data.DAO.OVChipkaartDAO.save() ");
        odao.save(oudeKaart);
        ovChipkaarten = odao.findAll();
        System.out.println(ovChipkaarten.size() + " OVChipkaarten\n");
        for (OVChipkaart o : ovChipkaarten) {
            System.out.println(o);
        }
        System.out.println();

        // update
        OVChipkaart updateKaart = new OVChipkaart(12345, Date.valueOf("2026-12-31"), 1, 100.0, rdao.findById(77));
        System.out.print("[Test] Eerst " + ovChipkaarten.size() + " OVChipkaarten, na nl.hu.ovchip.data.DAO.OVChipkaartDAO.update() ");
        odao.update(oudeKaart, updateKaart);
        ovChipkaarten = odao.findAll();
        System.out.println(ovChipkaarten.size() + " OVChipkaarten\n");
        for (OVChipkaart o : ovChipkaarten) {
            System.out.println(o);
        }

        // find by reiziger
        System.out.print("[Test] Eerst " + ovChipkaarten.size() + " OVChipkaarten, na nl.hu.ovchip.data.DAO.OVChipkaartDAO.findByReiziger() ");
        List<OVChipkaart> kaartenVanSietske = odao.findByReiziger(sietske);
        System.out.println("De OVChipkaarten van " + sietske + " zijn:");
        for (OVChipkaart kaart : kaartenVanSietske) {
            System.out.println(kaart);
        }

        // delete
        System.out.print("[Test] Eerst " + ovChipkaarten.size() + " OVChipkaarten, na nl.hu.ovchip.data.DAO.OVChipkaartDAO.delete() ");
        odao.delete(kaartenVanSietske.get(0));
        rdao.delete(sietske);
        ovChipkaarten = odao.findAll();
        System.out.println(ovChipkaarten.size() + " OVChipkaarten\n");
        for (OVChipkaart o : ovChipkaarten) {
            System.out.println(o);
        }
    }

    public static void main(String[] args) throws SQLException {
       PostgresConnect.testConnection();
//
//

        ReizigerDOAPsql reizigerDOAPsql=new ReizigerDOAPsql(connection);
       ReizigerDAOHibernate reizigerDAOHibernate= new ReizigerDAOHibernate(HibernateConnect.getSession());
       testReizigerDAO(reizigerDOAPsql);
        testReizigerDAO(reizigerDAOHibernate);

        testAdresDAO(new AdresDAOHibernate(HibernateConnect.getSession()),reizigerDAOHibernate);
        testAdresDAO(new AdresDAOPsql(connection),new ReizigerDOAPsql(connection));
        testOVChipkaartDAO(new OVChipkaartDAOPsql(connection),new ReizigerDOAPsql(connection));
        testOVChipkaartDAO(new OVChipkaartDAOHibernate(HibernateConnect.getSession()),reizigerDAOHibernate);
    }
}