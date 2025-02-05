package duke;

/**
 * Represents the delete task command.
 */
public class DeleteCommand extends Command {
    private int taskIndex;

    /**
     * Constructor for DeleteCommand
     *
     * @param taskIndex Index of task to delete.
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Removes task from task list, updates tasks in data file and shows that task has been removed on UI.
     *
     * @param tasks Task list component.
     * @param storage Storage component.
     * @param ui UI component.
     */
    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) {
        TaskList copyOfTaskListBeforeChange = tasks.clone();
        if (tasks.getLength() <= taskIndex || 0 > taskIndex) {
            throw new DukeException("Invalid task index provided!");
        }
        storage.addToHistory(tasks, this);
        Task removedTask = tasks.removeTask(taskIndex);
        assert !tasks.doesContain(removedTask) : "Should have removed new task from TaskList";
        storage.updateTasks(tasks);
        storage.addToHistory(copyOfTaskListBeforeChange, this);
        ui.showRemovedTask(removedTask, tasks);
    }

    @Override
    public String toString() {
        return "Delete Task Command";
    }
}
