import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

// Handles all the input made by the user.
public class InputHelper {
    private static Scanner scanner = new Scanner(System.in);

    // Reads a non-empty line of text. The character "|" is replaced with "/" 
    // because we use it to separate data in the text file.
    public static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty.");
            } else {
                return input.replace("|", "/");
            }
        }
    }

    // Reads a whole number between min and max.
    public static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int number = Integer.parseInt(scanner.nextLine().trim());
                if (number >= min && number <= max) {
                    return number;
                }
                System.out.println("Please enter a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    // Reads a positive decimal number (for money).
    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double number = Double.parseDouble(scanner.nextLine().trim());
                if (number > 0) {
                    return number;
                }
                System.out.println("Amount must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    // Reads a date in YYYY-MM-DD format. Also accepts 2026-9-5 and fixes it to 2026-09-05.
    public static String readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String[] parts = scanner.nextLine().trim().split("-");
                if (parts.length != 3) {
                    throw new DateTimeException("Wrong format");
                }
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                if (year < 1900 || year > 2200) {
                    throw new DateTimeException("Year out of range");
                }
                return LocalDate.of(year, month, day).toString();
            } catch (DateTimeException | NumberFormatException e) {
                System.out.println("Invalid date! Use the format YYYY-MM-DD (example: 2026-10-12).");
            }
        }
    }

    // Reads a time in 24 hour format. Also accepts 9:5 and fixes it to 09:05.
    public static String readTime(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                LocalTime time = LocalTime.parse(scanner.nextLine().trim());
                return time.toString();
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time! Use the format HH:MM (example: 09:00).");
            }
        }
    }

    // Reads a month in YYYY-MM format.
    public static String readMonth(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String[] parts = scanner.nextLine().trim().split("-");
                if (parts.length != 2) {
                    throw new DateTimeException("Wrong format");
                }
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                LocalDate.of(year, month, 1);   // throws an error if invalid
                return String.format("%04d-%02d", year, month);
            } catch (DateTimeException | NumberFormatException e) {
                System.out.println("Invalid month! Use the format YYYY-MM (example: 2026-10).");
            }
        }
    }

    // Reads Y or N.
    public static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y")) {
                return true;
            }
            if (input.equals("N")) {
                return false;
            }
            System.out.println("Please type Y or N.");
        }
    }

    public static void pressEnter(String message) {
        System.out.print(message);
        scanner.nextLine();
    }
}