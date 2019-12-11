import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Writer {
    public static String writeToScreen(ArrayList<DataObject> object) {
        return object.get(0).getCode();
    }

    public static void writeToText(String filename, String text) {
        try {
            FileWriter fileWriter = new FileWriter(new File(".").getCanonicalFile() + "/" + filename);
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToJson(String filename, String text) {
        DataObject object = new DataObject(text);
        String jsonString = buildJsonObject(object);
        try {
            FileWriter fileWriter = new FileWriter(new File(".").getCanonicalFile() + "/" + filename);
            fileWriter.write(jsonString);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String buildJsonObject(DataObject object) {
        return object.toString();
    }
}
