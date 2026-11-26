import java.time.LocalDate;
import java.util.ArrayList;

// Handles the Task Management menu and the list of tasks.
public class TaskManager {
    private ArrayList<Task> tasks = new ArrayList<>();

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void menu() {
        int choice;
        do {
            System.out.println("\nTask Management");
            System.out.println("[1] Add Task");
            System.out.println("[2] View Tasks");
            System.out.println("[3] Search Task");
            System.out.println("[4] Update Task");
            System.out.println("[5] Mark as Completed");
            System.out.println("[6] Change Priority");
            System.out.println("[7] Delete Task");
            System.out.println("[8] Return to Main Menu");
            choice = InputHelper.readInt("Choose an option: ", 1, 8);

            switch (choice) {
                case 1: addTask(); break;
                case 2: viewTasks(); break;
                case 3: searchTask(); break;
                case 4: updateTask(); break;
                case 5: markCompleted(); break;
                case 6: changePriority(); break;
                case 7: deleteTask(); break;
            }
        } while (choice != 8);
    }

    private String readPriority() {
        System.out.println("Priority: [1] High  [2] Medium  [3] Low");
        int p = InputHelper.readInt("Choose priority: ", 1, 3);
        if (p == 1) return "High";
        if (p == 2) return "Medium";
        return "Low";
    }

    private void addTask() {
        String name = InputHelper.readText("Task name: ");
        String description = InputHelper.readText("Description: ");
        String deadline = InputHelper.readDate("Deadline (YYYY-MM-DD): ");
        String priority = readPriority();

        tasks.add(new Task(name, description, deadline, priority));
        System.out.println("Task added!");
    }

    // Shows if the task is overdue if it is not completed and the deadline has passed.
    private String statusToShow(Task t) {
        String today = LocalDate.now().toString();
        if (!t.isCompleted() && t.getDeadline().compareTo(today) < 0) {
            return "Overdue";
        }
        return t.getStatus();
    }

    private void printTable(ArrayList<Task> list) {
        System.out.printf("%-4s %-22s %-12s %-10s %-10s%n", "No.", "Task", "Deadline", "Priority", "Status");
        for (int i = 0; i < list.size(); i++) {
            Task t = list.get(i);
            System.out.printf("%-4d %-22s %-12s %-10s %-10s%n",
                    i + 1, t.getTaskName(), t.getDeadline(), t.getPriority(), statusToShow(t));
        }
    }

    private void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\nNo tasks yet.");
            return;
        }

        System.out.println("\nView by: [1] All  [2] Pending only  [3] Completed only  [4] High priority only");
        int filter = InputHelper.readInt("Choose an option: ", 1, 4);

        ArrayList<Task> shown = new ArrayList<>();
        for (Task t : tasks) {
            if (filter == 1
                    || (filter == 2 && !t.isCompleted())
                    || (filter == 3 && t.isCompleted())
                    || (filter == 4 && t.getPriority().equals("High"))) {
                shown.add(t);
            }
        }

        if (shown.isEmpty()) {
            System.out.println("No tasks match that filter.");
            return;
        }
        System.out.println();
        printTable(shown);
        InputHelper.pressEnter("\nPress Enter to continue...");
    }

    private void searchTask() {
        String keyword = InputHelper.readText("Enter task name to search: ").toLowerCase();
        boolean found = false;
        for (Task t : tasks) {
            if (t.getTaskName().toLowerCase().contains(keyword)) {
                System.out.println();
                t.displayTask();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching task found.");
        }
    }

    private void updateTask() {
        int index = pickTask("update");
        if (index == -1) {
            return;
        }
        String name = InputHelper.readText("New task name: ");
        String description = InputHelper.readText("New description: ");
        String deadline = InputHelper.readDate("New deadline (YYYY-MM-DD): ");
        String priority = readPriority();

        tasks.get(index).updateTask(name, description, deadline, priority);
        System.out.println("Task updated!");
    }

    private void markCompleted() {
        int index = pickTask("mark as completed");
        if (index == -1) {
            return;
        }
        tasks.get(index).markCompleted();
        System.out.println("Task marked as completed!");
    }

    private void changePriority() {
        int index = pickTask("change the priority of");
        if (index == -1) {
            return;
        }
        tasks.get(index).setPriority(readPriority());
        System.out.println("Priority changed!");
    }

    private void deleteTask() {
        int index = pickTask("delete");
        if (index == -1) {
            return;
        }
        if (InputHelper.readYesNo("Are you sure? [Y/N]: ")) {
            tasks.remove(index);
            System.out.println("Task deleted.");
        }
    }

    // Shows the table of tasks and asks the user to pick one by number for the given action. Returns -1 if there are no tasks.
    private int pickTask(String action) {
        if (tasks.isEmpty()) {
            System.out.println("\nNo tasks yet.");
            return -1;
        }
        System.out.println();
        printTable(tasks);
        int number = InputHelper.readInt("Enter task number to " + action + ": ", 1, tasks.size());
        return number - 1;
    }
}