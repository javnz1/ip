package saitama.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task, which includes a specific date and time by which the task must be finished.
 */
public class Deadline extends Task {
    public LocalDateTime by;

    /**
     * Constructs a Deadline task with a description and a deadline string.
     *
     * @param description The description of the deadline.
     * @param by The deadline date/time string in the format "yyyy-MM-dd HHmm".
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDateTime.parse(by.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    /**
     * Returns a string representation of the Deadline task with its type identifier [D]
     * and the formatted deadline date.
     *
     * @return A formatted string representing the deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy h.mma")) + ")";
    }
}
