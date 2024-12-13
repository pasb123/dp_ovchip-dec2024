import nl.hu.ovchip.data.ReizigerDAO;
import nl.hu.ovchip.domain.Reiziger;

import java.sql.*;
import java.util.List;

public class Main {
//connection link dummy
        public static Connection connection;

        // conection method
        private static Connection getConnection() {
            String linkJB = "jdbc:postgresql://localhost:5432/ovchip2";
            String username = "postgres";
            String password = "sand66";

            try {
                connection = DriverManager.getConnection(linkJB, username, password);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            return connection;
        };
        //close connection
    public static void closeConnection() throws SQLException {
        if(connection != null){connection.close();
        }


    }
    //test connection
    private static void testConnection() throws SQLException {
        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery("SELECT * FROM reiziger");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("reiziger_id") + " "
                        + resultSet.getString("voorletters") + " "
                        + resultSet.getString("achternaam") + " "
                        + resultSet.getString("geboortedatum"));

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        closeConnection();
    }
    /**
     * P2. nl.hu.ovchip.domain.Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de nl.hu.ovchip.domain.Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test nl.hu.ovchip.data.ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] nl.hu.ovchip.data.ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na nl.hu.ovchip.data.ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        //update
        Reiziger updateSietske=new Reiziger(77, "S O", "", "Boersen", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na nl.hu.ovchip.data.ReizigerDAO.update() ");
        rdao.update(sietske, updateSietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        //delete
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na nl.hu.ovchip.data.ReizigerDAO.delete() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
    }
    public static void main(String[] args) throws SQLException {
        testConnection();
        ReizigerDOAPsql reizigerDOAPsql=new ReizigerDOAPsql(connection);
        testReizigerDAO(reizigerDOAPsql);
    }
}