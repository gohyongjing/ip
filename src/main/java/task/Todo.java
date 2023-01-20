package task;

/**
 * A simple todo task.
 */
public class Todo extends Task {

    /**
     * Constructs a new task.
     *
     * @param description Description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}