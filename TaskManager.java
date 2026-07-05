import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final String FILE_NAME = "tasks.txt";

    public void saveTask(String date, String description) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(date + " - " + description);
        } catch (IOException e) {
            System.err.println("Error saving task: " + e.getMessage());
        }
    }

    public List<String> getAllTasks() {
        List<String> tasks = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return tasks;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                tasks.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading tasks: " + e.getMessage());
        }
        return tasks;
    }

    // Rewrites file omitting the specific deleted item index line
    public void removeTaskAtIndex(int index) {
        List<String> tasks = getAllTasks();
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            // Rewrite configuration file fresh
            try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME, false))) {
                for (String t : tasks) {
                    out.println(t);
                }
            } catch (IOException e) {
                System.err.println("Error rewriting task vector matrix data file: " + e.getMessage());
            }
        }
    }

    public void clearAllTasks() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }
}