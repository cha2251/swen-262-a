import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIBooks {
    String line;
    BufferedReader reader;
    StringBuffer responseContent;
    HttpURLConnection connection;
    FileUtils utils;
    public APIBooks(FileUtils utils) {
        this.utils = utils;
    }
    public Book getBook(String isbn) {
        try {
            responseContent = new StringBuffer();
            URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn +"&key=AIzaSyC6w3D0Vkqxhevsjg38toIQIEhQejrjy4M");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            if(status < 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            return parse(responseContent.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            connection.disconnect();
        }
    }
    public Book parse(String raw) {
        if(JsonParser.parseString(raw).isJsonObject()) {
            JsonObject root = JsonParser.parseString(raw).getAsJsonObject();
            if(root.get("totalItems").getAsInt() > 0) {
                JsonElement bookData = root.get("items");
                JsonArray workable = bookData.getAsJsonArray();
                JsonObject firstItem = workable.get(0).getAsJsonObject();
                JsonObject volumeDetails = firstItem.get("volumeInfo").getAsJsonObject();
                //Get specific data
                JsonElement title = volumeDetails.get("title");
                JsonElement authors = volumeDetails.get("authors");
                JsonElement date = volumeDetails.get("publishedDate");
                JsonElement pages = volumeDetails.get("pageCount");
                JsonArray ids = volumeDetails.getAsJsonArray("industryIdentifiers");
                JsonObject isbn_13 = ids.get(0).getAsJsonObject();
                JsonElement isbn = isbn_13.get("identifier");
                Book googleBook = new Book(isbn.getAsLong(), title.getAsString(), authors.getAsString(),
                        "From Google API (NO PUB)", date.getAsString(), pages.getAsInt());
                String entry = isbn.getAsLong() + "," + title.toString() + "," +
                        authors.toString().replace("[","{").replace("]","}") +
                        ",\"NO PUB\"," + date.getAsString()+ "," +pages.getAsInt();
                utils.writeToFile(new File(utils.root + "/data/books.txt"),entry, true);
                return googleBook;
            }
        }
        return null;
    }
}
