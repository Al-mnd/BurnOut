// SThis stores our users information and profile.
public class User {
    private String name;
    private String program;
    private String school;
    private String workplace;

    public User() {
        name = "";
        program = "";
        school = "";
        workplace = "";
    }
  
    public String getName() { return name; }
    public String getProgram() { return program; }
    public String getSchool() { return school; }
    public String getWorkplace() { return workplace; }

    public void setName(String name) { this.name = name; }
    public void setProgram(String program) { this.program = program; }
    public void setSchool(String school) { this.school = school; }
    public void setWorkplace(String workplace) { this.workplace = workplace; }

    public boolean hasProfile() {
        return !name.isEmpty();
    }

    public void displayProfile() {
        System.out.println("\nUser Profile");
        System.out.println("Name      : " + name);
        System.out.println("Program   : " + program);
        System.out.println("School    : " + school);
        System.out.println("Workplace : " + workplace);
    }

    public void updateProfile() {
        name = InputHelper.readText("Enter your name: ");
        program = InputHelper.readText("Enter your program: ");
        school = InputHelper.readText("Enter your school: ");
        workplace = InputHelper.readText("Enter your workplace: ");
    }
}