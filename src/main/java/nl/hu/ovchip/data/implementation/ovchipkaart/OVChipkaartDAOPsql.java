package nl.hu.ovchip.data.implementation.ovchipkaart;

import nl.hu.ovchip.data.DAO.OVChipkaartDAO;
import nl.hu.ovchip.data.implementation.product.ProductDAOPsql;
import nl.hu.ovchip.data.implementation.reiziger.ReizigerDOAPsql;
import nl.hu.ovchip.domain.Adres;
import nl.hu.ovchip.domain.OVChipkaart;
import nl.hu.ovchip.domain.Product;
import nl.hu.ovchip.domain.Reiziger;

import java.sql.*;
import java.time.LocalDate;
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
    public boolean update(OVChipkaart oudOvchipkaart, OVChipkaart nieuwOvChipkaart, int productNummer,String status) throws SQLException {
        ProductDAOPsql productDAOPsql= new ProductDAOPsql(conn);
        PreparedStatement statement = conn.prepareStatement("" +
                "SELECT * FROM  ov_chipkaart_product where product_nummer = ? and kaart_nummer = ?");
        statement.setInt(1,productNummer);
        statement.setInt(2,oudOvchipkaart.getKaartNummer());
        ResultSet rs= statement.executeQuery();

        PreparedStatement statement2 = conn.prepareStatement("" +
                " update ov_chipkaart set geldig_tot=?, klasse=?,saldo=?,reiziger_id=? where kaart_nummer=?");
        statement2.setDate(1, nieuwOvChipkaart.getGeldigTot());
        statement2.setInt(2, nieuwOvChipkaart.getKlasse());
        statement2.setDouble(3, nieuwOvChipkaart.getSaldo());
        statement2.setInt(4,nieuwOvChipkaart.getReiziger().getId());
        statement2.setInt(5,oudOvchipkaart.getKaartNummer());
        statement2.execute();
        statement2.close();

        if (!rs.next()) {
            Product product= productDAOPsql.findById(productNummer);
            nieuwOvChipkaart.addProduct(product);
            PreparedStatement statement3 = conn.prepareStatement(""
                    +" insert into ov_chipkaart_product (kaart_nummer,product_nummer, last_update,status) " +
                    "values (?,?,?,?)");
            statement3.setInt(1,oudOvchipkaart.getKaartNummer());
            statement3.setInt(2,productNummer);
            statement3.setDate(3, Date.valueOf(LocalDate.now()));
            statement3.setString(4,status);
            statement3.execute();
            statement3.close();
        } else {
            throw new SQLException("deze link tussen  product en ov kaart bestaat al");
        }
        statement.close();
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart,int productNummer) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("" +
                "SELECT * FROM  ov_chipkaart_product where product_nummer = ? and kaart_nummer = ?");
        statement.setInt(1,productNummer);
        statement.setInt(2,ovChipkaart.getKaartNummer());
        ResultSet rs= statement.executeQuery();

        if (!rs.next()) {
            try{
            PreparedStatement statement1= conn.prepareStatement("delete from ov_chipkaart_product " +
                    "where product_nummer=?");
            statement1.setInt(1,productNummer);
        PreparedStatement statement2 = this.conn.prepareStatement("" +"DELETE FROM ov_chipkaart " +
                "where kaart_nummer=?");
        statement2.setInt(1, ovChipkaart.getKaartNummer());
        statement1.execute();
        statement2.execute();
        statement1.close();
        statement2.close();}
        catch (SQLException e){
            throw new SQLException(e);
        }}


        return true;
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM ov_chipkaart");
        PreparedStatement statement2= conn.prepareStatement("select * from ov_chipkaart_product where kaart_nummer=?");
        ReizigerDOAPsql reizigerDOAPsql= new ReizigerDOAPsql(conn);
        ProductDAOPsql productDAOPsql = new ProductDAOPsql(conn);
        List<OVChipkaart> ovChipkaartList = new ArrayList<>();
        while (resultSet.next()) {
            OVChipkaart ovChipkaart= new OVChipkaart(
                    resultSet.getInt("kaart_nummer"),
                    resultSet.getDate("geldig_tot"),
                    resultSet.getInt("klasse"),
                    resultSet.getDouble("saldo"),
                    reizigerDOAPsql.findById(resultSet.getInt("reiziger_id"))

            );
            statement2.setInt(1,ovChipkaart.getKaartNummer());
            ResultSet resultSet1= statement2.executeQuery();
            while (resultSet1.next()){
                ovChipkaart.addProduct(productDAOPsql.findById(resultSet1.getInt("product_nummer")));
            }
            ovChipkaartList.add(ovChipkaart);
        }
        return ovChipkaartList;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        ProductDAOPsql productDAOPsql = new ProductDAOPsql(conn);
        PreparedStatement statement2= conn.prepareStatement("select * from ov_chipkaart_product where kaart_nummer=?");

        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM ov_chipkaart " +
                "where reiziger_id = ?");
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
            statement2.setInt(1,ovChipkaart.getKaartNummer());
            ResultSet resultSet1= statement2.executeQuery();
            while (resultSet1.next()){
                ovChipkaart.addProduct(productDAOPsql.findById(resultSet1.getInt("product_nummer")));
            }
            ovChipkaartList.add(ovChipkaart);
        }
        return ovChipkaartList;
    }
}
