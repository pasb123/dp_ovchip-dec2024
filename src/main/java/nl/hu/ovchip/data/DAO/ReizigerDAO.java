package nl.hu.ovchip.data.DAO;

import nl.hu.ovchip.domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {
    boolean save(Reiziger reiziger) throws SQLException;
    boolean update (Reiziger oudeReiziger, Reiziger nieuweReiziger) throws SQLException;
    boolean delete (Reiziger reiziger) throws SQLException;
    List<Reiziger> findAll() throws SQLException;
    Reiziger findById(int id) throws SQLException;
}
