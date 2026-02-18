package saitama.task;

import java.time.LocalDate;

/**
 * Represents a generic task in the Saitama Sensei application.
 * A task contains a description and a status indicating whether it is completed.
 */
public class Task {
    public String description;
    public boolean isDone;

    /**
     * Constructs a new Task with the given description.
     * The task is initialized as not done by default.
     *
     * @param description The text description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     * "X" indicates the task is done, while a space " " indicates it is pending.
     *
     * @return A string representing the status icon.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Checks if the task is relevant to the given date.
     * Default implementation returns false.
     */
    public boolean isOnDate(LocalDate date) {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Task)) return false;
        Task other = (Task) obj;
        return this.description.equalsIgnoreCase(other.description);
    }

    /**
     * Returns a string representation of the task, including its status icon and description.
     *
     * @return A formatted string representing the task.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }
}