package nl.hu.ovchip.data.implementation;

import nl.hu.ovchip.data.DAO.ReizigerDAO;
import nl.hu.ovchip.domain.Reiziger;
import org.hibernate.*;

import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private Session session;

    public ReizigerDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            session.beginTransaction();
            session.persist(reiziger);
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            throw new HibernateException("it seems we cant save the traveler "+e);
        }
        return false;
    }

    @Override
    public boolean update(Reiziger oudeReiziger, Reiziger nieuweReiziger) {
try {
 ;
  if (session.get(Reiziger.class,oudeReiziger.getId())!=null){
      session.beginTransaction();
      session.merge(nieuweReiziger);
      session.getTransaction().commit();
  }
}
catch (HibernateException e){
    throw new HibernateException("it seems we cant find the traveler "+e);
}
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try{
        session.beginTransaction();
        session.remove(reiziger);
        session.getTransaction().commit();}
        catch (HibernateException e){
            throw new HibernateException("it seems we cant remove the traveler "+e);
        }
        return true;
    }

    @Override
    public List<Reiziger> findAll() {
        return session.createQuery("select reiziger from Reiziger reiziger", Reiziger.class).list();
    }
}
