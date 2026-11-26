import java.time.LocalDate;
import java.util.ArrayList;

// Handles income, expenses, balance, and the Finance Management menu.
public class FinanceManager {
    private ArrayList<Income> incomeRecords = new ArrayList<>();
    private ArrayList<Expense> expenseRecords = new ArrayList<>();

    public ArrayList<Income> getIncomeRecords() {
        return incomeRecords;
    }

    public ArrayList<Expense> getExpenseRecords() {
        return expenseRecords;
    }

    public void menu() {
        int choice;
        do {
            System.out.println("\nFinance Management");
            System.out.println("[1] Add Income");
            System.out.println("[2] Add Expense");
            System.out.println("[3] View Records");
            System.out.println("[4] View Balance");
            System.out.println("[5] Return to Main Menu");
            choice = InputHelper.readInt("Choose an option: ", 1, 5);

            switch (choice) {
                case 1: addIncome(); break;
                case 2: addExpense(); break;
                case 3: viewRecords(); break;
                case 4: viewBalance(); break;
            }
        } while (choice != 5);
    }

    public void addIncome() {
        String description = InputHelper.readText("Description (e.g. Salary): ");
        double amount = InputHelper.readDouble("Amount: ");
        incomeRecords.add(new Income(description, amount, LocalDate.now().toString()));
        System.out.println("Income recorded: " + description + " | " + money(amount));
    }

    public void addExpense() {
        String description = InputHelper.readText("Description: ");
        System.out.println("Category: [1] Food  [2] Transportation  [3] School  [4] Bills  [5] Others");
        int c = InputHelper.readInt("Choose category: ", 1, 5);
        String[] categories = {"Food", "Transportation", "School", "Bills", "Others"};
        String category = categories[c - 1];

        double amount = InputHelper.readDouble("Amount: ");
        expenseRecords.add(new Expense(description, category, amount, LocalDate.now().toString()));
        System.out.println("Expense recorded: " + description + " | " + category + " | " + money(amount));
    }

    private void viewRecords() {
        System.out.println("\nIncome");
        if (incomeRecords.isEmpty()) {
            System.out.println("  (none)");
        }
        for (Income i : incomeRecords) {
            System.out.println("  " + i.getDate() + " | " + i.getDescription() + " | " + money(i.getAmount()));
        }

        System.out.println("\nExpenses");
        if (expenseRecords.isEmpty()) {
            System.out.println("  (none)");
        }
        for (Expense e : expenseRecords) {
            System.out.println("  " + e.getDate() + " | " + e.getDescription() + " | "
                    + e.getCategory() + " | " + money(e.getAmount()));
        }
    }

    private void viewBalance() {
        System.out.println();
        System.out.println("Total income     : " + money(getTotalIncome("0000-01-01", "9999-12-31")));
        System.out.println("Total expenses   : " + money(getTotalExpenses("0000-01-01", "9999-12-31")));
        System.out.println("Remaining balance: " + money(calculateBalance()));
    }

    // Total income between two dates.
    public double getTotalIncome(String startDate, String endDate) {
        double total = 0;
        for (Income i : incomeRecords) {
            if (i.getDate().compareTo(startDate) >= 0 && i.getDate().compareTo(endDate) <= 0) {
                total += i.getAmount();
            }
        }
        return total;
    }

    // Total expenses between two dates.
    public double getTotalExpenses(String startDate, String endDate) {
        double total = 0;
        for (Expense e : expenseRecords) {
            if (e.getDate().compareTo(startDate) >= 0 && e.getDate().compareTo(endDate) <= 0) {
                total += e.getAmount();
            }
        }
        return total;
    }

    public double calculateBalance() {
        return getTotalIncome("0000-01-01", "9999-12-31") - getTotalExpenses("0000-01-01", "9999-12-31");
    }

    // Prints the finance part of a summary between two dates.
    public void generateSummary(String startDate, String endDate) {
        double income = getTotalIncome(startDate, endDate);
        double expenses = getTotalExpenses(startDate, endDate);

        System.out.println("\nFinances");
        System.out.println("Total income      : " + money(income));
        System.out.println("Total expenses    : " + money(expenses));
        System.out.println("Remaining balance : " + money(income - expenses));

        // Expenses are grouped by category for the summary.
        String[] categories = {"Food", "Transportation", "School", "Bills", "Others"};
        System.out.println("\nExpenses by category");
        for (String category : categories) {
            double total = 0;
            for (Expense e : expenseRecords) {
                if (e.getCategory().equals(category)
                        && e.getDate().compareTo(startDate) >= 0
                        && e.getDate().compareTo(endDate) <= 0) {
                    total += e.getAmount();
                }
            }
            System.out.println(String.format("%-15s: %s", category, money(total)));
        }
    }

    public static String money(double amount) {
        return String.format("PHP %,.2f", amount);
    }
}