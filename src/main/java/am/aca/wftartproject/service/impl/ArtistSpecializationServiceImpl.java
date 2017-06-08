package am.aca.wftartproject.service.impl;


import am.aca.wftartproject.dao.ArtistSpecializationLcpDao;
import am.aca.wftartproject.dao.impl.ArtistSpecializationLcpDaoImpl;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.exception.ServiceException;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ArtistSpecializationService;
import am.aca.wftartproject.util.DBConnection;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * @author surik
 */
public class ArtistSpecializationServiceImpl implements ArtistSpecializationService {

    private static final Logger LOGGER = Logger.getLogger(ArtistService.class);

    private ArtistSpecializationLcpDao lcpDao = null;

    public ArtistSpecializationServiceImpl() throws SQLException, ClassNotFoundException {
        lcpDao = new ArtistSpecializationLcpDaoImpl(new DBConnection().getDBConnection(DBConnection.DBType.REAL));
    }

    /**
     * @see ArtistSpecializationService#addArtistSpecialization()
     */
    @Override
    public void addArtistSpecialization() {

        try{
            lcpDao.addArtistSpecialization();
        }catch (DAOException e){
            String error = "Failed to add ArtistSpecialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }

    }

    /**
     * @param id
     * @return
     *
     * @see ArtistSpecializationService#getArtistSpecialization(int)
     */
    @Override
    public ArtistSpecialization getArtistSpecialization(int id) {

        try{
            return lcpDao.getArtistSpecialization(id);
        }catch (DAOException e){
            String error = "Failed to get Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }

    }

    /**
     * @param specialization
     * @return
     *
     * @see ArtistSpecializationService#getArtistSpecialization(String)
     */
    @Override
    public ArtistSpecialization getArtistSpecialization(String specialization) {

        try{
            return lcpDao.getArtistSpecialization(specialization);
        }catch (DAOException e){
            String error = "Failed to add Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }

    }

    /**
     * @see ArtistSpecializationService#deleteArtistSpecialization()
     */
    @Override
    public void deleteArtistSpecialization() {

        try{
            lcpDao.deleteArtistSpecialization();
        }catch (DAOException e){
            String error = "Failed to delete Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }

    }
}
