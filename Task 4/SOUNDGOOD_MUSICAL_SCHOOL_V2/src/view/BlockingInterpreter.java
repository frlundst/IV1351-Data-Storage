package view;
import controller.Controller;
import integration.SchoolDBException;

import java.util.Scanner;

public class BlockingInterpreter {
    private Controller controller;
    private boolean keepReceivingCommands = false;
    private final Scanner scanner = new Scanner(System.in);

    public BlockingInterpreter(Controller controller){
        this.controller = controller;
    }

    public void handleCmds() throws SchoolDBException{
        this.keepReceivingCommands = true;
        String command;
        while(this.keepReceivingCommands){
            System.out.println("[1] Find Student By ID \r\n [2] Quit");
            command = this.scanner.nextLine();
            switch(command){
                case "1":
                    System.out.print("Enter ID: ");
                    String ID = this.scanner.nextLine();
                    this.controller.findStudentByID(ID);
                    break;
                case "2":
                    this.keepReceivingCommands = false;
                    break;
            }
        }
    }

}
