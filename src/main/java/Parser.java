import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Parser {
    public static Command parse(String input) throws DukeException {
        String[] inputList = input.split(" ");
        String action = inputList[0];
        try {
            switch (action) {
            case "list":
                return new ListCommand();
            case "delete":
                if (inputList.length != 2) {
                    throw new DukeException("Please provide the target task index!");
                }
                return new DeleteCommand(Integer.parseInt(inputList[1]));
            case "done":
                if (inputList.length != 2) {
                    throw new DukeException("Please provide the target task index!");
                }
                return new SetDoneCommand(Integer.parseInt(inputList[1]) - 1);
            case "todo":
                return new AddCommand("todo",
                        input.replaceFirst(Pattern.quote("todo"),"").trim(),
                        null);
            case "deadline":
                String[] deadlineInfo = input.replaceFirst(Pattern.quote("deadline"),"").split("/by", 2);
                return new AddCommand("deadline",
                        deadlineInfo[0].trim(),
                        LocalDate.parse(deadlineInfo[1].trim()));
            case "event":
                String[] eventInfo = input.replaceFirst(Pattern.quote("event"),"").split("/at", 2);
                return new AddCommand("event",
                        eventInfo[0].trim(),
                        LocalDate.parse(eventInfo[1].trim()));
            case "occurring":
                return new OccurringCommand(LocalDate.parse(inputList[1]));
            case "bye":
                return new ExitCommand();
            default:
                throw new DukeException("I'm sorry, but I don't know what that means :-(");
            }
        } catch (DateTimeParseException e) {
            throw new DukeException("Date provided should be in YYYY-MM-DD format.");
        }
    }
}
