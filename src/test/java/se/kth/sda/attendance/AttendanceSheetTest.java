package se.kth.sda.attendance;

import com.google.gson.Gson;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import se.kth.sda.member.MemberList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class AttendanceSheetTest {

    final String ATTENDANCE_SHEET_1_FILE_NAME = "src/test/res/attendance_sheet_test_1.json";
    final String ATTENDANCE_SHEET_2_FILE_NAME = "src/test/res/attendance_sheet_test_2.json";
    final String MEMBER_LIST_1_FILE_NAME = "src/test/res/member_list_test_1.json";

    private <T> T readObjectFromJsonFile(String fileName, Class<T> tClass) throws FileNotFoundException {
        Gson gson = new Gson();
        String fileContent = new Scanner(new File(fileName)).useDelimiter("\\Z").next();
        T object = gson.fromJson(fileContent, tClass);
        return object;
    }

    @Test
    public void testCountWithEmptyAttendanceSheet() {
        AttendanceSheet attendanceSheet = new AttendanceSheet();

        int count = attendanceSheet.count();

        assertEquals(0, count);
    }

    @Test
    public void testCountWithAttendanceSheetFromMemberList() throws FileNotFoundException {
        MemberList memberList = readObjectFromJsonFile(MEMBER_LIST_1_FILE_NAME, MemberList.class);
        AttendanceSheet attendanceSheet = new AttendanceSheet(memberList);

        int count = attendanceSheet.count();

        assertEquals(8, count);
    }

    @Test
    public void testCountWithAttendanceSheetFromJson() throws FileNotFoundException {
        AttendanceSheet attendanceSheet = readObjectFromJsonFile(ATTENDANCE_SHEET_1_FILE_NAME, AttendanceSheet.class);

        int count = attendanceSheet.count();

        assertEquals(8, count);
    }

    @Test
    public void testCountWithAttendanceSheetFromJsonBig() throws FileNotFoundException {
        AttendanceSheet attendanceSheet = readObjectFromJsonFile(ATTENDANCE_SHEET_2_FILE_NAME, AttendanceSheet.class);

        int count = attendanceSheet.count();

        assertEquals(99, count);
    }

    @Test
    public void testPresentCount() throws FileNotFoundException {
        AttendanceSheet attendanceSheet = readObjectFromJsonFile(ATTENDANCE_SHEET_1_FILE_NAME, AttendanceSheet.class);

        int count = attendanceSheet.presentCount();

        assertEquals(3, count);
    }

    @Test
    public void testPresentCountBig() throws FileNotFoundException {
        AttendanceSheet attendanceSheet = readObjectFromJsonFile(ATTENDANCE_SHEET_2_FILE_NAME, AttendanceSheet.class);

        int count = attendanceSheet.presentCount();

        assertEquals(50, count);
    }

    @Test
    public void testAbsentCountBig() throws FileNotFoundException {
        AttendanceSheet attendanceSheet = readObjectFromJsonFile(ATTENDANCE_SHEET_2_FILE_NAME, AttendanceSheet.class);

        int count = attendanceSheet.absentCount();

        assertEquals(49, count);
    }
}
