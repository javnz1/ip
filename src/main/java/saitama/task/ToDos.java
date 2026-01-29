package saitama.task;

/**
 * Represents a Todo task, which is a basic task without any specific date or time.
 */
public class ToDos extends Task {
    /**
     * Constructs a Todo task with the specified description.
     *
     * @param description The description of the todo.
     */
    public ToDos(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the Todo task with its type identifier [T].
     *
     * @return A formatted string representing the todo task.
     */
    @Override
    public String toString(){
        return "[T]" + super.toString();
    }
}
