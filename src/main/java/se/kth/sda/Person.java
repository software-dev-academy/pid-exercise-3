package se.kth.sda;

import com.google.gson.annotations.SerializedName;

/**
 * @author Software Development Academy
 * Class representing a person. Only contains person's name.
 */
public class Person {
    @SerializedName("name") private final String name;  // Make the field final as we don't expect it to change once it's set

    public Person(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the person.
     * @return Returns the name
     */
    public String getName() {
        return name;
    }
}
