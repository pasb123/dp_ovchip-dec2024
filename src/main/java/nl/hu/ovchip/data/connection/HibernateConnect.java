package nl.hu.ovchip.data.connection;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class HibernateConnect {
    public HibernateConnect() {
    }

    public static Session getSession() {
        Session session= null;
        try{ session= new Configuration().configure().buildSessionFactory().openSession();}
        catch (HibernateException ex){
            throw  new HibernateException(ex);
        }
        return session;
    }
}
