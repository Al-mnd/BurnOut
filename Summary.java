import java.time.LocalDate;
import java.util.ArrayList;

// Generates the overall summary of tasks, schedules, and finances for a given date range.
public class Summary {

    public static void generateSummary(ArrayList<Schedule> schedules, ArrayList<Task> tasks,
                                       FinanceManager finance) {
        System.out.println("\nSummary Type");
        System.out.println("[1] Weekly");
        System.out.println("[2] Monthly");
        System.out.println("[3] All records");
        int choice = InputHelper.readInt("Choose an option: ", 1, 3);

        // every summary have a start dat and end date.
        String startDate;
        String endDate;
        String label;

        if (choice == 1) {
            startDate = InputHelper.readDate("Week start date (YYYY-MM-DD): ");
            endDate = LocalDate.parse(startDate).plusDays(6).toString();
            label = "Week of " + startDate + " to " + endDate;
        } else if (choice == 2) {
            String month = InputHelper.readMonth("Month (YYYY-MM): ");
            startDate = month + "-01";
            endDate = month + "-31";
            label = "Month of " + month;
        } else {
            startDate = "0000-01-01";
            endDate = "9999-12-31";
            label = "All records";
        }

        int completed = 0;
        int pending = 0;
        String nextDeadline = "";
        String nextTaskName = "";
        for (Task t : tasks) {
            if (inRange(t.getDeadline(), startDate, endDate)) {
                if (t.isCompleted()) {
                    completed++;
                } else {
                    pending++;
                    if (nextDeadline.isEmpty() || t.getDeadline().compareTo(nextDeadline) < 0) {
                        nextDeadline = t.getDeadline();
                        nextTaskName = t.getTaskName();
                    }
                }
            }
        }

        int classCount = 0;
        int workCount = 0;
        for (Schedule s : schedules) {
            if (inRange(s.getDate(), startDate, endDate)) {
                if (s.getType().equals("CLASS")) {
                    classCount++;
                } else {
                    workCount++;
                }
            }
        }

        System.out.println("\nSummary - " + label);
        System.out.println("\nTasks");
        System.out.println("Completed     : " + completed);
        System.out.println("Pending       : " + pending);
        if (nextDeadline.isEmpty()) {
            System.out.println("Next deadline : None");
        } else {
            System.out.println("Next deadline : " + nextTaskName + " (" + nextDeadline + ")");
        }

        System.out.println("\nSchedules");
        System.out.println("Class schedules : " + classCount);
        System.out.println("Work schedules  : " + workCount);

        finance.generateSummary(startDate, endDate);

        InputHelper.pressEnter("\nPress Enter to return to the main menu...");
    }

    private static boolean inRange(String date, String startDate, String endDate) {
        return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
    }
}
