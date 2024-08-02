import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SubfolderLister {
    public static void main(String[] args) {
        String folderPath = "F:\\Mods\\test";
        String outputFilePath = "F:\\Mods\\test.txt";

        try {
            readObjectJsonFiles(folderPath, outputFilePath);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void readObjectJsonFiles(String folderPath, String outputFilePath) throws IOException {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.getName().equals("object.json")) {
                    System.out.println("Processing file: " + file.getAbsolutePath());
                    extractAndWriteFields(file, outputFilePath);
                }
            }
        }
    }

    private static void extractAndWriteFields(File file, String outputFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             FileWriter writer = new FileWriter(outputFilePath, true)) {

            System.out.println("Starting extraction for file: " + file.getAbsolutePath());

            String line;
            boolean captureIngredients = false;
            String name = null;
            String edibility = null;
            String edibleIsDrink = null;
            StringBuilder ingredients = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                System.out.println("Read line: " + line);

                if (line.startsWith("\"Name\":")) {
                    name = getValue(line);
                    System.out.println("Found Name: " + name);
                } else if (line.startsWith("\"Edibility\":")) {
                    edibility = getValue(line);
                    System.out.println("Found Edibility: " + edibility);
                } else if (line.startsWith("\"EdibleIsDrink\":")) {
                    edibleIsDrink = getValue(line);
                    System.out.println("Found EdibleIsDrink: " + edibleIsDrink);
                } else if (line.startsWith("\"Ingredients\":")) {
                    captureIngredients = true;
                    System.out.println("Starting Ingredients capture");
                    continue;
                }

                if (captureIngredients && line.startsWith("[")) {
                    ingredients.append(line);
                    System.out.println("Appending Ingredients line: " + line);
                } else if (captureIngredients && line.endsWith("]")) {
                    ingredients.append(line);
                    System.out.println("Finishing Ingredients capture");
                    captureIngredients = false;
                }

                if (name != null && edibility != null && edibleIsDrink != null && ingredients.length() > 0) {
                    System.out.println("Writing to file...");
                    writer.write("Name: " + name + "\n");
                    writer.write("Edibility: " + edibility + "\n");
                    writer.write("EdibleIsDrink: " + edibleIsDrink + "\n");
                    writer.write("Ingredients: " + ingredients.toString() + "\n\n");
                    writer.flush();
                    break;
                }
            }
        }
    }

    private static String getValue(String line) {
        int startIndex = line.indexOf(":") + 1;
        int endIndex = line.length();

        if (line.endsWith(",")) {
            endIndex--;
        }

        return line.substring(startIndex, endIndex).trim().replaceAll("\"", "");
    }
}