// A work shift schedule.
public class WorkSchedule extends Schedule {

    public WorkSchedule(String title, String date, String startTime, String endTime) {
        super(title, date, startTime, endTime);
    }

    @Override
    public String getType() {
        return "WORK";
    }

    @Override
    public void displaySchedule() {
        System.out.print("[Work]  ");
        super.displaySchedule();
    }
}