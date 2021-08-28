package duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String DATA_FOLDER_PATH;
    private final String DATA_FILE_PATH;

    public Storage(String data_folder_path, String data_file_path) {
        this.DATA_FOLDER_PATH = data_folder_path;
        this.DATA_FILE_PATH = data_file_path;
    }

    public ArrayList<Task> load() throws DukeException {
        File directory = new File(DATA_FOLDER_PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File f;
        try {
            f = new File(DATA_FILE_PATH);
            if (!f.exists()) {
                f.createNewFile();
            }
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }

        Scanner s;
        try {
           s = new Scanner(f); // create a Scanner using the File as the source
        } catch (FileNotFoundException e) {
            throw new DukeException(e.getMessage());
        }
        ArrayList<Task> tasks = new ArrayList<>();
        while (s.hasNext()) {
            String l = s.nextLine();
            String[] TaskEntry = l.split("\\|");
            switch(TaskEntry[0]) {
                case "T":
                    tasks.add(new Todo(TaskEntry[2]));
                    break;
                case "D":
                    tasks.add(new Deadline(TaskEntry[2], LocalDate.parse(TaskEntry[3])));
                    break;
                case "E":
                    tasks.add(new Event(TaskEntry[2], LocalDate.parse(TaskEntry[3])));
                    break;
                default:
                    throw new DukeException("Invalid duke.Task Type stored in Data File");
            }
            if (TaskEntry[1].equals("X")) {
                tasks.get(tasks.size() - 1).markAsDone();
            }
        }
        return tasks;
    }

    public void updateTasks(TaskList tasks) throws DukeException {
        try {
            FileWriter fw = new FileWriter(DATA_FILE_PATH);
            for (int i = 0; i < tasks.getLength(); i++) {
                fw.write(tasks.getTask(i).toStringData() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new DukeException("Error writing data file:" + e.getMessage());
        }
    }
}
