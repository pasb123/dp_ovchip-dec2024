import nl.hu.ovchip.data.ReizigerDAO;
import nl.hu.ovchip.domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDOAPsql implements ReizigerDAO {
    private Connection conn;
    private  Connection getConnection() {
        String linkJB = "jdbc:postgresql://localhost:5432/ovchip2";
        String username = "postgres";
        String password = "sand66";

        try {
            conn = DriverManager.getConnection(linkJB, username, password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return conn;
    }

    public ReizigerDOAPsql(Connection conn){
        this.conn=conn;
        getConnection();
    }
    @Override
    public boolean save(Reiziger reiziger) throws SQLException {

        PreparedStatement statement = this.getConnection().prepareStatement("" +
                "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel,achternaam,geboortedatum) VALUES (?, ?, ?,?,?)");
        statement.setInt(1, reiziger.getId());
        statement.setString(2, reiziger.getVoorletters());
        statement.setString(3, reiziger.getTussenvoegsel());
        statement.setString(4, reiziger.getAchternaam());
        statement.setDate(5,reiziger.getGeboortedatum());
        statement.execute();
        statement.close();
        return true;
    }

    @Override
    public boolean update(Reiziger oudeReiziger, Reiziger nieuweReiziger) throws SQLException {
        PreparedStatement statement = this.getConnection().prepareStatement("" + "update reiziger set voorletters=?, tussenvoegsel=?, achternaam=?,geboortedatum=? Where reiziger_id=?");
        statement.setString(1, nieuweReiziger.getVoorletters());
        statement.setString(2, nieuweReiziger.getTussenvoegsel());
        statement.setString(3, nieuweReiziger.getAchternaam());
        statement.setDate(4, nieuweReiziger.getGeboortedatum());
        statement.setInt(5, oudeReiziger.getId());
        statement.executeUpdate();
        statement.close();

        return false;
    }




    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        PreparedStatement statement = this.getConnection().prepareStatement("" +"DELETE FROM reiziger where reiziger_id=?");
       statement.setInt(1, reiziger.getId());
       statement.execute();
       statement.close();
        return false;
    }
    @Override
    public List<Reiziger> findAll() throws SQLException {
        ResultSet resultSet =getConnection().createStatement().executeQuery("SELECT * FROM reiziger");
        List<Reiziger>Reizigerlist = new ArrayList();
        while (resultSet.next()){
            Reiziger reiziger= new  Reiziger(resultSet.getInt("reiziger_id"),resultSet.getString("voorletters"),resultSet.getString("tussenvoegsel"),
                    resultSet.getString("achternaam"),resultSet.getDate("geboortedatum"));

            Reizigerlist.add(reiziger);
        }
        return Reizigerlist;
    }}
