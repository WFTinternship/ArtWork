package am.aca.wftartproject.dao;

import am.aca.wftartproject.model.ArtistSpecialization;

/**
 * @author surik
 */
public interface ArtistSpecializationLcpDao {

    /**
     * Adds artist specializations into database
     */
    void addArtistSpecialization();

    /**
     * Gets artist specialization by id
     *
     * @param id
     */
    ArtistSpecialization getArtistSpecialization(int id);

    /**
     * Gets artist specialization
     *
     * @param specialization
     * @return
     */
    ArtistSpecialization getArtistSpecialization(String specialization);

    /**
     * Deletes artist specialization
     */
    void deleteArtistSpecialization();

}
