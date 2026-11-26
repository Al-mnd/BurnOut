// Can't make a plain Schedule object directly
// Dates are in YYYY-MM-DD format, and times are in HH:MM format (24-hour clock).
public abstract class Schedule {
    private String title;
    private String date;
    private String startTime;
    private String endTime;

    public Schedule(String title, String date, String startTime, String endTime) {
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTitle() { return title; }
    public String getDate() { return date; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }

    public void setTitle(String title) { this.title = title; }
    public void setDate(String date) { this.date = date; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    // Abstract method that child classes must implement to return their type.
    public abstract String getType();

    // Child classes override this to add their own labels to the schedule display.
    public void displaySchedule() {
        System.out.println(title + " | " + date + " | " + startTime + " - " + endTime);
    }

    public void updateSchedule(String title, String date, String startTime, String endTime) {
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Two schedules conflict if they are on the same date and their times overlap.
    public boolean conflictsWith(Schedule other) {
        if (!date.equals(other.date)) {
            return false;
        }
        return startTime.compareTo(other.endTime) < 0
            && other.startTime.compareTo(endTime) < 0;
    }
}