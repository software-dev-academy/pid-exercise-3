package se.kth.sda;

import com.google.gson.Gson;
import se.kth.sda.attendance.AttendanceSheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Start {

    static final Scanner scanner = new Scanner(System.in);

    public enum Option {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY
    }

    static String datePattern = "dd-MM-yyyy";
    static DateFormat dateFormat = new SimpleDateFormat(datePattern);


    public static void printWelcomeText() {
        System.out.println("In this program you can take attendance of people in a sheet. This sheet can be read from a " +
                "json document or you can choose to add people manually. You can also load a list of people from json to a sheet and add " +
                "people to that sheet.");
    }

    public static void printMainMenuCommands() {
        System.out.println("Here are the commands you can use:");
        System.out.println("");
        System.out.println("'load' to load a member list into an attendance sheet");
        System.out.println("'edit' to edit a member list");
        System.out.println("'new' to create a new member list");
        System.out.println("'display' to display an attendance sheet given a date");
        System.out.println("'help' to see the available commands again");
        System.out.println("'exit' to exit the program");
        System.out.println("");
        System.out.println("Tip: to start taking attendance either load a list or create a new one.");
    }

    public static void main(String[] args) {
        System.out.println("Hello and welcome to the attendance sheet program");
        printWelcomeText();

        boolean stop = false;
        while (!stop) {
            printMainMenuCommands();
            String line = scanner.nextLine();

            if (line.equals("load")) {
                loadMemberFileAndTakeAttendance();

            } else if (line.equals("edit")) {
                editMemberList();
            } else if (line.equals("new")) {
                newMemberList();
            } else if (line.equals("display")) {
                displayAttendanceSheet();
            } else if (line.equals("help")) {
                printWelcomeText();
                printMainMenuCommands();
            } else if (line.equals("exit")) {
                System.out.println("Thanks for using the attendance sheet program!");
                System.exit(0);
            }
        }
    }

    private static void displayAttendanceSheet() {

        System.out.println("Here are the five latest attendance sheets:");
        List<String> files = getFilesThatFollowFormat();

        System.out.println("Write a date corresponding to an attendance sheet: ");
        String answer = scanner.nextLine().trim();

        String fileName = "attendance-sheets/" + answer + ".json";
        AttendanceSheet attendanceSheet = loadJsonFile(AttendanceSheet.class, fileName);

        if (attendanceSheet != null) {
            attendanceSheet.print();
        } else {
            System.out.println("Date does not match any attendance sheet.");
        }
    }


    private static boolean isDateStringValidFormat(String value) {
        Date date = null;
        try {
            date = dateFormat.parse(value);
            if (!value.equals(dateFormat.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

    public static List<String> getFilesThatFollowFormat() {
        try (Stream<Path> walk = Files.walk(Paths.get("attendance-sheets"))) {

            List<String> result = walk.map(x -> x.toString())
                    .filter(f -> f.endsWith(".json"))
                    .map(f -> f.substring(0, f.length() - 5))
                    .map(f -> f.replaceFirst("attendance-sheets/", ""))
                    .filter(f -> isDateStringValidFormat(f))
                    .collect(Collectors.toList());

            result.forEach(System.out::println);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }

    private static void newMemberList() {
        MemberList memberList = new MemberList();
        memberList.edit();
        memberList.saveToFile();
    }

    public static void loadMemberFileAndTakeAttendance() {
        MemberList memberList = askInputAndLoadJsonFile(MemberList.class, "member-lists/");
        System.out.println("gson:");
        System.out.println(new Gson().toJson(memberList));
        if (memberList != null) {
            AttendanceSheet attendanceSheet = new AttendanceSheet(memberList);
            addPeopleToAttendanceSheet(attendanceSheet);
            attendanceSheet.takeAttendance(scanner);
            saveAttendanceSheet(attendanceSheet);
        }

    }

    public static void editMemberList() {
        MemberList memberList = askInputAndLoadJsonFile(MemberList.class, "member-lists/");
        memberList.edit();
        memberList.saveToFile();
    }

    public static void addPeopleToAttendanceSheet(AttendanceSheet sheet) {
        boolean writeName = true;
        System.out.println("You can now add people to the sheet if you want to.");
        while (writeName) {
            System.out.println("Write the name of a person [First name Last name] you want to add to the list or write '#' to continue.");
            String name = scanner.nextLine();
            if (!name.equals("#")) {
                sheet.addPerson(name);
                System.out.println("Added!");
            } else {
                writeName = false;
            }
        }
    }

    public static void saveAttendanceSheet(AttendanceSheet sheet) {
        String answer = askYesNo("Do you want to save your attendance sheet? [y/n]");
        if (answer.equals("y")) {
            sheet.saveToFile();
        }
    }

    public static String askYesNo(String text) {
        boolean acceptableAnswer = false;
        String answer = "";
        while (!acceptableAnswer) {
            System.out.println(text);
            answer = Start.scanner.nextLine();
            if (answer.equals("y")) {
                acceptableAnswer = true;
            } else if (answer.equals("n")) {
                acceptableAnswer = true;
            } else {
                System.out.println("Please answer with 'y' or 'n' (yes or no)");
                acceptableAnswer = false;
            }
        }
        return answer;
    }

    private static <T> T askInputAndLoadJsonFile(Class<T> tClass, String pathName) {
        return askInputAndLoadJsonFile(tClass, tClass.getName(), pathName);
    }

    private static <T> T askInputAndLoadJsonFile(Class<T> tClass, String fileHint, String pathName) {
        System.out.println("Write the name of your " + fileHint + " file.");
        String fileName = pathName + scanner.nextLine();
        return loadJsonFile(tClass, fileName);
    }

    private static <T> T loadJsonFile(Class<T> tClass, String fileName) {
        String fileContent = null;
        try {
            fileContent = new Scanner(new File(fileName)).useDelimiter("\\Z").next();
            Gson gson = new Gson();
            T jsonObject = gson.fromJson(fileContent, tClass);
            return jsonObject;

        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found, make sure it exists!");
        }
        return null;
    }
}
