package nl.hu.ovchip.data.DAO;

import nl.hu.ovchip.domain.Adres;
import nl.hu.ovchip.domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {
    boolean save(Adres adres) throws SQLException;
    boolean update (Adres oudAdres, Adres nieuweAdres) throws SQLException;
    boolean delete (Adres reiziger) throws SQLException;
    List<Adres> findAll() throws SQLException;
    Adres findByReiziger(Reiziger reiziger) throws SQLException;
}
