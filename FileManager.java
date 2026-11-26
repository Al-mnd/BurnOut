import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

// Saves and loads all records using one text file.
public class FileManager {
    private String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    public boolean saveData(User user, ArrayList<Schedule> schedules, ArrayList<Task> tasks,
                            ArrayList<Income> incomes, ArrayList<Expense> expenses) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("USER|" + user.getName() + "|" + user.getProgram() + "|"
                    + user.getSchool() + "|" + user.getWorkplace());

            for (Schedule s : schedules) {
                writer.println(s.getType() + "|" + s.getTitle() + "|" + s.getDate() + "|"
                        + s.getStartTime() + "|" + s.getEndTime());
            }
            for (Task t : tasks) {
                writer.println("TASK|" + t.getTaskName() + "|" + t.getDescription() + "|"
                        + t.getDeadline() + "|" + t.getPriority() + "|" + t.getStatus());
            }
            for (Income i : incomes) {
                writer.println("INCOME|" + i.getDescription() + "|" + i.getAmount() + "|" + i.getDate());
            }
            for (Expense e : expenses) {
                writer.println("EXPENSE|" + e.getDescription() + "|" + e.getCategory() + "|"
                        + e.getAmount() + "|" + e.getDate());
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
            return false;
        }
    }

    public void loadData(User user, ArrayList<Schedule> schedules, ArrayList<Task> tasks,
                         ArrayList<Income> incomes, ArrayList<Expense> expenses) {
        File file = new File(fileName);
        if (!file.exists()) {
            return;  
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] p = line.split("\\|");

                    if (p[0].equals("USER")) {
                        user.setName(p[1]);
                        user.setProgram(p[2]);
                        user.setSchool(p[3]);
                        user.setWorkplace(p[4]);
                    } else if (p[0].equals("CLASS")) {
                        schedules.add(new ClassSchedule(p[1], p[2], p[3], p[4]));
                    } else if (p[0].equals("WORK")) {
                        schedules.add(new WorkSchedule(p[1], p[2], p[3], p[4]));
                    } else if (p[0].equals("TASK")) {
                        tasks.add(new Task(p[1], p[2], p[3], p[4], p[5]));
                    } else if (p[0].equals("INCOME")) {
                        incomes.add(new Income(p[1], Double.parseDouble(p[2]), p[3]));
                    } else if (p[0].equals("EXPENSE")) {
                        expenses.add(new Expense(p[1], p[2], Double.parseDouble(p[3]), p[4]));
                    }
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                    System.out.println("Skipped a damaged line in the data file.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}