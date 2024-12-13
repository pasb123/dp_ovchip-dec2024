import nl.hu.ovchip.data.connection.HibernateConnect;
import nl.hu.ovchip.data.connection.PostgresConnect;
import nl.hu.ovchip.data.implementation.ReizigerDAOHibernate;
import nl.hu.ovchip.data.implementation.ReizigerDOAPsql;
import nl.hu.ovchip.data.DAO.ReizigerDAO;
import nl.hu.ovchip.domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.List;


public class Main {
//connection link dummy
    private static Connection connection= PostgresConnect.connection;



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
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na nl.hu.ovchip.data.DAO.ReizigerDAO.save() ");
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
    public static void main(String[] args) throws SQLException {
       PostgresConnect.testConnection();
        ReizigerDOAPsql reizigerDOAPsql=new ReizigerDOAPsql(connection);
        ReizigerDAOHibernate reizigerDAOHibernate= new ReizigerDAOHibernate(HibernateConnect.getSession());
        testReizigerDAO(reizigerDOAPsql);
        testReizigerDAO(reizigerDAOHibernate);
    }
}