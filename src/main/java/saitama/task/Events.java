package saitama.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task, which occurs during a specific time frame with a start and end date.
 */
public class Events extends Task {
    public LocalDate from;
    public LocalDate to;

    /**
     * Constructs an Event task with a description and a duration.
     *
     * @param description The description of the event.
     * @param from The start date in "dd-MM-yyyy" format.
     * @param to The end date in "dd-MM-yyyy" format.
     */
    public Events(String description, String from, String to) {
        super(description);
        this.from = LocalDate.parse(from.trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.to = LocalDate.parse(to.trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    /**
     * Checks if the task is relevant to the given date.
     *  Default implementation returns false.
     *
     * @return A boolean representing whether a task is in or within the given date
     */
    @Override
    public boolean isOnDate(LocalDate date) {
        return !date.isBefore(from) && !date.isAfter(to);
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj) && obj instanceof Events) {
            Events other = (Events) obj;
            return this.from.equals(other.from) && this.to.equals(other.to);
        }
        return false;
    }

    /**
     * Returns a string representation of the Event task with its type identifier [E]
     * and the formatted duration.
     *
     * @return A formatted string representing the event task.
     */
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                + " to: " + to.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
