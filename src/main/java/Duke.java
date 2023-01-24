import command.Command;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import command.Parser;
import storage.Storage;

import task.Task;
import task.TaskList;

/**
 * Duke command line tool that helps to track tasks.
 */
public class Duke {
    /** Whether the duke is running or has been commanded to stop running */
    private boolean isRunning;

    /** Storage to load and save tasks on disk */
    private final Storage storage;

    /** Task list to store tasks in memory */
    private TaskList tasks;

    /**
     * Constructs a duke.
     */
    public Duke() {
        storage = new Storage("./data/tasks.txt");
        isRunning = false;
        System.out.println("Hello!");
        try {
            tasks = new TaskList(storage.getScanner());
        } catch (FileNotFoundException e) {
            System.out.println("Task list not found on disk, creating empty task list");
            tasks = new TaskList();
        }
    }

    /**
     * Entry point to start and run duke.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Duke duke = new Duke();
        duke.run();
    }

    /**
     * Runs the duke.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        isRunning = true;
        System.out.println("Awaiting commands...");
        while (isRunning) {
            try {
                execute(Parser.parse(scanner.nextLine()));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
        exit();
    }

    /**
     * Executes a command.
     *
     * @param command Command to execute.
     */
    private void execute(Command command) {
        switch (command.getName()) {
        case NO_OP:
            break;
        case BYE:
            isRunning = false;
            break;
        case TODO:
            // FallThrough
        case DEADLINE:
            // FallThrough
        case EVENT:
            System.out.println("Added task: " + tasks.execute(command));
            break;
        case LIST:
            System.out.println(tasks);
            break;
        case MARK:
            Task task = tasks.execute(command);
            System.out.println("Marked task: " + task + " as " + (task.getIsDone() ? "" : "not ") + "done");
            break;
        case DELETE:
            System.out.println("Deleted task: " + tasks.execute(command));
            break;
        }
    }

    /**
     * Cleans up the duke to exit.
     * Save tasks to the disk.
     */
    private void exit() {
        try {
            tasks.save(storage.getFileWriter());
        } catch (IOException e) {
            System.out.println("Error saving tasks to disk!");
        }
        System.out.println("Good bye!");
    }

}
