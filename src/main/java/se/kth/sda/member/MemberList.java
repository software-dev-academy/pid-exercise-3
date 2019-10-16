package se.kth.sda.member;

import org.apache.commons.text.RandomStringGenerator;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;


public class MemberList {
    private List<Member> members;

    // Used for generating random alpha-numerical strings
    private RandomStringGenerator stringGenerator = new RandomStringGenerator.Builder()
            .withinRange('0', 'z')
            .filteredBy(LETTERS, DIGITS)
            .build();

    public MemberList() {
        members = new ArrayList<>();
    }

    /**
     * Gets the names that occurs more than once in the MemberList.
     *
     * @return A list of the names
     */
    public List<String> getDuplicateNames() {
        ArrayList<String> list = new ArrayList<>();
        List<String> nameList = members.stream()
                .map(m -> m.getName())
                .collect(Collectors.toList());
        List<String> duplicateList = nameList.stream()
                .filter(n -> Collections.frequency(nameList, n) > 1)
                .distinct()
                .collect(Collectors.toList());
        return duplicateList;
    }

    /**
     * Adds a new member to the MemeberList with a generated unique id.
     *
     * @param name
     */
    public void add(String name) {
        String id = stringGenerator.generate(16);
        Member member = new Member(name, id);
        members.add(member);
    }

    /**
     * Gets a list of names matching the names of members in MemberList.
     * @return list of names.
     */
    public List<String> getNames() {
        return members.stream().map(m -> m.getName()).collect(Collectors.toList());
    }

    /**
     * Gets size of MemberList.
     * @return size.
     */
    public int size() {
        return members.size();
    }
}
