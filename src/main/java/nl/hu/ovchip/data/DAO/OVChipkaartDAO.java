package nl.hu.ovchip.data.DAO;

import nl.hu.ovchip.domain.Adres;
import nl.hu.ovchip.domain.OVChipkaart;
import nl.hu.ovchip.domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    boolean save(OVChipkaart ovChipkaart) throws SQLException;
    boolean update (OVChipkaart oudOvchipkaart, OVChipkaart nieuwOvChipkaart,int productNummer,String status) throws SQLException;
    boolean delete (OVChipkaart ovChipkaart, int productNummer) throws SQLException;
    List<OVChipkaart> findAll() throws SQLException;
    List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;

}
