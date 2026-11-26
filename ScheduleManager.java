import java.util.ArrayList;

// Shows the list of menu where student can modify their schedule.
public class ScheduleManager {
    private ArrayList<Schedule> schedules = new ArrayList<>();

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void menu() {
        int choice;
        do {
            System.out.println("\nSchedule Management");
            System.out.println("[1] Add Schedule");
            System.out.println("[2] View Schedules");
            System.out.println("[3] Update Schedule");
            System.out.println("[4] Delete Schedule");
            System.out.println("[5] Return to Main Menu");
            choice = InputHelper.readInt("Choose an option: ", 1, 5);

            switch (choice) {
                case 1: addSchedule(); break;
                case 2: viewSchedules(); break;
                case 3: updateSchedule(); break;
                case 4: deleteSchedule(); break;
            }
        } while (choice != 5);
    }

    private void addSchedule() {
        int type = InputHelper.readInt("Schedule type (1 = Class, 2 = Work): ", 1, 2);
        String title = InputHelper.readText("Title: ");
        String date = InputHelper.readDate("Date (YYYY-MM-DD): ");
        String start = InputHelper.readTime("Start time (HH:MM): ");
        String end = readEndTime(start);

        Schedule newSchedule;
        if (type == 1) {
            newSchedule = new ClassSchedule(title, date, start, end);
        } else {
            newSchedule = new WorkSchedule(title, date, start, end);
        }

        Schedule conflict = findConflict(newSchedule, null);
        boolean save;
        if (conflict != null) {
            System.out.println("\nWARNING: This conflicts with:");
            conflict.displaySchedule();
            save = InputHelper.readYesNo("Save anyway? [Y/N]: ");
        } else {
            save = InputHelper.readYesNo("Save Sched[Y/N]: ");
        }

        if (save) {
            schedules.add(newSchedule);
            System.out.println("Schedule Saved!");
        } else {
            System.out.println("Schedule not saved.");
        }
    }

    private void viewSchedules() {
        if (schedules.isEmpty()) {
            System.out.println("\nNo schedules yet.");
            return;
        }
        System.out.println();
        for (int i = 0; i < schedules.size(); i++) {
            System.out.print((i + 1) + ". ");
            schedules.get(i).displaySchedule();
        }
    }

    private void updateSchedule() {
        int index = pickSchedule("update");
        if (index == -1) {
            return;
        }
        Schedule s = schedules.get(index);

        // Store old values in case the user wants to cancel the update
        String oldTitle = s.getTitle();
        String oldDate = s.getDate();
        String oldStart = s.getStartTime();
        String oldEnd = s.getEndTime();

        String title = InputHelper.readText("New title: ");
        String date = InputHelper.readDate("New date (YYYY-MM-DD): ");
        String start = InputHelper.readTime("New start time (HH:MM): ");
        String end = readEndTime(start);
        s.updateSchedule(title, date, start, end);

        Schedule conflict = findConflict(s, s);
        if (conflict != null) {
            System.out.println("\nWARNING: The updated schedule conflicts with:");
            conflict.displaySchedule();
            if (!InputHelper.readYesNo("Keep the update anyway? [Y/N]: ")) {
                s.updateSchedule(oldTitle, oldDate, oldStart, oldEnd);
                System.out.println("Update cancelled.");
                return;
            }
        }
        System.out.println("Schedule updated!");
    }

    private void deleteSchedule() {
        int index = pickSchedule("delete");
        if (index == -1) {
            return;
        }
        if (InputHelper.readYesNo("Are you sure? [Y/N]: ")) {
            schedules.remove(index);
            System.out.println("Schedule deleted.");
        }
    }

    // User can choose a schedule and return its index, or return -1 if the user enters -1
    private int pickSchedule(String action) {
        if (schedules.isEmpty()) {
            System.out.println("\nNo schedules yet.");
            return -1;
        }
        viewSchedules();
        int number = InputHelper.readInt("Enter number to " + action + ": ", 1, schedules.size());
        return number - 1;
    }

    // the code will keep asking if the end time is earlier than the start time
    private String readEndTime(String start) {
        while (true) {
            String end = InputHelper.readTime("End time (HH:MM): ");
            if (end.compareTo(start) > 0) {
                return end;
            }
            System.out.println("End time must be later than the start time.");
        }
    }

    // Looks for a schedule that conflicts with the given one.
    // "ignore" is the schedule itself when updating (so it doesn't conflict with itself).
    private Schedule findConflict(Schedule check, Schedule ignore) {
        for (Schedule s : schedules) {
            if (s != ignore && check.conflictsWith(s)) {
                return s;
            }
        }
        return null;
    }
}
