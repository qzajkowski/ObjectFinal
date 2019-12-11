import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Scraper {
    public static ArrayList<DataObject> scrapePage(String url) throws MalformedURLException {

        ArrayList<DataObject> objects = new ArrayList<>();
        ArrayList<String> lines = new ArrayList<>();

        URL link = new URL(url);
        String line;
        try (Scanner linkScanner = new Scanner(link.openStream())) {
            while (linkScanner.hasNextLine()) {
                line = linkScanner.nextLine();
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (String l : lines) {
            sb.append(l).append("\n");
        }
        objects.add(new DataObject(sb.toString()));
        return objects;
    }
}
