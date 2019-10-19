package se.kth.sda.member;

import com.google.gson.annotations.SerializedName;

/**
 * @author Software Development Academy
 * Class representing a person. Only contains person's name.
 */
public class Member {
    // Make the fields final as we don't expect them to change once they're set
    @SerializedName("name") private final String name;
    @SerializedName("id") private final String id;

    public Member(String name, String id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Gets the name of the person.
     * @return Returns the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the id of the person.
     * @return Returns the id
     */
    public String getId() {
        return id;
    }
}
