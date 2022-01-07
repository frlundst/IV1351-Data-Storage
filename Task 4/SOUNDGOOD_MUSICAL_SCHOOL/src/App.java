import integration.SchoolDBException;
import view.BlockingInterpreter;
import controller.Controller;

public class App {
    /**
     * main starting method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        try{
            new BlockingInterpreter(new Controller()).handleCmds();
        }catch(SchoolDBException exception){
            exception.printStackTrace();
        }
    }
}
