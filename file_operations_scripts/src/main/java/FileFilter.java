import java.io.*;
import java.nio.file.*;
import java.util.*;


public class FileFilter {
    
    public static void main(String[] args) {
        String inputFileName = "F:\\Mods\\tokenizer.txt";
        String outputFileName = "F:\\Mods\\tokencount.txt";

        try {
            // Read lines from input file
            List<String> lines = readLinesFromFile(inputFileName);
            
            // Count tokens appearances
            Map<String, Integer> tokenCount = countTokens(lines);
            
            // Sort tokens by count
            List<Map.Entry<String, Integer>> sortedTokens = sortTokensByCount(tokenCount);
            
            // Write sorted tokens to output file
            writeSortedTokensToFile(sortedTokens, outputFileName);
            
            System.out.println("Tokens sorted and written to " + outputFileName);
            
        } catch (IOException e) {
            System.err.println("Error reading or writing files: " + e.getMessage());
        }
    }
    
    private static List<String> readLinesFromFile(String fileName) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.replaceAll("\\s+", ""));
            }
        }
        return lines;
    }
    
    private static Map<String, Integer> countTokens(List<String> lines) {
        Map<String, Integer> tokenCount = new HashMap<>();
        for (String line : lines) {
            if (!line.startsWith("Name")) {
                tokenCount.put(line, tokenCount.getOrDefault(line, 0) + 1);
            }
        }
        return tokenCount;
    }
    
    private static List<Map.Entry<String, Integer>> sortTokensByCount(Map<String, Integer> tokenCount) {
        List<Map.Entry<String, Integer>> sortedTokens = new ArrayList<>(tokenCount.entrySet());
        sortedTokens.sort((a, b) -> b.getValue().compareTo(a.getValue())); // Sort in descending order
        return sortedTokens;
    }
    
    private static void writeSortedTokensToFile(List<Map.Entry<String, Integer>> sortedTokens, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, Integer> entry : sortedTokens) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
        }
    }
}