package services.tasklist;

import command.CommandType;
import services.Format;
import services.bizerrors.EmptyArgumentException;
import services.bizerrors.InvalidArgumentException;
import services.bizerrors.JarvisException;
import services.tasklist.tasks.Deadline;
import services.tasklist.tasks.Event;
import services.tasklist.tasks.Task;
import services.tasklist.tasks.Todo;

import java.util.ArrayList;

public class List {
    private static ArrayList<Task> taskList = new ArrayList<>();
    private static int taskCount = 0;

    public static void add(String description, CommandType taskType, String... args) throws JarvisException {
        Task newTask;
        // this if block is unnecessary currently (is never reached), but it may be useful in the future.
        if (description.isEmpty()) {
            throw new EmptyArgumentException(taskType.toString().toLowerCase());
        }
        switch (taskType) {
            case TODO:
                newTask = new Todo(description);
                break;
            case DEADLINE:
                newTask = new Deadline(description, args[0]);
                break;
            case EVENT:
                newTask = new Event(description, args[0], args[1]);
                break;
            default:
                // the program should never reach this point.
                throw new JarvisException("Default case reached.");
        }
        taskList.add(newTask);
        taskCount++;
        Format.print("added: " + newTask + "\n" + taskCount + " more tasks to do, Sir.");
    }

    public static void markDone(int taskNumber) {
        Task task = taskList.get(taskNumber - 1);
        task.setDone();
        Format.print("Check.\n\t" + task + "\n" + "Way to go, sir.");
    }

    public static void markUndone(int taskNumber) {
        Task task = taskList.get(taskNumber - 1);
        task.setUndone();
        Format.print("As you wish, sir.\n\t" + task);
    }

    public static void show() {
        if (taskCount == 0) {
            Format.print("Sir, there are no tasks on your calendar.");
            return;
        }
        String result = "Sir, there are " + taskCount + " tasks on your calendar:\n";
        for (int i = 1; i < taskCount; i++) {
            result += i + ". " + taskList.get(i - 1) + "\n";
        }
        result += taskCount + ". " + taskList.get(taskCount - 1);
        Format.print(result);
    }
}
