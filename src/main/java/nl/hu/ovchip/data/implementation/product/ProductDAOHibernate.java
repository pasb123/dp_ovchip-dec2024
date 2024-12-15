package nl.hu.ovchip.data.implementation.product;

import nl.hu.ovchip.data.DAO.ProductDAO;
import nl.hu.ovchip.domain.OVChipkaart;
import nl.hu.ovchip.domain.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    private Session session;

    public ProductDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        try {
            session.beginTransaction();
            session.merge(product);
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            throw new HibernateException("it seems we cant save the cart "+e);
        }
        return true;
    }

    @Override
    public boolean update(Product oudProduct, Product nieuwProduct, int ovChipkaartNummer, String status) throws SQLException {
        try {

            if (session.get(OVChipkaart.class,oudProduct.getProductNummer())!=null){
                nieuwProduct.addOVChipkaart(ovChipkaartNummer);
                session.beginTransaction();
                session.merge(nieuwProduct);
                session.getTransaction().commit();
            }
        }
        catch (HibernateException e){
            throw new HibernateException("it seems we cant update the cart "+e);
        }
        return true;
    }

    @Override
    public boolean delete(Product product, int ovChipkaartNummer) throws SQLException {
        try{
            session.beginTransaction();
            session.remove(product);
            session.getTransaction().commit();}
        catch (HibernateException e){
            throw new HibernateException("it seems we cant remove the cart "+e);
        }
        return true;
    }

    @Override
    public Product findById(int productNummer) throws SQLException {
        return null;
    }

    @Override
    public List<Product> findByOVChipkaart(Product product, int ovChipkaartNummer) throws SQLException {
        return;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        return session.createQuery("FROM Product", Product.class).list();
    }
}
