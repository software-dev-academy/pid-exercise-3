package se.kth.sda.member;

import com.google.gson.Gson;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class MemberListTest {

    final String MEMBER_LIST_1_FILE_NAME = "src/test/res/member_list_test_1.json";
    final String EMPTY_MEMBER_LIST_FILE_NAME = "src/test/res/empty_member_list.json";
    final String MEMBER_LIST_WITH_DUPLICATE_FILE_NAME = "src/test/res/member_list_with_duplicate.json";
    final String MEMBER_LIST_WITH_ONLY_DUPLICATES_FILE_NAME = "src/test/res/member_list_with_only_duplicates.json";

    /**
     * Helper method to construct an object from a JSON file.
     *
     * @param fileName Name of the JSON file.
     * @param tClass   Class of the object to be constructed.
     * @return constructed object.
     * @throws FileNotFoundException if JSON file is not found.
     */
    private <T> T readObjectFromJsonFile(String fileName, Class<T> tClass) throws FileNotFoundException {
        Gson gson = new Gson();
        String fileContent = new Scanner(new File(fileName)).useDelimiter("\\Z").next();
        T object = gson.fromJson(fileContent, tClass);
        return object;
    }

    @Test
    public void testGetDuplicateNamesWithoutDuplicates() throws FileNotFoundException {
        MemberList memberList = readObjectFromJsonFile(MEMBER_LIST_1_FILE_NAME, MemberList.class);
        List<String> expectedList = Arrays.asList();

        List<String> actualList = memberList.getDuplicateNames();

        assertEquals(expectedList, actualList);
    }

    @Test
    public void testGetDuplicateNamesWithDuplicates() throws FileNotFoundException {
        MemberList memberList = readObjectFromJsonFile(MEMBER_LIST_WITH_DUPLICATE_FILE_NAME, MemberList.class);
        List<String> expectedList = Arrays.asList("Steve Jobs");

        List<String> actualList = memberList.getDuplicateNames();

        assertEquals(expectedList, actualList);
    }

    @Test
    public void testGetDuplicateNamesWithOnlyDuplicates() throws FileNotFoundException {
        MemberList memberList = readObjectFromJsonFile(MEMBER_LIST_WITH_ONLY_DUPLICATES_FILE_NAME, MemberList.class);
        List<String> expectedList = Arrays.asList("David Yu", "Kwabena Asante-poku", "Steve Jobs",
                "Batman Bin Suparman");

        List<String> actualList = memberList.getDuplicateNames();

        assertEquals(expectedList, actualList);
    }

    @Test
    public void testGetZeroSizeWithEmptyMemberList() throws FileNotFoundException {
        MemberList memberList = readObjectFromJsonFile(EMPTY_MEMBER_LIST_FILE_NAME, MemberList.class);

        int actualSize = memberList.size();

        assertEquals(0, actualSize);
    }

    @Test
    public void testGetCorrectSizeWithMemberList1() throws FileNotFoundException {
        MemberList memberList = readObjectFromJsonFile(MEMBER_LIST_1_FILE_NAME, MemberList.class);

        int actualSize = memberList.size();

        assertEquals(8, actualSize);
    }

    @Test
    public void testGetCorrectNamesWithMemberList1() throws FileNotFoundException {
        MemberList memberList = readObjectFromJsonFile(MEMBER_LIST_1_FILE_NAME, MemberList.class);
        List<String> expectedList = Arrays.asList("David Yu", "Mazen Bahy", "Kwabena Asante-poku", "Philipp Haller",
                "Ric Glassey", "Micky Mouse", "Steve Jobs", "Batman Bin Suparman");

        List<String> actualList = memberList.getNames();

        assertEquals(expectedList, actualList);
    }


}


/**
 *
 */
class MemberListFactory {

}

