package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ArtistSpecializationLkpDao;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.ArtistSpecialization;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * @author surik
 */
public class ArtistSpecializationLkpDaoImpl implements ArtistSpecializationLkpDao {

    private Connection conn = null;
    private static final Logger LOGGER = Logger.getLogger(ArtistSpecializationLkpDaoImpl.class);

    public ArtistSpecializationLkpDaoImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * @see ArtistSpecializationLkpDao#addArtistSpecialization()
     */
    @Override
    public void addArtistSpecialization() {
        try {
            PreparedStatement ps;
            for (ArtistSpecialization artSpecElement : ArtistSpecialization.values()) {
                ps = conn.prepareStatement("INSERT INTO artist_specialization_lkp(id,spec_type) VALUES(?,?)");
                ps.setInt(1, artSpecElement.getId());
                ps.setString(2, artSpecElement.getType());
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            String error = "Failed to add specialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
    }

    /**
     * @param id
     * @return
     * @see ArtistSpecializationLkpDao#getArtistSpecialization(int)
     */
    @Override
    public ArtistSpecialization getArtistSpecialization(int id) {
        ArtistSpecialization artSpec = ArtistSpecialization.PAINTER;
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM artist_specialization_lkp WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                artSpec = ArtistSpecialization.valueOf(rs.getString("spec_type"));
            }
            rs.close();
        } catch (SQLException e) {
            String error = "Failed to get specialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return artSpec;
    }

    /**
     * @param specialization
     * @return
     * @see ArtistSpecializationLkpDao#getArtistSpecialization(String)
     */
    @Override
    public ArtistSpecialization getArtistSpecialization(String specialization) {
        ArtistSpecialization artSpec = ArtistSpecialization.OTHER;
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM artist_specialization_lkp WHERE spec_type = ?")) {
            ps.setString(1, specialization);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                artSpec = ArtistSpecialization.valueOf(rs.getString("spec_type"));
            }
        } catch (SQLException e) {
            String error = "Failed to get specialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
        return artSpec;
    }

    /**
     * @see ArtistSpecializationLkpDao#deleteArtistSpecialization()
     */
    @Override
    public void deleteArtistSpecialization() {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate("DELETE FROM artist_specialization_lkp");
        } catch (SQLException e) {
            String error = "Failed to delete specialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
    }
}
