package se.kth.sda.attendance;

import com.google.gson.annotations.SerializedName;
import se.kth.sda.member.Member;
import se.kth.sda.member.MemberList;

import java.util.*;
import java.util.stream.Collectors;

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
        for (Member member : memberList.getMembers()) {
            attendanceSlots.add(new AttendanceSlot(member.getName(), member.getId()));
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
    /**
     * Gets a list of ids which are duplicate in AttendanceSheet.
     * @return list of ids.
     */
    public List<String> getDuplicateIDs() {
        List<String> idList = attendanceSlots.stream()
                .map(a -> a.getId())
                .collect(Collectors.toList());

        List<String> duplicateIdList = idList.stream()
                .filter(id -> Collections.frequency(idList, id) > 1)
                .distinct()
                .collect(Collectors.toList());
        return duplicateIdList;
    }

}
