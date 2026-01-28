import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void save(ArrayList<Task> taskList) {
        try {
            File f = new File(filePath);
            f.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(f);

            for (Task t: taskList) {
                fw.write(fileFormat(t) + System.lineSeparator());
            }

            fw.close();
        } catch (IOException e) {
            System.out.println("⚠ Sensei encountered an error saving: " + e.getMessage());
        }
    }

    public ArrayList<Task> load() {
        ArrayList<Task> taskList = new ArrayList<>();
        File f = new File(filePath);

        if (!f.exists()) {
            return taskList;
        }

        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNext()) {
                String line = sc.nextLine();
                Task task = lineToTask(line);
                if (task != null) {
                    taskList.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Sensei couldn't load the file: " + e.getMessage());
        }
        return taskList;
    }

    public String fileFormat (Task t) {
        String type = (t instanceof ToDos) ? "T" : (t instanceof Deadline) ? "D" : "E";
        int isDone = t.isDone ? 1 : 0;
        String base = type + " | " + isDone + " | " + t.description;

        if (t instanceof Deadline) {
            return base + " | " + ((Deadline) t).by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        } else if (t instanceof Events) {
            return base + " | " + ((Events) t).from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " | " + ((Events) t).to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return base;
    }

    public Task lineToTask(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        Task t;

        switch (type) {
            case "T":
                t = new ToDos(description);
                break;
            case "D":
                t = new Deadline(description, parts[3]);
                break;
            case "E":
                t = new Events(description, parts[3], parts[4]);
                break;
            default:
                return null;
        }

        if (isDone)
            t.markAsDone();

        return t;
    }
}
