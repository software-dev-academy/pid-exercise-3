package se.kth.sda.attendance;

class AttendanceSlot {
    private String name;
    private String id;
    private boolean present;

    public AttendanceSlot(String name, String id) {
        this.name = name;
        this.id = id;
        this.present = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    /**
     * Sets the present field to true
     */
    public void markAsPresent() {
        this.present = true;
    }

    /**
     * Sets the present field to false
     */
    public void markAsAbsent() {
        this.present = false;
    }
}
