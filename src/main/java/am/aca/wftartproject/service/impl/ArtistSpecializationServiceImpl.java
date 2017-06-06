package am.aca.wftartproject.service.impl;


import am.aca.wftartproject.dao.ArtistSpecializationLcpDao;
import am.aca.wftartproject.dao.impl.ArtistSpecializationLcpDaoImpl;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.service.ArtistSpecializationService;
import am.aca.wftartproject.util.DBConnection;

import java.sql.SQLException;

/**
 * @author surik
 */
public class ArtistSpecializationServiceImpl implements ArtistSpecializationService {

    ArtistSpecializationLcpDao lcpDao = null;

    public ArtistSpecializationServiceImpl() throws SQLException, ClassNotFoundException {
        lcpDao = new ArtistSpecializationLcpDaoImpl(new DBConnection().getDBConnection(DBConnection.DBType.REAL));
    }

    /**
     * @param specialization
     *
     * @see ArtistSpecializationService#addArtistSpecialization(ArtistSpecialization)
     */
    @Override
    public void addArtistSpecialization(ArtistSpecialization specialization) {
        lcpDao.addArtistSpecialization(specialization);
    }

    /**
     * @param id
     * @return
     *
     * @see ArtistSpecializationService#getArtistSpecialization(int)
     */
    @Override
    public ArtistSpecialization getArtistSpecialization(int id) {

        return lcpDao.getArtistSpecialization(id);
    }

    /**
     * @param specialization
     * @return
     *
     * @see ArtistSpecializationService#getArtistSpecialization(String)
     */
    @Override
    public ArtistSpecialization getArtistSpecialization(String specialization) {

        return lcpDao.getArtistSpecialization(specialization);
    }

    /**
     * @param id
     *
     * @see ArtistSpecializationService#deleteArtistSpecialization(int)
     */
    @Override
    public void deleteArtistSpecialization(int id) {

        lcpDao.deleteArtistSpecialization(id);
    }
}
