package se.kth.sda;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MemberList {
    private ArrayList<String> names;

    public MemberList() {
        names = new ArrayList<>();
    }


    public void edit() {
        System.out.println("You can do the following:");
        System.out.println("'add [Name]' to add someone to the list.");
        System.out.println("'remove [Name]' to remove someone from the list.");
        System.out.println("For example, to remove someone called Person Personson you would write 'remove Person Personson'");

        boolean editing = true;
        while (editing) {
            System.out.println("Write a commmand or write # to continue.");
            String answer = Start.scanner.nextLine();
            String[] stringArray = answer.split(" ", 2);
            if (stringArray[0].equals("#")) {
                editing = false;
            } else {
                if (stringArray.length < 2) {
                    System.out.println("Bad input for command.");
                } else {
                    if (stringArray[0].equals("add")) {
                        names.add(stringArray[1]);
                        System.out.println("Name added");
                    } else if (stringArray[0].equals("remove")) {
                        boolean removed = names.remove(stringArray[1]);
                        if (removed) {
                            System.out.println("Name removed!");
                        } else {
                            System.out.println("Could not find name in list.");
                        }
                    } else {
                        System.out.println(stringArray[0] + " is not a valid command.");
                    }
                }
            }
        }
    }

    public void saveToFile() {
        File memberListFolder = new File("member-lists");
        Gson gson = new Gson();

        if (!memberListFolder.isDirectory()) {
            boolean isDirectoryCreated = memberListFolder.mkdir();
            if (!isDirectoryCreated) {
                System.out.println("Could not create member list directory. Member list not saved.");
                return;
            }
        }

        try {
            System.out.println("Choose a filename for the member list (json file extension will be appended).");
            String fileName = Start.scanner.nextLine();
            FileWriter fileWriter = new FileWriter("member-lists/" + fileName + ".json");
            gson.toJson(this, fileWriter);
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Member list saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getNames() {
        return names;
    }
}
