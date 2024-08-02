import java.io.*;
import java.util.*;

public class ListShuffle {

    public static void main(String[] args) {
    	String inputFile = "F:\\Mods\\id_list_merged.txt";  // Output file path
        String outputFile = "F:\\Mods\\ids_divided.txt";  // Output file path

        try {
            // Read the input file
            List<String> itemList = readItemsFromFile(inputFile);
            
            // Remove duplicates from the list
            Set<String> uniqueItems = new LinkedHashSet<>(itemList);
            itemList.clear();
            itemList.addAll(uniqueItems);
            
            // Shuffle the list
            Collections.shuffle(itemList);
            
            // Divide the list into 5 parts
            List<List<String>> dividedList = divideList(itemList, 5);
            
            // Write the divided lists to the output file
            writeListsToFile(dividedList, outputFile);
            
            System.out.println("Divided and saved the unique list into 5 parts in the output file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read items from file
    private static List<String> readItemsFromFile(String filePath) throws IOException {
        List<String> itemList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            itemList.add(line);
        }
        reader.close();
        return itemList;
    }

    // Divide list into n parts
    private static List<List<String>> divideList(List<String> itemList, int parts) {
        List<List<String>> dividedList = new ArrayList<>();
        int itemsPerPart = itemList.size() / parts;
        int startIndex = 0;
        int endIndex = itemsPerPart;
        for (int i = 0; i < parts; i++) {
            if (i == parts - 1) {
                endIndex = itemList.size();
            }
            dividedList.add(itemList.subList(startIndex, endIndex));
            startIndex = endIndex;
            endIndex = Math.min(endIndex + itemsPerPart, itemList.size());
        }
        return dividedList;
    }

    // Write lists to file
    private static void writeListsToFile(List<List<String>> dividedList, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (int i = 0; i < dividedList.size(); i++) {
            writer.write("Part " + (i + 1) + ":\n");
            for (String item : dividedList.get(i)) {
                writer.write(item);
                writer.newLine();
            }
            writer.write("\n");
        }
        writer.close();
    }
}