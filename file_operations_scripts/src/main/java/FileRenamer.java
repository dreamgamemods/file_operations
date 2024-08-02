import java.io.File;

public class FileRenamer {

    public static void main(String[] args) {
        String directoryPath = "F:\\Mods\\test";
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            renamePNGFiles(directory);
            System.out.println("PNG files renamed successfully.");
        } else {
            System.err.println("Invalid directory path.");
        }
    }

    private static void renamePNGFiles(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    renamePNGFiles(file); // Recursive call for subdirectories
                } else if (file.isFile() && file.getName().toLowerCase().endsWith(".png")) {
                    String folderName = directory.getName();
                    String newFileName = folderName + "_" + file.getName();
                    File newFile = new File(file.getParent(), newFileName);
                    if (file.renameTo(newFile)) {
                        System.out.println("Renamed: " + file.getName() + " -> " + newFileName);
                    } else {
                        System.err.println("Failed to rename: " + file.getName());
                    }
                }
            }
        }
    }
}