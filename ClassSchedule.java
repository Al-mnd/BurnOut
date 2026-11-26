// Class schedule of a student.
public class ClassSchedule extends Schedule {

    public ClassSchedule(String title, String date, String startTime, String endTime) {
        super(title, date, startTime, endTime);
    }

    @Override
    public String getType() {
        return "CLASS";
    }

    @Override
    public void displaySchedule() {
        System.out.print("[Class] ");
        super.displaySchedule();
    }
}
