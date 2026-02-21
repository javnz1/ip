package saitama.task;

import java.time.LocalDate;
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
        this.by = LocalDateTime.parse(by.trim(),
                DateTimeFormatter.ofPattern("dd-MM-uuuu HHmm")
                .withResolverStyle(java.time.format.ResolverStyle.STRICT));
    }

    /**
     * Checks if the task is relevant to the given date.
     *  Default implementation returns false.
     *
     * @return A boolean representing whether a task is in or within the given date
     */
    @Override
    public boolean isOnDate(LocalDate date) {
        return this.by.toLocalDate().equals(date);
    }

    /**
     * Compares this deadline with another object for equality.
     * Two deadlines are considered equal if they have the same description
     * and the exact same deadline date and time.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the objects are equal; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj) && obj instanceof Deadline) {
            return this.by.equals(((Deadline) obj).by);
        }
        return false;
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
