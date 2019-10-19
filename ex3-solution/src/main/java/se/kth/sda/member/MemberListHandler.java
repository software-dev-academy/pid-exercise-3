package se.kth.sda.member;

import com.google.gson.Gson;
import se.kth.sda.Start;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberListHandler {


    private static void printEditInstructions() {
        System.out.println("You can do the following:");
        System.out.println("'add [Name]' to add someone to the list.");
        System.out.println("'remove [Name]' to remove someone from the list.");
        System.out.println("For example, to remove someone called Person Personson you would write 'remove Person Personson'");
        System.out.println("You can write 'help' to show this information again.'");
    }

    private static boolean isValidEditCommand(String command) {

        // # is not allowed in command
        if (command.contains("#")) {
            return false;
        }

        String[] commandArray = command.split(" ", 2);

        if (commandArray.length < 2) {
            return false;
        } else if (commandArray[0].equals("add") || commandArray[0].equals("remove")) {
            return true;
        } else {
            return false;
        }

    }

    public static void edit(MemberList memberList, Scanner scanner) {
        printEditInstructions();

        boolean isValidInput = false;
        while (!isValidInput) {
            System.out.println("Write a commmand or write '#' to continue.");
            String answer = scanner.nextLine();

            if (answer.strip().equals("#")) {
                break;
            } else if (answer.strip().equals("help")) {
                printEditInstructions();
                continue;
            }

            if (isValidEditCommand(answer)) {
                isValidInput = true;
                String command = answer.split(" ")[0];
                String name = answer.split(" ", 2)[1];
                if (command.equals("add")) {
//                    memberList.add
                } else if (command.equals("remove")) {
                    //
                }

            } else {
                System.out.println("Bad input, write 'help' to see instructions");
            }

        }
    }

    public static void saveToFile(MemberList memberList, Scanner scanner) {
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
            String fileName = scanner.nextLine();
            FileWriter fileWriter = new FileWriter("member-lists/" + fileName + ".json");
            gson.toJson(memberList, fileWriter);
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Member list saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
