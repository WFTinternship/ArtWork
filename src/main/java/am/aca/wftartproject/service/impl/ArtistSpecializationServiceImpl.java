package am.aca.wftartproject.service.impl;


import am.aca.wftartproject.dao.ArtistSpecializationLkpDao;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.exception.ServiceException;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ArtistSpecializationService;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author surik
 */
public class ArtistSpecializationServiceImpl implements ArtistSpecializationService {

    private static final Logger LOGGER = Logger.getLogger(ArtistService.class);

    private ArtistSpecializationLkpDao lkpDao = null;

    public void setLkpDao(ArtistSpecializationLkpDao lkpDao) {
        this.lkpDao = lkpDao;
    }

//        public ArtistSpecializationServiceImpl() throws SQLException, ClassNotFoundException {
//        DataSource conn = new ConnectionFactory().getConnection(ConnectionModel.POOL).getProductionDBConnection();
//        lkpDao = new ArtistSpecializationLkpDaoImpl(conn);
//    }


    /**
     * @see ArtistSpecializationService#addArtistSpecialization()
     */
    @Override
    @Transactional("transactionManager")
    public void addArtistSpecialization() {
        try {
            lkpDao.addArtistSpecialization();
        } catch (DAOException e) {
            String error = "Failed to add ArtistSpecialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @see ArtistSpecializationService#getArtistSpecialization(int)
     * @param id
     * @return
     */
    @Override
    @Transactional("transactionManager")
    public ArtistSpecialization getArtistSpecialization(int id) {
        try {
            return lkpDao.getArtistSpecialization(id);
        } catch (DAOException e) {
            String error = "Failed to get Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @see ArtistSpecializationService#getArtistSpecialization(String)
     * @param specialization
     * @return
     */
    @Override
    @Transactional("transactionManager")
    public ArtistSpecialization getArtistSpecialization(String specialization) {
        try {
            return lkpDao.getArtistSpecialization(specialization);
        } catch (DAOException e) {
            String error = "Failed to add Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }


    /**
     * @see ArtistSpecializationService#deleteArtistSpecialization()
     */
    @Override
    @Transactional("transactionManager")
    public void deleteArtistSpecialization() {
        try {
            lkpDao.deleteArtistSpecialization();
        } catch (DAOException e) {
            String error = "Failed to delete Artist: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new ServiceException(String.format(error, e.getMessage()));
        }
    }
}
