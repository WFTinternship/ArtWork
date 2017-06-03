package am.aca.wftartproject.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ASUS on 26-May-17
 */
public class PropertyHelper {

    private final String FILE_DIR = getClass().getClassLoader().getResource("database-config.properties").getPath();

    public Properties getProperties() {
        Properties prop = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(FILE_DIR);
            prop.load(inputStream);
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }

    public void getMessage(String str) {
        System.out.print(getProperties().getProperty(str));
    }

}
