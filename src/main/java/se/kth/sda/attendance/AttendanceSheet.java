package se.kth.sda.attendance;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import se.kth.sda.Start;
import se.kth.sda.member.MemberList;

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

    public ArrayList<AttendanceSlot> getAttendanceSlots() {
        return attendanceSlots;
    }

    public Date getDate() {
        return date;
    }

    void setDate(Date date) {
        this.date = date;
    }

    public AttendanceSheet() {
        attendanceSlots = new ArrayList<AttendanceSlot>();
        date = new Date();
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

    public void addPerson(String name) {
        AttendanceSlot attendanceSlot = new AttendanceSlot(name);
        attendanceSlots.add(attendanceSlot);
    }

}
