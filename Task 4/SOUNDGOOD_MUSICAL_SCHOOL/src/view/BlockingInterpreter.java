package view;
import controller.*;
import model.*;
import integration.*;
import java.util.*;


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
        String student_ID;
        while(this.keepReceivingCommands){
            System.out.println("[1] Find Student By ID \r\n [2] List instruments \r\n [3] Rent Instrument \r\n [4] Terminate rental \r\n[5] QUIT");
            command = this.scanner.nextLine();
            switch(command){
                case "1":
                    System.out.print("Enter ID: ");
                    String ID = this.scanner.nextLine();
                    System.out.println(this.controller.findStudentByID(ID).toString());
                    System.out.println("\r\n");
                    break;
                case "2":
                    System.out.print("Enter what kind of instrument: ");
                    String kind = this.scanner.nextLine();
                    List<Instrument> instruments = this.controller.readAllAvailableInstruments(kind);
                    Iterator<Instrument> iterator = instruments.listIterator();
                    while(iterator.hasNext()){
                        System.out.println(iterator.next().toString());
                    }
                    System.out.println("\r\n");
                    break;
                case "3":
                    System.out.print("Enter ID of instrument that student want to rent: ");
                    String rental_instrument_id = scanner.nextLine();
                    System.out.print("Enter ID of student that want to rent: ");
                    student_ID = scanner.nextLine();
                    System.out.println(this.controller.createLeaseContract(student_ID, rental_instrument_id));
                    break;
                case "4":
                    System.out.print("Enter ID of student for which lease contract to be terminated: ");
                    student_ID = scanner.nextLine();
                    System.out.println(this.controller.deleteLeaseContract(student_ID));
                    break;
                case "5":
                    this.keepReceivingCommands = false;
                    break;
            }
        }
    }

}
