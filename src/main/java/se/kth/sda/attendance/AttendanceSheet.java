package se.kth.sda.attendance;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import se.kth.sda.Start;
import se.kth.sda.MemberList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class AttendanceSheet {
    @SerializedName("attendance_sheet")
    private ArrayList<AttendanceSlot> attendanceSlots;
    private Date date;

    static String datePattern = "dd-MM-yyyy";
    static DateFormat dateFormat = new SimpleDateFormat(datePattern);

    public AttendanceSheet() {
        attendanceSlots = new ArrayList<AttendanceSlot>();
    }

    public AttendanceSheet(MemberList memberList) {
        attendanceSlots = new ArrayList<AttendanceSlot>();
        for (String name : memberList.getNames()) {
            attendanceSlots.add(new AttendanceSlot(name));
        }
    }

    public int count() {
        return attendanceSlots.size();
    }

    public int presentCount() {
        int total = 0;
        for (AttendanceSlot slot : attendanceSlots) {
            if (slot.isPresent()) {
                ++total;
            }
        }
        return total;
    }

    public int absentCount() {
        int total = 0;
        for (AttendanceSlot slot : attendanceSlots) {
            if (!slot.isPresent()) {
                ++total;
            }
        }
        return total;
    }

    public void takeAttendance(Scanner scanner) {
        System.out.println("Taking attendance...");
        date = getAttendanceDateFromInput(scanner);
        for (AttendanceSlot slot : attendanceSlots) {
            String answer = Start.askYesNo("Is " + slot.getPersonName() + " present? [y/n]");
            if (answer.equals("y")) {
                slot.markAsPresent();
            } else {
                slot.markAsAbsent();
            }
        }
        System.out.println("Done taking attendance. There were a total of " + count() + " people in the sheet whereof "
                + presentCount() + " were present and " + absentCount() + " were absent.");
        System.out.println("Date: " + getStandardDateString());
    }

    private Date getAttendanceDateFromInput(Scanner scanner) {
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

    public String getStandardDateString() {
        return dateFormat.format(date);
    }

    public void addPerson(String name) {
        AttendanceSlot attendanceSlot = new AttendanceSlot(name);
        attendanceSlots.add(attendanceSlot);
    }

    public void saveToFile() {
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
            FileWriter fileWriter = new FileWriter("attendance-sheets/" + getStandardDateString() + ".json");
            gson.toJson(this, fileWriter);
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Attendance sheet saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        System.out.println("Attendance sheet for date " + dateFormat.format(date));
        for (AttendanceSlot attendanceSlot: attendanceSlots) {
            System.out.print(attendanceSlot.getPersonName() + "\t\t");
            if (attendanceSlot.isPresent()) {
                System.out.println("Present");
            } else {
                System.out.println("Absent");
            }
        }
    }
}
