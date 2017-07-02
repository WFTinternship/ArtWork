package am.aca.wftartproject.dao.impl;

import am.aca.wftartproject.dao.ArtistSpecializationLkpDao;
import am.aca.wftartproject.dao.rowmappers.ArtistSpecializationlkpMapper;
import am.aca.wftartproject.exception.dao.DAOException;
import am.aca.wftartproject.model.ArtistSpecialization;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author surik
 */
@Component
public class ArtistSpecializationLkpDaoImpl extends BaseDaoImpl implements ArtistSpecializationLkpDao {

    private static final Logger LOGGER = Logger.getLogger(ArtistSpecializationLkpDaoImpl.class);

    private SessionFactory sessionFactory;
    @Autowired
    public ArtistSpecializationLkpDaoImpl(SessionFactory sf) {
        this.sessionFactory = sf;
    }
    /**
     * @see ArtistSpecializationLkpDao#addArtistSpecialization()
     */
    @Override
    public void addArtistSpecialization() {

        try {
            String query = "INSERT INTO artist_specialization_lkp(id,spec_type) VALUES(?,?)";
            int rowsAffected = 0;
            for (ArtistSpecialization artSpecElement : ArtistSpecialization.values()) {
                jdbcTemplate.update(query, artSpecElement.getId(), artSpecElement.getType());
                rowsAffected++;
            }
            if (rowsAffected == 0) {
                throw new DAOException("Failed to add specialization");
            }
        } catch (DataAccessException e) {
            String error = "Failed to add specialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }

//        region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        try {
//            conn = getDataSource().getConnection();
//            for (ArtistSpecialization artSpecElement : ArtistSpecialization.values()) {
//                ps = conn.prepareStatement("INSERT INTO artist_specialization_lkp(id,spec_type) VALUES(?,?)");
//                ps.setInt(1, artSpecElement.getId());
//                ps.setString(2, artSpecElement.getType());
//                ps.executeUpdate();
//            }
//        } catch (SQLException e) {
//            String error = "Failed to add specialization: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(ps, conn);
//        }
//        endregion
    }


    /**
     * @see ArtistSpecializationLkpDao#getArtistSpecialization(int)
     * @param id
     * @return
     */
    @Override
    public ArtistSpecialization getArtistSpecialization(int id) {

        try {
            String query = "SELECT * FROM artist_specialization_lkp WHERE id = ?";
            return jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> new ArtistSpecializationlkpMapper().mapRow(rs,rowNum));

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get specialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }


//        region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        ArtistSpecialization tempArtSpec = null;
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement(
//                    "SELECT * FROM artist_specialization_lkp WHERE id = ?");
//            ps.setInt(1, id);
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                tempArtSpec = ArtistSpecialization.valueOf(rs.getString("spec_type"));
//            }
//        } catch (SQLException e) {
//            String error = "Failed to get specialization: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }
//        return tempArtSpec;

//        endregion

    }


    /**
     * @see ArtistSpecializationLkpDao#getArtistSpecialization(String)
     * @param specialization
     * @return
     */
    @Override
    public ArtistSpecialization getArtistSpecialization(String specialization) {

        try {
            String query = "SELECT * FROM artist_specialization_lkp WHERE spec_type = ?";
            return jdbcTemplate.queryForObject(query, new Object[]{specialization}, (rs, rowNum) -> new ArtistSpecializationlkpMapper().mapRow(rs,rowNum));

        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            String error = "Failed to get specialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }

//        region <Version with Simple JDBC>

//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        ArtistSpecialization tempArtSpec = null;
//        try {
//            conn = getDataSource().getConnection();
//            ps = conn.prepareStatement(
//                    "SELECT * FROM artist_specialization_lkp WHERE spec_type = ?");
//            ps.setString(1, specialization);
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                tempArtSpec = ArtistSpecialization.valueOf(rs.getString("spec_type"));
//            }
//        } catch (SQLException e) {
//            String error = "Failed to get specialization: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(rs, ps, conn);
//        }
//        return tempArtSpec;

//        endregion
    }


    /**
     * @see ArtistSpecializationLkpDao#deleteArtistSpecialization()
     */
    @Override
    public void deleteArtistSpecialization() {

        try {
            String query = "DELETE FROM artist_specialization_lkp";

            int rowsAffected = jdbcTemplate.update(query);
            if (rowsAffected <= 0) {
                throw new DAOException("Failed to delete specialization");
            }
        } catch (DataAccessException e) {
            String error = "Failed to delete specialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        }

//        region <Version with Simple JDBC>

//        Connection conn = null;
//        Statement st = null;
//        try {
//            conn = getDataSource().getConnection();
//            st = conn.createStatement();
//            st.executeUpdate("DELETE FROM artist_specialization_lkp");
//        } catch (SQLException e) {
//            String error = "Failed to delete specialization: %s";
//            LOGGER.error(String.format(error, e.getMessage()));
//            throw new DAOException(error, e);
//        } finally {
//            closeResources(st, conn);
//        }

//        endregion
    }
}
