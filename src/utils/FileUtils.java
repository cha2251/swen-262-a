package utils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class FileUtils {
    final String root;
    public FileUtils(String root) {
        this.root = root;
    }

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
    public void writeToFile(File file, String content, boolean append) {
        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(file, append));
            myWriter.write(content);
            myWriter.newLine();
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public List<String> readFromFile(File file) {
        List<String> lines = new ArrayList<>();
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                lines.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return lines;
    }

    public void addEntry(StoredType type, String[] args) {
        StringBuilder csv = new StringBuilder();
        csv.append(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + ",");
        csv.append(type.name() + ",");
        for(String s : args) {
            csv.append(s + ",");
        }
        csv.deleteCharAt(csv.length()-1);
        writeToFile(new File(root + "/data/library.lbms"), csv.toString(), true);
    }
    public String readProperty(String key) {
        try {
            FileReader reader = new FileReader(root + "/data/config.properties");
            Properties props = new Properties();
            props.load(reader);
            return props.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAIL";
    }
    public void setProperty(String key, String value) {
        try {
            FileReader reader = new FileReader(root + "/data/config.properties");
            Properties props = new Properties();
            props.load(reader);
            props.setProperty(key,value);

            File configFile = new File(root + "/data/config.properties");
            FileWriter writer = new FileWriter(configFile);
            props.store(writer, "settings");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveDefaults() {
        try {
            Properties defaultProperties = new Properties();
            String time = LocalDateTime.of(0, 1, 1, 0, 0).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            defaultProperties.setProperty("ModifiedTime", time);
            defaultProperties.setProperty("currentID", "1");

            File configFile = new File(root + "/data/config.properties");
            FileWriter writer = new FileWriter(configFile);
            defaultProperties.store(writer, "LBMS Properties");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
