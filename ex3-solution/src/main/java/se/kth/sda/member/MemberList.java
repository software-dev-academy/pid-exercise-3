package se.kth.sda.member;

import org.apache.commons.text.RandomStringGenerator;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;


public class MemberList {
    private List<Member> members;

    // Used for generating id
    private static RandomStringGenerator stringGenerator = new RandomStringGenerator.Builder()
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
                .map(Member::getName)
                .collect(Collectors.toList());
        List<String> duplicateList = nameList.stream()
                .filter(n -> Collections.frequency(nameList, n) > 1)
                .distinct()
                .collect(Collectors.toList());
        return duplicateList;
    }

    /**
     * Adds a new member to the MemberList with a generated unique id.
     *
     * @param name
     */
    public void add(String name) {
        String id = stringGenerator.generate(16);
        Member member = new Member(name, id);
        members.add(member);
    }

    /**
     * Removes members from the MemberList that matches (exact match) the given name. Returns true if a member was
     * removed, false otherwise.
     *
     * @param name
     */
    public boolean remove(String name) {
        int start_size = members.size();
        members = members.stream().filter(m -> !m.getName().equals(name)).collect(Collectors.toList());
        return start_size == members.size();
    }

    /**
     * Gets a list of names matching the names of members in MemberList.
     * @return list of names.
     */
    public List<String> getNames() {
        return members.stream().map(Member::getName).collect(Collectors.toList());
    }

    /**
     * Gets a list of the members in MemberList.
     * @return list of names.
     */
    public List<Member> getMembers() {
        return members;
    }

    /**
     * Gets size of MemberList.
     * @return size.
     */
    public int size() {
        return members.size();
    }

    /**
     * Returns true if MemberList contains duplicate ids, false otherwise.
     * @return
     */
    public boolean containsDuplicateIDs() {
        return getDuplicateIDs().size() > 0;
    }

    /**
     * Gets a list of ids which are duplicate in MemberList.
     * @return list of ids.
     */
    public List<String> getDuplicateIDs() {
        List<String> idList = members.stream()
                .map(Member::getId)
                .collect(Collectors.toList());

        List<String> duplicateIdList = idList.stream()
                .filter(n -> Collections.frequency(idList, n) > 1)
                .distinct()
                .collect(Collectors.toList());
        return duplicateIdList;
    }
}
