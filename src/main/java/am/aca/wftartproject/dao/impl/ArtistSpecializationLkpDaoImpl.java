package am.aca.wftartproject.dao.impl;
import am.aca.wftartproject.dao.ArtistSpecializationLkpDao;
import am.aca.wftartproject.exception.DAOException;
import am.aca.wftartproject.model.ArtistSpecialization;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @author surik
 */
public class ArtistSpecializationLkpDaoImpl extends BaseDaoImpl implements ArtistSpecializationLkpDao {

    private static final Logger LOGGER = Logger.getLogger(ArtistSpecializationLkpDaoImpl.class);

    public ArtistSpecializationLkpDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }


    /**
     * @see ArtistSpecializationLkpDao#addArtistSpecialization()
     */
    @Override
    public void addArtistSpecialization() {
        Connection conn = null;
        try {
            conn = getDataSource().getConnection();
            PreparedStatement ps;
            for (ArtistSpecialization artSpecElement : ArtistSpecialization.values()) {
                ps = conn.prepareStatement("INSERT INTO artist_specialization_lkp(id,spec_type) VALUES(?,?)");
                ps.setInt(1, artSpecElement.getId());
                ps.setString(2, artSpecElement.getType());
                ps.executeUpdate();
//                ps.close();
                closeResources(ps);
            }
        } catch (SQLException e) {
            String error = "Failed to add specialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            /*try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }*/
            closeResources(conn);
        }
    }


    /**
     * @see ArtistSpecializationLkpDao#getArtistSpecialization(int)
     * @param id
     * @return
     */
    @Override
    public ArtistSpecialization getArtistSpecialization(int id) {
        Connection conn = null;
        ArtistSpecialization tempArtSpec = null;
        try {
            conn = getDataSource().getConnection();

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM artist_specialization_lkp WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tempArtSpec = ArtistSpecialization.valueOf(rs.getString("spec_type"));
            }
            /*rs.close();
            ps.close();*/
            closeResources(rs, ps);
        } catch (SQLException e) {
            String error = "Failed to get specialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            /*try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }*/
            closeResources(conn);
        }
        return tempArtSpec;
    }


    /**
     * @see ArtistSpecializationLkpDao#getArtistSpecialization(String)
     * @param specialization
     * @return
     */
    @Override
    public ArtistSpecialization getArtistSpecialization(String specialization) {
        Connection conn = null;
        ArtistSpecialization tempArtSpec = null;
        try {
            conn = getDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM artist_specialization_lkp WHERE spec_type = ?");
            ps.setString(1, specialization);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tempArtSpec = ArtistSpecialization.valueOf(rs.getString("spec_type"));
            }
//            ps.close();
            closeResources(ps);//rs?
        } catch (SQLException e) {
            String error = "Failed to get specialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            /*try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }*/
            closeResources(conn);
        }
        return tempArtSpec;
    }


    /**
     * @see ArtistSpecializationLkpDao#deleteArtistSpecialization()
     */
    @Override
    public void deleteArtistSpecialization() {
        Connection conn = null;
        try {
            conn = getDataSource().getConnection();
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM artist_specialization_lkp");
//            st.close();
            closeResources(st);
        } catch (SQLException e) {
            String error = "Failed to delete specialization: %s";
            LOGGER.error(String.format(error, e.getMessage()));
            throw new DAOException(error, e);
        } finally {
            /*try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }*/
            closeResources(conn);
        }
    }
}
