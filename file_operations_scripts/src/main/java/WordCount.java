import java.io.*;
import java.util.*;

public class WordCount {

    public static void main(String[] args) {
        String file1 = "F:\\Mods\\wordcount_ids.txt";  // Update this with the actual path
        String file2 = "F:\\Mods\\wordcount_machines.txt";  // Update this with the actual path
        String outputFile = "F:\\Mods\\wordcount_output.txt";  // Update this with the actual path


        try {
            // Read the first words from file1
            List<String> words = readFirstWordsFromFile(file1);

            // Count lines containing each word in file2
            Map<String, Integer> wordCounts = countLinesContainingWords(file2, words);

            // Sort the word counts by number of appearances
            List<Map.Entry<String, Integer>> sortedWordCounts = sortWordCounts(wordCounts);

            // Write the sorted results to output file
            writeWordCountsToFile(outputFile, sortedWordCounts);

            System.out.println("Word counts written to " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read the first words from each line in file1
    private static List<String> readFirstWordsFromFile(String filename) throws IOException {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String firstWord = getFirstWord(line);
                if (firstWord != null) {
                    words.add(firstWord);
                }
            }
        }
        return words;
    }

    // Helper method to get the first word from a line
    private static String getFirstWord(String line) {
        String[] words = line.split("\\s+");
        if (words.length > 0) {
            return words[0];
        }
        return null;
    }

    // Count lines containing each word in file2
    private static Map<String, Integer> countLinesContainingWords(String filename, List<String> words) throws IOException {
        Map<String, Integer> wordCounts = new HashMap<>();
        for (String word : words) {
            wordCounts.put(word, 0);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (String word : words) {
                    if (line.contains(word)) {
                        wordCounts.put(word, wordCounts.get(word) + 1);
                    }
                }
            }
        }
        return wordCounts;
    }

    // Sort word counts by number of appearances
    private static List<Map.Entry<String, Integer>> sortWordCounts(Map<String, Integer> wordCounts) {
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(wordCounts.entrySet());
        sortedList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        return sortedList;
    }

    // Write the sorted word counts to the output file
    private static void writeWordCountsToFile(String filename, List<Map.Entry<String, Integer>> wordCounts) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, Integer> entry : wordCounts) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
        }
    }
}