package nl.hu.ovchip.data.implementation.ovchipkaart;

import nl.hu.ovchip.data.DAO.OVChipkaartDAO;
import nl.hu.ovchip.data.implementation.reiziger.ReizigerDOAPsql;
import nl.hu.ovchip.domain.Adres;
import nl.hu.ovchip.domain.OVChipkaart;
import nl.hu.ovchip.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection conn ;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("" +
                "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse,saldo,reiziger_id) VALUES (?, ?, ?,?,?)");
        statement.setInt(1, ovChipkaart.getKaartNummer());
        statement.setDate(2, ovChipkaart.getGeldigTot());
        statement.setInt(3, ovChipkaart.getKlasse());
        statement.setDouble(4, ovChipkaart.getSaldo());
        statement.setInt(5,ovChipkaart.getReiziger().getId());
        statement.execute();
        statement.close();
        return true;
    }

    @Override
    public boolean update(OVChipkaart oudOvchipkaart, OVChipkaart nieuwOvChipkaart) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("" +
                " update ov_chipkaart set geldig_tot=?, klasse=?,saldo=?,reiziger_id=? where kaart_nummer=?");
        statement.setDate(1, nieuwOvChipkaart.getGeldigTot());
        statement.setInt(2, nieuwOvChipkaart.getKlasse());
        statement.setDouble(3, nieuwOvChipkaart.getSaldo());
        statement.setInt(4,nieuwOvChipkaart.getReiziger().getId());
        statement.setInt(5,oudOvchipkaart.getKaartNummer());
        statement.execute();
        statement.close();
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        PreparedStatement statement = this.conn.prepareStatement("" +"DELETE FROM ov_chipkaart where kaart_nummer=?");
        statement.setInt(1, ovChipkaart.getKaartNummer());
        statement.execute();
        statement.close();
        return true;
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM ov_chipkaart");
        ReizigerDOAPsql reizigerDOAPsql= new ReizigerDOAPsql(conn);
        List<OVChipkaart> ovChipkaartList = new ArrayList<>();
        while (resultSet.next()) {
            OVChipkaart ovChipkaart= new OVChipkaart(
                    resultSet.getInt("kaart_nummer"),
                    resultSet.getDate("geldig_tot"),
                    resultSet.getInt("klasse"),
                    resultSet.getDouble("saldo"),
                    reizigerDOAPsql.findById(resultSet.getInt("reiziger_id"))

            );
            ovChipkaartList.add(ovChipkaart);
        }
        return ovChipkaartList;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM ov_chipkaart where reiziger_id = ?");
        preparedStatement.setInt(1,reiziger.getId());
       ResultSet resultSet= preparedStatement.executeQuery();
        List<OVChipkaart> ovChipkaartList = new ArrayList<>();
        while (resultSet.next()) {
            OVChipkaart ovChipkaart= new OVChipkaart(
                    resultSet.getInt("kaart_nummer"),
                    resultSet.getDate("geldig_tot"),
                    resultSet.getInt("klasse"),
                    resultSet.getDouble("saldo"),
                    reiziger);
            ovChipkaartList.add(ovChipkaart);
        }
        return ovChipkaartList;
    }
}
