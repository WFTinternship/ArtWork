
package am.aca.wftartproject;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    @Autowired
    private static ArtistServiceImpl artistService;
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Artist artist = new Artist();
        artist.setFirstName("abg");
        artist.setLastName("ez").setAge(25).setEmail("abraaaaa@mail.ru");
        artistService.addArtist(artist);


    }
}

