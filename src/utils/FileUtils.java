package utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    public File CreateFile(String path, String name) {
        try {
            File myObj = new File(path + "/" + name);
            if (myObj.createNewFile()) {
                return myObj;
            } else {
                return null;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }
}
