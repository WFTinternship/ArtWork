
package am.aca.wftartproject;

import am.aca.wftartproject.entity.Artist;
import am.aca.wftartproject.service.impl.ArtistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Calendar;


public class Main {
    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().toLocalDate());
    }
}

