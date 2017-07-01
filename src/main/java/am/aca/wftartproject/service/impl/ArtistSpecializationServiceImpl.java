package am.aca.wftartproject.service.impl;


import am.aca.wftartproject.dao.ArtistSpecializationLkpDao;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.exception.service.ServiceException;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.ArtistSpecializationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author surik
 */
@Service
@Transactional(readOnly = true)
public class ArtistSpecializationServiceImpl implements ArtistSpecializationService {
    private static final Logger LOGGER = Logger.getLogger(ArtistService.class);

    private final ArtistSpecializationLkpDao lkpDao;

    @Autowired
    public ArtistSpecializationServiceImpl(ArtistSpecializationLkpDao lkpDao) {
        this.lkpDao = lkpDao;
    }

    /**
     * @see ArtistSpecializationService#addArtistSpecialization()
     */
    @Override
    @Transactional
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
    @Transactional
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
