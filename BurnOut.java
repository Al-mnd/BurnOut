// The start of our system
public class BurnOut {
    public static void main(String[] args) {
        User user = new User();
        ScheduleManager scheduleManager = new ScheduleManager();
        TaskManager taskManager = new TaskManager();
        FinanceManager financeManager = new FinanceManager();
        FileManager fileManager = new FileManager("burnout_data.txt");

        System.out.println("BurnOut\n");
        System.out.println("Loading Saved Data...");
        fileManager.loadData(user, scheduleManager.getSchedules(), taskManager.getTasks(),
                financeManager.getIncomeRecords(), financeManager.getExpenseRecords());

        if (!user.hasProfile()) {
            System.out.println("No profile found. Set up one first.\n");
            user.updateProfile();
            System.out.println("\nProfile Saved! Welcome " + user.getName() + "!");
        } else {
            System.out.println("Welcome back, " + user.getName() + "!");
        }

        int choice;
        do {
            System.out.println("\nMain Menu");
            System.out.println("[1] Schedule Management");
            System.out.println("[2] Task Management");
            System.out.println("[3] Finance Management");
            System.out.println("[4] Weekly/Monthly Summary");
            System.out.println("[5] User Profile");
            System.out.println("[6] Exit");
            choice = InputHelper.readInt("Choose an option: ", 1, 6);

            switch (choice) {
                case 1:
                    scheduleManager.menu();
                    break;
                case 2:
                    taskManager.menu();
                    break;
                case 3:
                    financeManager.menu();
                    break;
                case 4:
                    Summary.generateSummary(scheduleManager.getSchedules(), taskManager.getTasks(), financeManager);
                    break;
                case 5:
                    user.displayProfile();
                    if (InputHelper.readYesNo("Update profile? [Y/N]: ")) {
                        user.updateProfile();
                        System.out.println("Profile updated!");
                    }
                    break;
            }

            // Saving so no data is lost
            if (choice != 6) {
                fileManager.saveData(user, scheduleManager.getSchedules(), taskManager.getTasks(),
                        financeManager.getIncomeRecords(), financeManager.getExpenseRecords());
            }
        } while (choice != 6);

        System.out.println("\nSaving data...");
        boolean saved = fileManager.saveData(user, scheduleManager.getSchedules(), taskManager.getTasks(),
                financeManager.getIncomeRecords(), financeManager.getExpenseRecords());
        if (saved) {
            System.out.println("Data saved successfully. Thank You! " + user.getName() + "!");
        }
    }
}