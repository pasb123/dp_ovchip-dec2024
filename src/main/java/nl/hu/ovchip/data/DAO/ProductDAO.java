package nl.hu.ovchip.data.DAO;

import nl.hu.ovchip.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    boolean save(Product product)  throws SQLException;
    boolean update(Product oudProduct, Product nieuwProduct,int ovChipkaartNummer, String status) throws  SQLException;
    boolean delete(Product product, int ovChipkaartNummer) throws SQLException;
    Product findById(int productNummer) throws SQLException;
    List<Product> findByOVChipkaart(Product product,int ovChipkaartNummer) throws SQLException;
    List<Product> findAll() throws  SQLException;
}
