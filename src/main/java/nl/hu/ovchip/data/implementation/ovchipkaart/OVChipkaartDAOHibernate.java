package nl.hu.ovchip.data.implementation.ovchipkaart;

import nl.hu.ovchip.data.DAO.OVChipkaartDAO;
import nl.hu.ovchip.domain.Adres;
import nl.hu.ovchip.domain.OVChipkaart;
import nl.hu.ovchip.domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    private Session session;

    public OVChipkaartDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        try {
            session.beginTransaction();
            session.merge(ovChipkaart);
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            throw new HibernateException("it seems we cant save the cart "+e);
        }
        return true;
    }


    @Override
    public boolean update(OVChipkaart oudOvchipkaart, OVChipkaart nieuwOvChipkaart, int productNummer, String status) throws SQLException {
        try {

            if (session.get(OVChipkaart.class,oudOvchipkaart.getKaartNummer())!=null){
                session.beginTransaction();
                session.merge(nieuwOvChipkaart);
                session.getTransaction().commit();
            }
        }
        catch (HibernateException e){
            throw new HibernateException("it seems we cant update the cart "+e);
        }
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart, int productnummer) throws SQLException {
        try{
            session.beginTransaction();
            session.remove(ovChipkaart);
            session.getTransaction().commit();}
        catch (HibernateException e){
            throw new HibernateException("it seems we cant remove the cart "+e);
        }
        return true;
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        return session.createQuery("select ovchipkaart from OVChipkaart ovchipkaart", OVChipkaart.class).list();
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        List<OVChipkaart> ovChipkaart;
        try{ ovChipkaart = new ArrayList<>(session.createQuery("select ovchipkaart from OVChipkaart ovchipkaart where reiziger.reiziger_id =:reiziger_id", OVChipkaart.class)
                .setParameter("reiziger_id", reiziger.getId()).list());}
        catch (HibernateException ex){
            throw new HibernateException("find by reiziger cannot be completed "+ ex);
        }
        return ovChipkaart;
    }
}
