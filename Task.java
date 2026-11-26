// Represents one academic or work-related task.
public class Task {
    private String taskName;
    private String description;
    private String deadline;   // YYYY-MM-DD
    private String priority;   // High, Medium, or Low
    private String status;     // Pending or Completed

    public Task(String taskName, String description, String deadline, String priority) {
        this.taskName = taskName;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.status = "Pending";
    }

    // Used when loading from the file.
    public Task(String taskName, String description, String deadline, String priority, String status) {
        this(taskName, description, deadline, priority);
        this.status = status;
    }

    public String getTaskName() { return taskName; }
    public String getDescription() { return description; }
    public String getDeadline() { return deadline; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }

    public void displayTask() {
        System.out.println("Task        : " + taskName);
        System.out.println("Description: " + description);
        System.out.println("Deadline    : " + deadline);
        System.out.println("Priority    : " + priority);
        System.out.println("Status      : " + status);
    }

    public void updateTask(String taskName, String description, String deadline, String priority) {
        this.taskName = taskName;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
    }

    public void markCompleted() {
        status = "Completed";
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return status.equals("Completed");
    }
}