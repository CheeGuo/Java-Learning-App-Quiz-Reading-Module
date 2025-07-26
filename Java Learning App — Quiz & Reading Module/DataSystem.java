
import java.io.*;
import java.util.*;
import javax.swing.*;

// This class handles file encryption, writing, and reading operations
public class DataSystem implements DataSystemInterface {

    // Encrypt a string using a basic custom cipher
    private String encrypt(String data) {
        String result = "";
        for (char s : data.toCharArray()) {
            int firstLayer = s ^ 'K';// XOR with 'K' as basic cipher key
            int left = firstLayer / 2;
            int right = firstLayer - left;
            left = (left - 1 + 94) % 94 + 32; // shift to printable ASCII
            right = (right + 1) % 94 + 32;
            result += (char) left;
            result += (char) right;
        }
        return result;
    }

    // Decrypt the encrypted string back to original form
    private String decrypt(String encryptedLine) {
        List<Character> encryptedData = new ArrayList<>();
        for (char ch : encryptedLine.toCharArray()) {
            encryptedData.add(ch);
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < encryptedData.size(); i += 2) {
            int left = (encryptedData.get(i) - 32 + 1 + 94) % 94;
            int right = (encryptedData.get(i + 1) - 32 - 1 + 94) % 94;
            int combined = left + right;
            int original = combined ^ 'K';
            result.append((char) original);
        }
        return result.toString();
    }

    // Write encrypted quiz history to file
    public void WriteFile(String data) {
        try {
            data = encrypt(data + "\n");  // Add newline after each record
            FileWriter writer = new FileWriter("history.txt", true);
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Write encrypted badge achievements to file
    public void WriteFileBadge(String data) {
        try {
            data = encrypt(data + "\n");  // Add newline after each record
            FileWriter writer = new FileWriter("Achievement.txt", true); // Fixed typo in filename
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Read and decrypt badge data from file
    public String ReadFileBadge() {
    String data = "";
        try {
            File file = new File("Achievement.txt");
            if (!file.exists()) {
                file.createNewFile(); // Create the file if it doesn't exist
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String decrypted = decrypt(line);
                data += decrypted + "\n";
            }
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return data;
    }

    // Read and decrypt quiz history from file
    public String ReadFile() {
        String data = "";
        try {
            File file = new File("history.txt");
            if (!file.exists()) {
                file.createNewFile(); // Create the file if it doesn't exist
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String decrypted = decrypt(line);
                data += decrypted + "\n";
            }
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return data;
    }
    
}
