// Records the income of the user
public class Income {
    private String description;
    private double amount;
    private String date;  

    public Income(String description, double amount, String date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
}
