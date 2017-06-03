package am.aca.wftartproject.util;

import java.io.*;

/**
 * Created by ASUS on 26-May-17
 */
public class PhotoIOHelper {

    private StringBuilder rootFolderPath = new StringBuilder("src\\main\\resources\\itemphotos\\test.jpg");

    public String addPhotoToDirectory(String fileName, Integer userId) {
        File file = new File(rootFolderPath.append(userId.toString()).append("\\").append(fileName).toString());
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    public String getPhotoFromDirectory(Integer userId) {
        File file = new File(rootFolderPath.append(userId.toString()).append("\\").toString());
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
