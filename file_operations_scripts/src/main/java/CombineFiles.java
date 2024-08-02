import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CombineFiles {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String inputFolder = "F:\\Mods\\test";
        String outputFileBeforeKeyword = "F:\\Mods\\combined_crops.txt";
        String outputFileAfterKeyword = "F:\\Mods\\combined_seeds.txt";

        String keyword = "},";

        try {
            FileWriter writerBeforeKeyword = new FileWriter(outputFileBeforeKeyword);
            FileWriter writerAfterKeyword = new FileWriter(outputFileAfterKeyword);

            List<String> files = listFiles(inputFolder);

            for (String file : files) {
                boolean keywordFound = false;
                List<String> contents = readObjectFile(file);
                for (String line : contents) {
                    if (!line.trim().isEmpty()) {
                        if (!keywordFound) {
                            writerBeforeKeyword.write(line + "\n");
                        } else {
                            writerAfterKeyword.write(line + "\n");
                        }

                        if (line.contains(keyword)) {
                            keywordFound = true;
                        }
                    }
                }
            }

            writerBeforeKeyword.close();
            writerAfterKeyword.close();
            System.out.println("Splitting completed successfully.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static List<String> listFiles(String inputFolder) {
        List<String> files = new ArrayList<>();
        File folder = new File(inputFolder);

        if (folder.isDirectory()) {
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isDirectory()) {
                        files.addAll(listFiles(file.getAbsolutePath()));
                    } else if (file.getName().equals("crop.json")) {
                        files.add(file.getAbsolutePath());
                    }
                }
            }
        } else {
            System.err.println("Error: Input folder is not a directory.");
        }

        return files;
    }

    private static List<String> readObjectFile(String filePath) throws IOException {
        List<String> contents = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;
        while ((line = reader.readLine()) != null) {
            contents.add(line);
        }

        reader.close();
        return contents;
    }
}