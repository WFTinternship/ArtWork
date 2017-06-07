package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ArtistSpecializationLcpDao;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.ArtistSpecialization;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author surik
 */
public class ArtistSpecializationLcpDaoImpl implements ArtistSpecializationLcpDao {

    private Connection conn = null;
    private static final Logger LOGGER = Logger.getLogger(ArtistSpecializationLcpDaoImpl.class);

    public ArtistSpecializationLcpDaoImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param specialization
     * @see ArtistSpecializationLcpDao#addArtistSpecialization(ArtistSpecialization)
     */
    @Override
    public void addArtistSpecialization(ArtistSpecialization specialization) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO artist_specialization_lcp(id, spec_type) VALUES (?, ?)")) {

            ps.setInt(1, specialization.getSpecId());
            ps.setString(2, specialization.getType());

            ps.executeUpdate();

        } catch (SQLException e) {
            String error = "Failed to add specialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
    }

    /**
     * @param id
     * @return
     * @see ArtistSpecializationLcpDao#getArtistSpecialization(int)
     */
    @Override
    public ArtistSpecialization getArtistSpecialization(int id) {
        ArtistSpecialization art = ArtistSpecialization.OTHER;
        try (PreparedStatement ps = conn.prepareStatement("SELECT id, spec_type FROM artist_specialization_lcp WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                art.valueOf(rs.getString(2));
            }
            return art;

        } catch (SQLException e) {
            String error = "Failed to get specialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
    }

    /**
     * @param specialization
     * @return
     * @see ArtistSpecializationLcpDao#getArtistSpecialization(String)
     */
    @Override
    public ArtistSpecialization getArtistSpecialization(String specialization) {
        ArtistSpecialization art = ArtistSpecialization.OTHER;
        try (PreparedStatement ps = conn.prepareStatement("SELECT id, spec_type FROM artist_specialization_lcp WHERE spec_type = ?")) {
            ps.setString(1, specialization);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                art.valueOf(rs.getString(2));
            }
            return art;

        } catch (SQLException e) {
            String error = "Failed to get specialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
    }

    /**
     * @param id
     * @see ArtistSpecializationLcpDao#deleteArtistSpecialization(int)
     */
    @Override
    public void deleteArtistSpecialization(int id) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM artist_specialization_lcp WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            String error = "Failed to delete specialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }
    }
}
