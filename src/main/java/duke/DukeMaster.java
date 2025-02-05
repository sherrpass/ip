package duke;

import java.util.Objects;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The DukeMaster Application.
 */
public class DukeMaster extends Application {
    private final String dataFilePath = "./data/tasks.txt";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public DukeMaster() {}

    @Override
    public void start(Stage stage) {
        ui = new Ui(stage);
        storage = new Storage(dataFilePath);
        tasks = generateTaskList();

        setEventHandlers();

        ui.showWelcome();
    }

    private TaskList generateTaskList() {
        try {
            return new TaskList(storage.load());
        } catch (DukeDataException e) {
            ui.showLoadingError(e.getMessage());
            return new TaskList();
        }
    }

    private void setEventHandlers() {
        ui.getSendButton().setOnMouseClicked((event) -> handleUserInput(ui.readCommand()));
        ui.getUserInput().setOnAction((event) -> handleUserInput(ui.readCommand()));
    }

    private void handleUserInput(String fullCommand) {
        try {
            ui.showFullCommand(fullCommand);
            ui.clearInput();
            Command c = Parser.parse(fullCommand);
            assert !Objects.isNull(c) : "Should always return a command, if no error thrown.";
            c.execute(tasks, storage, ui);
        } catch (DukeException e) {
            ui.showError(e.getMessage());
        }
    }
}
