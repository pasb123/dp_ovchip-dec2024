package nl.hu.ovchip.data.implementation.adres;

import jakarta.persistence.Query;
import nl.hu.ovchip.data.DAO.AdresDAO;
import nl.hu.ovchip.domain.Adres;
import nl.hu.ovchip.domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class AdresDAOHibernate implements AdresDAO {
    private Session session;

    public AdresDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            session.beginTransaction();
            session.merge(adres);
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            throw new HibernateException("it seems we cant save the adress "+e);
        }
        return true;
    }

    @Override
    public boolean update(Adres oudAdres, Adres nieuweAdres) {
        try {

            if (session.get(Adres.class,oudAdres.getAdres_id())!=null){
                session.beginTransaction();
                session.merge(nieuweAdres);
                session.getTransaction().commit();
            }
        }
        catch (HibernateException e){
            throw new HibernateException("it seems we cant update the adres "+e);
        }
        return true;
    }

    @Override
    public boolean delete(Adres adres) {
        try{
            session.beginTransaction();
            session.remove(adres);
            session.getTransaction().commit();}
        catch (HibernateException e){
            throw new HibernateException("it seems we cant remove the adres "+e);
        }
        return true;

    }

    @Override
    public List<Adres> findAll() throws SQLException {
         return session.createQuery("select adres from Adres adres", Adres.class).list();
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {

Adres adres=null;
        try{ adres= session.createQuery("select adres from Adres adres where reiziger.reiziger_id =:reiziger_id",Adres.class)
                .setParameter("reiziger_id",reiziger.getId()).getSingleResult();}
        catch (HibernateException ex){
            throw new HibernateException("find by reiziger cannot be completed "+ ex);
        }
        return adres;
    }

}
