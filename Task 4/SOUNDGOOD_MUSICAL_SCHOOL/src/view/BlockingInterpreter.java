package view;

import controller.*;
import model.*;
import integration.*;
import java.util.*;

public class BlockingInterpreter {
    private Controller controller;
    private boolean keepReceivingCommands = false;
    private final Scanner scanner = new Scanner(System.in);

    public BlockingInterpreter(Controller controller) {
        this.controller = controller;
    }

    /**
     * handleCmds method is a method for displaying options and results for the
     * user.
     * @throws SchoolDBException
     */
    public void handleCmds() throws SchoolDBException {
        keepReceivingCommands = true;
        String command;
        System.out.println("\r\nWELCOME TO SOUNDGOOD MUSICAL SCHOOL LEASING SYSTEM\r\n");
        while (keepReceivingCommands) {
            System.out.println("[1] List instruments \r\n[2] Rent instrument \r\n[3] Terminate rental \r\n[4] QUIT");
            System.out.print("Enter number: ");
            command = scanner.nextLine();
            switch (command) {
                case "1":
                    System.out.print("Enter what kind of instrument: ");
                    String kind = scanner.nextLine();
                    List<Instrument> instruments;
                    instruments = this.controller.findAvailableInstruments(kind);
                    Iterator<Instrument> iterator = instruments.listIterator();
                    System.out.println("----------------------------------------------------------------------------");
                    System.out.println("[ID Type Brand Available? Quantity] \r\n");
                    while (iterator.hasNext()) {
                        System.out.println(iterator.next().toString());
                    }
                    System.out.println(
                            "---------------------------------------------------------------------------- \r\n");
                    break;
                case "2":
                    System.out.print("Enter ID of instrument that student want to rent: ");
                    String rental_instrument_id = scanner.nextLine();
                    System.out.print("Enter ID of student that want to rent: ");
                    String student_ID = scanner.nextLine();
                    controller.createLeaseContract(Integer.parseInt(student_ID), Integer.parseInt(rental_instrument_id));
                    break;
                case "3":
                    System.out.print("Enter ID of instrument for which lease contract to be terminated: ");
                    String instrument_ID = scanner.nextLine();
                    controller.deleteLeaseContract(Integer.parseInt(instrument_ID));
                    break;
                case "4":
                    keepReceivingCommands = false;
                    break;
                default:
                    System.out.println("ERR: ENTER ONE OF THE LISTED NUMBERS PLEASE");
                    break;
            }
        }
    }

}
