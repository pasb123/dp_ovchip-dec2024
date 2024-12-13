package nl.hu.ovchip.data.implementation.adres;

import nl.hu.ovchip.data.DAO.AdresDAO;
import nl.hu.ovchip.data.implementation.reiziger.ReizigerDOAPsql;
import nl.hu.ovchip.domain.Adres;
import nl.hu.ovchip.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection conn ;

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("" +
                "INSERT INTO adres (adres_id, postcode, huisnummer,straat,woonplaats,reiziger_id) VALUES (?, ?, ?,?,?,?)");
        statement.setInt(1, adres.getAdres_id());
        statement.setString(2, adres.getPostcode());
        statement.setString(3, adres.getHuisnummer());
        statement.setString(4, adres.getStraat());
        statement.setString(5,adres.getWoonplaats());
        statement.setInt(6,adres.getReiziger_id());
        statement.execute();
        statement.close();
        return true;
    }

    @Override
    public boolean update(Adres oudAdres, Adres nieuwAdres) throws SQLException {
        PreparedStatement statement = this.conn.prepareStatement(
                "update adres set straat = ?, huisnummer = ?, postcode = ?, woonplaats = ?, reiziger_id = ? where adres_id = ?"
        );
        statement.setString(1, nieuwAdres.getStraat());
        statement.setString(2, nieuwAdres.getHuisnummer());
        statement.setString(3, nieuwAdres.getPostcode());
        statement.setString(4, nieuwAdres.getWoonplaats());
        statement.setInt(5, nieuwAdres.getReiziger_id());
        statement.setInt(6, oudAdres.getAdres_id());
        statement.executeUpdate();
        statement.close();

 return  true;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        PreparedStatement statement = this.conn.prepareStatement("" +"DELETE FROM adres where adres_id=?");
        statement.setInt(1, adres.getAdres_id());
        statement.execute();
        statement.close();
        return true;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM adres");
        ReizigerDOAPsql reizigerDOAPsql= new ReizigerDOAPsql(conn);
        List<Adres> adresList = new ArrayList<>();
        while (resultSet.next()) {
            Adres adres = new Adres(
                    resultSet.getInt("adres_id"),
                    resultSet.getString("postcode"),
                    resultSet.getString("huisnummer"),
                    resultSet.getString("straat"),
                    resultSet.getString("woonplaats"),
                    reizigerDOAPsql.findById(resultSet.getInt("reiziger_id"))

            );
            adresList.add(adres);
        }
        return adresList;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        PreparedStatement preparedStatement =conn.prepareStatement(""+"SELECT * FROM adres where reiziger_id=?");
        preparedStatement.setInt(1,reiziger.getId());
        ResultSet resultSet= preparedStatement.executeQuery();
        Adres adres= null;
        while (resultSet.next()){
            adres = new Adres(
                    resultSet.getInt("adres_id"),
                    resultSet.getString("postcode"),
                    resultSet.getString("huisnummer"),
                    resultSet.getString("straat"),
                    resultSet.getString("woonplaats"),
                   reiziger);

        }
        return adres;
    }
    }

