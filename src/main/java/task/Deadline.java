package task;

/**
 * A task with a deadline.
 */
public class Deadline extends Task {

    /**
     * The deadline of the task.
     */
    protected String by;

    /**
     * Constructs a new deadline.
     *
     * @param description Description of the task.
     * @param by Deadline of the task.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getRecreateCommand(int id) {
        String result = "deadline " + description + " /by " + by;
        if (isDone) {
            result += "\nmark " + id;
        }
        return result;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}