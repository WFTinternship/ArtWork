
package am.aca.wftartproject;

import am.aca.wftartproject.dao.ArtistDao;
import am.aca.wftartproject.dao.impl.ArtistDaoImpl;
import am.aca.wftartproject.model.Artist;
import am.aca.wftartproject.model.ArtistSpecialization;
import am.aca.wftartproject.service.ArtistService;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;
@Component
public class Main {

    @Autowired
    private  ArtistServiceImpl artistService;
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Artist artist = new Artist();
        artist.setFirstName("abg");
        artist.setLastName("ez").setAge(25).setEmail("abraaaaa@mail.ru");

    }

    public void add (Artist artist){
        artistService.addArtist(artist);

    }
}

