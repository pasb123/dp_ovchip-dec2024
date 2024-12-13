package nl.hu.ovchip.data.implementation.reiziger;

import nl.hu.ovchip.data.DAO.ReizigerDAO;
import nl.hu.ovchip.data.connection.PostgresConnect;
import nl.hu.ovchip.domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDOAPsql implements ReizigerDAO {
    private Connection conn;


    public ReizigerDOAPsql(Connection conn){
        this.conn=conn;
    }
    @Override
    public boolean save(Reiziger reiziger) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("" +
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
        PreparedStatement statement = this.conn.prepareStatement("" + "update reiziger set voorletters=?, tussenvoegsel=?, achternaam=?,geboortedatum=? Where reiziger_id=?");
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
        PreparedStatement statement = this.conn.prepareStatement("" +"DELETE FROM reiziger where reiziger_id=?");
       statement.setInt(1, reiziger.getId());
       statement.execute();
       statement.close();
        return false;
    }
    @Override
    public List<Reiziger> findAll() throws SQLException {
        ResultSet resultSet =conn.createStatement().executeQuery("SELECT * FROM reiziger");
        List<Reiziger>Reizigerlist = new ArrayList();
        while (resultSet.next()){
            Reiziger reiziger= new  Reiziger(resultSet.getInt("reiziger_id"),
                    resultSet.getString("voorletters"),
                    resultSet.getString("tussenvoegsel"),
                    resultSet.getString("achternaam"),
                    resultSet.getDate("geboortedatum"));

            Reizigerlist.add(reiziger);
        }
        return Reizigerlist;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        PreparedStatement preparedStatement =conn.prepareStatement(""+"SELECT * FROM reiziger where reiziger_id=?");
        preparedStatement.setInt(1,id);
        ResultSet resultSet= preparedStatement.executeQuery();
        Reiziger reiziger= null;
        while (resultSet.next()){
        reiziger=new  Reiziger(resultSet.getInt("reiziger_id"),
                resultSet.getString("voorletters"),
                resultSet.getString("tussenvoegsel"),
                resultSet.getString("achternaam"),
                resultSet.getDate("geboortedatum"));
        }
        return reiziger;
    }
}
