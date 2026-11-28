// One expense record.
public class Expense {
    private String description;
    private String category;
    private double amount;
    private String date;   // YYYY-MM-DD

    public Expense(String description, String category, double amount, String date) {
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
}