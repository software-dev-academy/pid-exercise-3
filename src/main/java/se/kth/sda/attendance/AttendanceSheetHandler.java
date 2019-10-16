package se.kth.sda.attendance;

import com.google.gson.Gson;
import se.kth.sda.Start;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Static class for handling I/O interface with AttendanceSheet.
 */
public class AttendanceSheetHandler {

    static String datePattern = "dd-MM-yyyy";
    static DateFormat dateFormat = new SimpleDateFormat(datePattern);

    public static void takeAttendance(AttendanceSheet attendanceSheet, Scanner scanner) {
        System.out.println("Taking attendance...");
        List<String> duplicateIds = attendanceSheet.getDuplicateIDs();
        if (duplicateIds.size() > 0) {
            System.out.println("\n\n******WARNING******\n\nThere are duplicate ids of some members:");
            duplicateIds.forEach(id -> System.out.println("Duplicate id: " + id));
        }
        Date date = getAttendanceDateFromInput(scanner);
        attendanceSheet.setDate(date);
        for (AttendanceSlot slot : attendanceSheet.getAttendanceSlots()) {
            String answer = Start.askYesNo("Is " + slot.getName() + " present? [y/n]");
            if (answer.equals("y")) {
                slot.markAsPresent();
            } else {
                slot.markAsAbsent();
            }
        }
        System.out.println("Done taking attendance. There were a total of " + attendanceSheet.count() + " people in the sheet whereof "
                + attendanceSheet.presentCount() + " were present and " + attendanceSheet.absentCount() + " were absent.");
        System.out.println("Date: " + getStandardDateString(attendanceSheet));
    }

    private static Date getAttendanceDateFromInput(Scanner scanner) {
        Date date = null;
        boolean validDate = false;
        while (!validDate) {
            System.out.println("What is the date for today? [" + datePattern + "]");
            String dateString = scanner.nextLine();
            try {
                date = dateFormat.parse(dateString);
                validDate = true;
            } catch (ParseException e) {
                System.out.println("Invalid date, it should be in the format " + datePattern + ". Also make sure the date actually" +
                        " exists.");
            }
        }
        return date;
    }

    public static String getStandardDateString(AttendanceSheet attendanceSheet) {
        return dateFormat.format(attendanceSheet.getDate());
    }


    public static void saveToFile(AttendanceSheet attendanceSheet) {
        File attendanceSheetFolder = new File("attendance-sheets");
        Gson gson = new Gson();

        if (!attendanceSheetFolder.isDirectory()) {
            boolean isDirectoryCreated = attendanceSheetFolder.mkdir();
            if (!isDirectoryCreated) {
                System.out.println("Could not create sheet directory. Attendance sheet not saved.");
                return;
            }
        }

        try {
            FileWriter fileWriter = new FileWriter("attendance-sheets/" + getStandardDateString(attendanceSheet) + ".json");
            gson.toJson(attendanceSheet, fileWriter);
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Attendance sheet saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printAttendanceSheet (AttendanceSheet attendanceSheet) {
        System.out.println("Attendance sheet for date " + dateFormat.format(attendanceSheet.getDate()));
        for (AttendanceSlot attendanceSlot: attendanceSheet.getAttendanceSlots()) {
            System.out.print(attendanceSlot.getName() + "\t\t");
            if (attendanceSlot.isPresent()) {
                System.out.println("Present");
            } else {
                System.out.println("Absent");
            }
        }
    }

}
