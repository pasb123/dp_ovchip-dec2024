package nl.hu.ovchip.data.implementation.product;

import nl.hu.ovchip.data.DAO.ProductDAO;
import nl.hu.ovchip.domain.OVChipkaart;
import nl.hu.ovchip.domain.Product;
import nl.hu.ovchip.domain.Reiziger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql  implements ProductDAO {
    private Connection conn ;

    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("" +
                "INSERT INTO product (product_nummer, naam, beschrijving,prijs) " +
                "VALUES (?, ?, ?,?)");
        statement.setInt(1, product.getProductNummer());
        statement.setString(2, product.getNaam());
        statement.setString(3, product.getBeschrijving());
        statement.setDouble(4, product.getPrijs());
        statement.execute();
        statement.close();
        return true;
    }

    @Override
    public boolean update(Product oudProduct, Product nieuwProduct,int ovChipkaartNummer, String status) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("" +
                "SELECT * FROM  ov_chipkaart_product where product_nummer = ? and kaart_nummer = ?");
        statement.setInt(1,oudProduct.getProductNummer());
        statement.setInt(2,ovChipkaartNummer);
        ResultSet rs= statement.executeQuery();

        PreparedStatement statement2 = conn.prepareStatement("" +
                "update product set naam=?, beschrijving=?,prijs=? " +
                "where product_nummer=?");
        statement2.setString(1, nieuwProduct.getNaam());
        statement2.setString(2, nieuwProduct.getBeschrijving());
        statement2.setDouble(3, nieuwProduct.getPrijs());
        statement2.setInt(4,oudProduct.getProductNummer());
        statement2.execute();
        statement2.close();
     if (!rs.next()) {
           nieuwProduct.addOVChipkaart(ovChipkaartNummer);
           PreparedStatement statement3 = conn.prepareStatement(""
           +" insert into ov_chipkaart_product (kaart_nummer,product_nummer, last_update,status) " +
                   "values (?,?,?,?)");
           statement3.setInt(1,ovChipkaartNummer);
           statement3.setInt(2,oudProduct.getProductNummer());
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
    public boolean delete(Product product, int ovChipkaartNummer) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("" +
                "SELECT * FROM  ov_chipkaart_product where product_nummer = ? and kaart_nummer = ?");
        statement.setInt(1,product.getProductNummer());
        statement.setInt(2,ovChipkaartNummer);
        ResultSet rs= statement.executeQuery();

        if (rs.next()) {
            PreparedStatement statement3 = conn.prepareStatement("" +
                    "delete FROM  ov_chipkaart_product where product_nummer = ? ");
            statement3.setInt(1,product.getProductNummer());
            statement3.execute();
            statement3.close();
        PreparedStatement statement2= conn.prepareStatement("delete from product " +
                "where product_nummer=?");
        statement2.setInt(1,product.getProductNummer());
        statement2.execute();
        statement2.close();
        }
        statement.close();
        return true;
    }

    @Override
    public Product findById(int productNummer) throws SQLException {



        PreparedStatement preparedStatement =conn.prepareStatement(""+"" +
                "SELECT * FROM product " + "where product_nummer=?");
        preparedStatement.setInt(1,productNummer);
        ResultSet resultSet= preparedStatement.executeQuery();
        Product product = null;

        while (resultSet.next()){
            product=new Product(
                    resultSet.getInt("product_nummer"),
                    resultSet.getString("naam"),
                    resultSet.getString("beschrijving"),
                    resultSet.getDouble("prijs"));
        }preparedStatement.close();

        return product;

    }

    @Override
    public List<Product> findByOVChipkaart(Product product,int ovChipkaartNummer) throws SQLException {
        PreparedStatement statement= conn.prepareStatement("select c.*,p.* from ov_chipkaart c " +
                "join ov_chipkaart_product cp on c.kaart_nummer = cp.kaart_nummer " +
                "join  product p on cp.product_nummer = p.product_nummer where cp.kaart_nummer=?");
        PreparedStatement statement2= conn.prepareStatement("select * from ov_chipkaart_product where product_nummer=?");
        statement.setInt(1,ovChipkaartNummer);
        ResultSet resultSet= statement.executeQuery();
        List<Product> productList= new ArrayList<>();
        while (resultSet.next()) {
            Product product2= new Product(
                    resultSet.getInt("product_nummer"),
                    resultSet.getString("naam"),
                    resultSet.getString("beschrijving"),
                    resultSet.getDouble("prijs")
            );
            statement2.setInt(1,product2.getProductNummer());
            ResultSet resultSet1= statement2.executeQuery();
            while (resultSet1.next()){
                product2.addOVChipkaart(resultSet1.getInt("kaart_nummer"));
            }
            productList.add(product2);
        }
        statement.close();
        return productList;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        PreparedStatement statement= conn.prepareStatement("select * from product");
        PreparedStatement statement2= conn.prepareStatement("select * from ov_chipkaart_product where product_nummer=?");
        ResultSet resultSet= statement.executeQuery();

        List<Product> productList= new ArrayList<>();
        while (resultSet.next()) {
            Product product2= new Product(
                    resultSet.getInt("product_nummer"),
                    resultSet.getString("naam"),
                    resultSet.getString("beschrijving"),
                    resultSet.getDouble("prijs")

            );
            statement2.setInt(1,product2.getProductNummer());
            ResultSet resultSet1= statement2.executeQuery();
            while (resultSet1.next()){
                product2.addOVChipkaart(resultSet1.getInt("kaart_nummer"));
            }
            productList.add(product2);
        }

        return productList;
    }
}
