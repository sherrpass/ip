package duke;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }

    public LocalDate getDueDate() {
        return by;
    }

    @Override
    public String toStringData() {
        return "D" + super.toStringData() + "|" + by;
    }

    @Override
    public boolean matches(String query) {
        return super.matches(query)
                || by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")).toLowerCase().contains(query.toLowerCase())
                || query.equalsIgnoreCase("deadline");
    }
}