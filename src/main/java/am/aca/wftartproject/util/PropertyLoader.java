package am.aca.wftartproject.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by surik on 6/1/17
 */


public class PropertyLoader {

    public static Properties getProperties(String FILE_DIR){

        Properties prop = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(PropertyLoader.class.getClassLoader().getResource(FILE_DIR).getPath());
            prop.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }


}
